package com.example.Banking.services;

import com.example.Banking.DTOs.TransactionDTO;
import com.example.Banking.Entities.Account;
import com.example.Banking.Entities.Ledger;
import com.example.Banking.Entities.Transaction;
import com.example.Banking.Enums.AccountType;
import com.example.Banking.Mapper.TransactionMapper;
import com.example.Banking.Reposetries.AccountRepository;
import com.example.Banking.Reposetries.LedgerRepository;
import com.example.Banking.Reposetries.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private LedgerRepository ledgerRepo;

    private final Random random = new Random();

    public Page<TransactionDTO> getTransactions(Pageable pageable, String accountId, String type, String search) {

        boolean hasAccountId = accountId != null && !accountId.isEmpty();
        boolean hasType = type != null && !type.isEmpty();
        boolean hasSearch = search != null && !search.isEmpty();

        if (hasSearch && hasType) {
            // Search + type filter
            return transactionRepository
                    .findByTypeAndFromAccountAccountNumberContainingIgnoreCaseOrTypeAndToAccountAccountNumberContainingIgnoreCase(
                            type, accountId, type, search, pageable)
                    .map(TransactionMapper::toDTO);
        }

        if (!hasAccountId && !hasType) {
            // No filters → all
            return transactionRepository.findAll(pageable).map(TransactionMapper::toDTO);
        }

        if (hasAccountId && !hasType) {
            // Only accountId
            return transactionRepository
                    .findByFromAccountAccountNumberContainingIgnoreCaseOrToAccountAccountNumberContainingIgnoreCase(
                            accountId, accountId, pageable)
                    .map(TransactionMapper::toDTO);
        }

        if (!hasAccountId && hasType) {
            // Only type
            return transactionRepository.findByType(type, pageable).map(TransactionMapper::toDTO);
        }

        // Both accountId + type
        return transactionRepository
                .findByTypeAndFromAccountAccountNumberContainingIgnoreCaseOrTypeAndToAccountAccountNumberContainingIgnoreCase(
                        type, accountId, type, accountId, pageable)
                .map(TransactionMapper::toDTO);
    }

    @Transactional
    public TransactionDTO transfer(Long fromAccountId, Long toAccountId, Double amount) {
        if (amount < 1) {
            throw new RuntimeException("Please Enter Valued Amount");
        }

        if (fromAccountId.equals(toAccountId))
            throw new RuntimeException("Cannot transfer to the same account");

        Account from = accountRepo.findByAccountNumber(fromAccountId.toString());
        Account to = accountRepo.findByAccountNumber(toAccountId.toString());

        if (from.getAccountType() == AccountType.FIXED || from.getAccountType() == AccountType.SAVINGS) {
            throw new RuntimeException("You Cant transfer from " + from.getAccountType() + " Account");
        }

        if (to.getAccountType() == AccountType.FIXED) {
            throw new RuntimeException("You Cant transfer to " + to.getAccountType() + " Account");
        }

        if (from.getBalance() < amount)
            throw new RuntimeException("Insufficient balance");

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        accountRepo.save(from);
        accountRepo.save(to);

        Transaction tx = new Transaction();
        tx.setFromAccount(from);
        tx.setToAccount(to);
        tx.setAmount(amount);
        tx.setType("TRANSFER");
        tx.setStatus("SUCCESS");
        transactionRepository.save(tx);
        // Ledger entries
        Ledger debit = new Ledger();
        debit.setAccount(from);
        debit.setTransaction(tx);
        debit.setEntryType("DEBIT");
        debit.setAmount(amount);
        ledgerRepo.save(debit);

        Ledger credit = new Ledger();
        credit.setAccount(to);
        credit.setTransaction(tx);
        credit.setEntryType("CREDIT");
        credit.setAmount(amount);
        ledgerRepo.save(credit);

        return TransactionMapper.toDTO(tx);
    }

    @Transactional
    public TransactionDTO deposit(Long accountId, Double amount) {
        if (amount < 1) {
            throw new RuntimeException("Please Enter Valued Amount");
        }
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if (account.getAccountType() == AccountType.FIXED && amount < 10000) {
            throw new RuntimeException("You Cant Add less than 10000 in fixed account");
        }
        // تحديث الرصيد
        account.setBalance(account.getBalance() + amount);
        accountRepo.save(account);
        // إنشاء transaction
        Transaction transaction = new Transaction();
        transaction.setFromAccount(account);
        transaction.setToAccount(account);
        transaction.setType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setStatus("SUCCESS");
        transaction.setCreatedAt(LocalDateTime.now());

        Ledger credit = new Ledger();
        credit.setAccount(account);
        credit.setTransaction(transaction);
        credit.setEntryType("CREDIT");
        credit.setAmount(amount);
        ledgerRepo.save(credit);

        transactionRepository.save(transaction);

        return TransactionMapper.toDTO(transaction);
    }

    @Transactional
    public TransactionDTO withdraw(Long accountId, Double amount) {

        if (amount < 1) {
            throw new RuntimeException("Please Enter Valued Amount");
        }
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        // التحقق من الرصيد
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }
        // التحقق من نوع الحساب (مثلاً لا يسمح بالسحب من FIXED)
        if (account.getAccountType() == AccountType.FIXED) {
            throw new RuntimeException("You cannot withdraw from a fixed account");
        }
        // تحديث الرصيد
        account.setBalance(account.getBalance() - amount);
        accountRepo.save(account);
        // إنشاء transaction
        Transaction transaction = new Transaction();
        transaction.setFromAccount(account);
        transaction.setToAccount(account);
        transaction.setType("WITHDRAW");
        transaction.setAmount(amount);
        transaction.setStatus("SUCCESS");
        transaction.setCreatedAt(LocalDateTime.now());
        // إنشاء قيد محاسبي (Debit)
        Ledger debit = new Ledger();
        debit.setAccount(account);
        debit.setTransaction(transaction);
        debit.setEntryType("DEBIT");
        debit.setAmount(amount);
        ledgerRepo.save(debit);

        transactionRepository.save(transaction);

        return TransactionMapper.toDTO(transaction);
    }

}
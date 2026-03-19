package com.example.Banking.services;

import com.example.Banking.DTOs.AccountDTO;
import com.example.Banking.Entities.Account;
import com.example.Banking.Enums.AccountType;
import com.example.Banking.Mapper.AccountMapper;
import com.example.Banking.Reposetries.AccountRepository;
import com.example.Banking.Reposetries.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private UserRepository userRepo;

    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    public Account getAccountById(Long id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public List<AccountDTO> getAccountByUserId(Long userId) {
        return accountRepo.findByUserId(userId).stream().map(AccountMapper::toDTO).toList();
    }

    public AccountDTO createAccount(Long userId, AccountType type) {
        List<Account> userAccounts = accountRepo.findByUserId(userId);

        // rule 1: max 3 accounts
        if (userAccounts.size() >= 3) {
            throw new RuntimeException("User cannot have more than 3 accounts");
        }

        // rule 2: no duplicate type
        boolean exists = accountRepo.existsByUserIdAndAccountType(userId, type);
        if (exists) {
            throw new RuntimeException("User already has this account type");
        }

        Faker faker = new Faker();
        Account account = new Account();
        account.setUser(userRepo.findById(userId).get());
        account.setAccountNumber(faker.number().digits(10));
        account.setBalance(0.0);
        account.setStatus("ACTIVE");
        account.setAccountType(type);
        account.setInterest(0);

        return AccountMapper.toDTO(accountRepo.save(account));
    }

    public void deleteAccount(Long id) {
        accountRepo.deleteById(id);
    }
}
package com.example.Banking.services;

import com.example.Banking.Entities.Account;
import com.example.Banking.Entities.Ledger;
import com.example.Banking.Entities.Transaction;
import com.example.Banking.Entities.User;
import com.example.Banking.Enums.AccountType;
import com.example.Banking.Enums.Role;
import com.example.Banking.Reposetries.AccountRepository;
import com.example.Banking.Reposetries.LedgerRepository;
import com.example.Banking.Reposetries.TransactionRepository;
import com.example.Banking.Reposetries.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private final UserRepository userRepo;
    @Autowired
    private final AccountRepository accountRepo;
    @Autowired
    private final TransactionRepository transactionRepo;
    @Autowired
    private final LedgerRepository ledgerRepo;

    public DataSeeder(UserRepository userRepo, AccountRepository accountRepo, TransactionRepository transactionRepo, LedgerRepository ledgerRepo) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
        this.transactionRepo = transactionRepo;
        this.ledgerRepo = ledgerRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        List<User> users = new ArrayList<>();

        // توليد 5 مستخدم
        for(int i=0;i<200;i++){
            User user = new User();
            user.setName(faker.name().fullName());
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(new BCryptPasswordEncoder().encode("password123"));
            user.setRole(Role.USER);
            users.add(user);
        }
        userRepo.saveAll(users);

        List<Account> accounts = new ArrayList<>();
        // توليد حساب لكل مستخدم
        for(User user : users){
            Account account = new Account();
            account.setUser(user);
            account.setAccountNumber(faker.number().digits(10));
            account.setBalance(faker.number().randomDouble(2, 1000, 50000));
            account.setStatus("ACTIVE");
            account.setAccountType(AccountType.CURRENT);
            account.setInterest(0);
            accounts.add(account);
        }
        accountRepo.saveAll(accounts);

        List<Transaction> transactions = new ArrayList<>();
        List<Ledger> ledgers = new ArrayList<>();
        Random random = new Random();

        // توليد معاملات عشوائية
        for(int i=0;i<200;i++){
            Account from = accounts.get(random.nextInt(accounts.size()));
            Account to = accounts.get(random.nextInt(accounts.size()));
            if(from.getId().equals(to.getId())) continue;

            double amount = faker.number().randomDouble(2, 10, 5000);

            Transaction tx = new Transaction();
            tx.setFromAccount(from);
            tx.setToAccount(to);
            tx.setAmount(amount);
            tx.setType("TRANSFER");
            tx.setStatus("SUCCESS");
            transactions.add(tx);

            // Ledger entries
            Ledger debit = new Ledger();
            debit.setAccount(from);
            debit.setTransaction(tx);
            debit.setEntryType("DEBIT");
            debit.setAmount(amount);
            ledgers.add(debit);

            Ledger credit = new Ledger();
            credit.setAccount(to);
            credit.setTransaction(tx);
            credit.setEntryType("CREDIT");
            credit.setAmount(amount);
            ledgers.add(credit);
        }

        transactionRepo.saveAll(transactions);
        ledgerRepo.saveAll(ledgers);

        System.out.println("Seeding completed!");
    }
}
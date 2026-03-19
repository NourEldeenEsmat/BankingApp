package com.example.Banking.Reposetries;

import com.example.Banking.Entities.Account;
import com.example.Banking.Enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUserId(Long userId);
    Account findByAccountNumber(String accountNumber);

    boolean existsByUserIdAndAccountType(Long user, AccountType accountType);
}
package com.example.Banking.Controller;

import com.example.Banking.DTOs.AccountDTO;
import com.example.Banking.Entities.Account;
import com.example.Banking.Enums.AccountType;
import com.example.Banking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createAccount(@PathVariable Long userId, @RequestParam AccountType accountType) {
        try {
            return new ResponseEntity<>(accountService.createAccount(userId, accountType), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @GetMapping("/getByUserId/{userId}")
    public List<AccountDTO> getByUserId(@PathVariable Long userId){
        return accountService.getAccountByUserId(userId);
    }
    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }
}
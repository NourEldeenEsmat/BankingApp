package com.example.Banking.Mapper;

import com.example.Banking.DTOs.AccountDTO;
import com.example.Banking.Entities.Account;

public class AccountMapper {

    public static AccountDTO toDTO(Account account) {
        if (account == null) return null;

        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance());
        dto.setAccountType(account.getAccountType());
        dto.setUserId(account.getUser().getId());
        dto.setInterest(account.getInterest());
        return dto;
    }
}
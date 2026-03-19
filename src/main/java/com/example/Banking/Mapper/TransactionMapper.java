package com.example.Banking.Mapper;

import com.example.Banking.DTOs.TransactionDTO;
import com.example.Banking.Entities.Transaction;

public class TransactionMapper {

    public static TransactionDTO toDTO(Transaction transaction) {
        if (transaction == null) return null;

        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setType(transaction.getType());
        dto.setStatus(transaction.getStatus());
        dto.setCreatedAt(transaction.getCreatedAt());
        dto.setFromAccountNumber(transaction.getFromAccount().getAccountNumber());
        dto.setToAccountNumber(transaction.getToAccount().getAccountNumber());
        return dto;
    }
}
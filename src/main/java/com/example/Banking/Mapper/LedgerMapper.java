package com.example.Banking.Mapper;

import com.example.Banking.DTOs.LedgerDto;
import com.example.Banking.Entities.Ledger;
import com.example.Banking.Entities.Transaction;

public class LedgerMapper {
    public static LedgerDto toDto(Ledger ledger) {
        Transaction transaction = ledger.getTransaction();
        return new LedgerDto(
                ledger.getId()
                , ledger.getEntryType()
                , ledger.getAmount()
                , transaction.getType()
                , transaction.getStatus()
                , transaction.getFromAccount().getAccountNumber()
                , transaction.getToAccount().getAccountNumber()
                , ledger.getCreatedAt().toString()
                , ledger.getAccount().getBalance().toString()
        );
    }
}

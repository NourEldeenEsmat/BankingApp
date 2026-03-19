package com.example.Banking.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LedgerDto {
    private Long id;
    private String entryType; // DEBIT / CREDIT
    private Double amount;
    private String transactionType;
    private String transactionStatus;
    private String fromAccount;
    private String toAccount;
    private String createdAt;
    private String balance;
}

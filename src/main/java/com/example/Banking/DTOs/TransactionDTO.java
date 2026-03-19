package com.example.Banking.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private Long id;
    private Double amount;
    private String type;
    private String fromAccountNumber;
    private String toAccountNumber;
    private String status;
    private LocalDateTime createdAt;

}

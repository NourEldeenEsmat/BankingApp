package com.example.Banking.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Ledger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String entryType; // DEBIT / CREDIT
    private Double amount;
    private LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne
    private Account account;
    @ManyToOne
    private Transaction transaction;
}
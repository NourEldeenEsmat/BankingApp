package com.example.Banking.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type; // TRANSFER, DEPOSIT, WITHDRAWAL
    private Double amount;
    private String status; // SUCCESS, FAILED
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
    private LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne
    private Account fromAccount;
    @ManyToOne
    private Account toAccount;
}
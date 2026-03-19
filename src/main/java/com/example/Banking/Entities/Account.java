package com.example.Banking.Entities;

import com.example.Banking.Enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private Double balance;
    private String status;
    private int interest;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @ManyToOne
    private User user;
}
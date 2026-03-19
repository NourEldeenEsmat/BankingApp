package com.example.Banking.DTOs;
import com.example.Banking.Enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    private Long id;
    private String accountNumber;
    private Double balance;
    private Long userId;
    private AccountType accountType;
    private int interest;

}
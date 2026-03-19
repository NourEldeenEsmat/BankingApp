package com.example.Banking.DTOs;

import lombok.Data;

@Data
public
class AuthRequest {
    private String username;
    private String password;
}

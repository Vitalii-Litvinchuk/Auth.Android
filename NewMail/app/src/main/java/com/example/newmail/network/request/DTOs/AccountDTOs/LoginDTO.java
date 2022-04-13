package com.example.newmail.network.request.DTOs.AccountDTOs;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String password;
}

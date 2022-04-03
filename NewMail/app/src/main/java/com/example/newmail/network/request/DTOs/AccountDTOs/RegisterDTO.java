package com.example.newmail.network.request.DTOs.AccountDTOs;

import lombok.Data;

@Data
public class RegisterDTO {
    private String email;
    private String firstName;
    private String secondName;
    private String photo;
    private String phone;
    private String password;
    private String confirmPassword;
}
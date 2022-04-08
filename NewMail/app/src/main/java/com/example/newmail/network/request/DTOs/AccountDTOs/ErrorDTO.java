package com.example.newmail.network.request.DTOs.AccountDTOs;

import lombok.Data;

@Data
public class ErrorDTO {
    String[] email;
    String[] firstName;
    String[] password;
    String[] photo;
    String[] confirmPassword;
}

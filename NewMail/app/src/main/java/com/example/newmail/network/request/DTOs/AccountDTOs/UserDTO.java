package com.example.newmail.network.request.DTOs.AccountDTOs;

import lombok.Data;

@Data
public class UserDTO {
    long id;
    String email;
    String firstName;
    String secondName;
    String photo ;
    String phone ;
}

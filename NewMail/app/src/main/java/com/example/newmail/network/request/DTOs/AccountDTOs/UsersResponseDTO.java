package com.example.newmail.network.request.DTOs.AccountDTOs;

import java.util.ArrayList;

import lombok.Data;

@Data
public class UsersResponseDTO {
    ArrayList<UserDTO> users;
}

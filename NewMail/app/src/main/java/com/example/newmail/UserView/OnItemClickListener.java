package com.example.newmail.UserView;

import com.example.newmail.network.request.DTOs.AccountDTOs.UserDTO;

public interface OnItemClickListener {
    void onItemClick(UserDTO user);
}
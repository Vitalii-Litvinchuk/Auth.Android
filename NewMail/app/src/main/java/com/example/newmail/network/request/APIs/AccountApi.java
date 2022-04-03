package com.example.newmail.network.request.APIs;

import com.example.newmail.network.request.DTOs.AccountDTOs.RegisterDTO;
import com.example.newmail.network.request.DTOs.AccountDTOs.RegisterResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountApi {
    @POST("/api/account/register")
    public Call<RegisterResponseDTO> register(@Body RegisterDTO model);
}
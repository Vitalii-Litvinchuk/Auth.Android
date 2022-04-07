package com.example.newmail.network.request.APIs;

import com.example.newmail.network.request.DTOs.AccountDTOs.RegisterDTO;
import com.example.newmail.network.request.DTOs.AccountDTOs.RegisterResponseDTO;
import com.example.newmail.network.request.DTOs.AccountDTOs.UserResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AccountApi {
    @POST("/api/account/register")
    public Call<RegisterResponseDTO> register(@Body RegisterDTO model);
    @GET("/api/account/users")
    public Call<UserResponseDTO> users();
}
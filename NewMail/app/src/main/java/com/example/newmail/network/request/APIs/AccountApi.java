package com.example.newmail.network.request.APIs;

import com.example.newmail.network.request.DTOs.AccountDTOs.LoginDTO;
import com.example.newmail.network.request.DTOs.AccountDTOs.MessageResponseDTO;
import com.example.newmail.network.request.DTOs.AccountDTOs.RegisterDTO;
import com.example.newmail.network.request.DTOs.AccountDTOs.AccountResponseDTO;
import com.example.newmail.network.request.DTOs.AccountDTOs.UserDTO;
import com.example.newmail.network.request.DTOs.AccountDTOs.UserResponseDTO;
import com.example.newmail.network.request.DTOs.AccountDTOs.UsersResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AccountApi {
    @POST("/api/account/register")
    public Call<AccountResponseDTO> register(@Body RegisterDTO model);
    @POST("/api/account/login")
    public Call<AccountResponseDTO> login(@Body LoginDTO model);
    @GET("/api/account/users")
    public Call<UsersResponseDTO> users();
    @GET("/api/account/user/{id}")
    public Call<UserResponseDTO> user(@Path("id") String Id);
    @PUT("/api/account/edit")
    public Call<MessageResponseDTO> edit(@Body UserDTO userDTO);
}
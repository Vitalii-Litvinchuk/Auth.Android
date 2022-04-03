package com.example.newmail.network.request.APIs;

import com.example.newmail.network.request.DTOs.ImageDTOs.ImageDTO;
import com.example.newmail.network.request.DTOs.ImageDTOs.ImageResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ImageApi {
    @POST("/image/upload")
    public Call<ImageResponseDTO> uploadImage(@Body ImageDTO model);
}

package com.example.newmail.network.request;

import com.example.newmail.constants.Urls;
import com.example.newmail.interceptors.JWTInterceptor;
import com.example.newmail.network.ConnectionDetector;
import com.example.newmail.network.request.APIs.AccountApi;
import com.example.newmail.network.request.APIs.ImageApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestService {
    private static RequestService instance;
    private Retrofit retrofit;

    private RequestService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new JWTInterceptor())
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Urls.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RequestService getInstance() {
        if (instance == null)
            instance = new RequestService();
        return instance;
    }

    public ImageApi jsonImageApi() {
        return retrofit.create(ImageApi.class);
    }

    public AccountApi jsonAccountApi() {
        return retrofit.create(AccountApi.class);
    }
}

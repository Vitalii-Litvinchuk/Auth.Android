package com.example.newmail.network.request;

import androidx.annotation.Nullable;

import com.example.newmail.constants.Urls;
import com.example.newmail.network.request.APIs.AccountApi;
import com.example.newmail.network.request.APIs.ImageApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
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
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Urls.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private RequestService(String token) {
        Authenticator authenticator = new Authenticator() {
            @Nullable
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                if (response.request().header("Authorization") != null) {
                    return null;
                }
                return response.request().newBuilder().header("Authorization", "Bearer " + token).build();
            }
        };

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .authenticator(authenticator)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
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

    public static void setInstanceAuthorization(String token) {
        instance = new RequestService(token);
    }

    public static void resetInstance() {
        instance = new RequestService();
    }

    public ImageApi jsonImageApi() {
        return retrofit.create(ImageApi.class);
    }

    public AccountApi jsonAccountApi() {
        return retrofit.create(AccountApi.class);
    }
}

package com.example.newmail.account;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.newmail.R;
import com.example.newmail.UserView.UserAdapter;
import com.example.newmail.simplification.EasierActivity;
import com.example.newmail.network.request.DTOs.AccountDTOs.UserResponseDTO;
import com.example.newmail.network.request.RequestService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersActivity extends EasierActivity {

    private UserAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        recyclerView = findViewById(R.id.rcv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2,
                LinearLayoutManager.VERTICAL, false));


        buildUsers();
    }

    private void buildUsers() {
            RequestService.getInstance().jsonAccountApi().users()
                    .enqueue(new Callback<UserResponseDTO>() {
                        @Override
                        public void onResponse(Call<UserResponseDTO> call, Response<UserResponseDTO> response) {
                            UserResponseDTO data = response.body();
                            adapter = new UserAdapter(data.getUsers());
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<UserResponseDTO> call, Throwable t) {
                        }
                    });
    }
}
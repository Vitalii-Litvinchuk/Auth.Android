package com.example.newmail.account;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.newmail.R;
import com.example.newmail.UserView.UsersAdapter;
import com.example.newmail.network.request.DTOs.AccountDTOs.UserDTO;
import com.example.newmail.simplification.BaseActivity;
import com.example.newmail.network.request.DTOs.AccountDTOs.UsersResponseDTO;
import com.example.newmail.network.request.RequestService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersActivity extends BaseActivity {

    private UsersAdapter adapter;
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
                .enqueue(new Callback<UsersResponseDTO>() {
                    @Override
                    public void onResponse(Call<UsersResponseDTO> call, Response<UsersResponseDTO> response) {
                        UsersResponseDTO data = response.body();
                        adapter = new UsersAdapter(data.getUsers(),
                                UsersActivity.this::onClickByItem,
                                UsersActivity.this::onClickEditUser);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<UsersResponseDTO> call, Throwable t) {
                    }
                });
    }

    private void onClickByItem(UserDTO user) {
//        Toast.makeText(HomeApplication.getAppContext(), user.getEmail(), Toast.LENGTH_LONG).show();
        Intent intent;
        intent = new Intent(this, ViewUserActivity.class);
        Bundle b = new Bundle();
        b.putLong("userId", user.getId());
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }

    private void onClickEditUser(UserDTO user) {
//        Toast.makeText(HomeApplication.getAppContext(), "EditUser "+user.getEmail(), Toast.LENGTH_LONG).show();
        Intent intent;
        intent = new Intent(this, EditUserActivity.class);
        Bundle b = new Bundle();
        b.putLong("userId", user.getId());
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }
}
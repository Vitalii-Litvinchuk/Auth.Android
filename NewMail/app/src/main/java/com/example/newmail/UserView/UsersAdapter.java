package com.example.newmail.UserView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.newmail.R;
import com.example.newmail.application.HomeApplication;
import com.example.newmail.constants.Urls;
import com.example.newmail.network.request.DTOs.AccountDTOs.UserDTO;

import java.util.Date;
import java.util.List;

import lombok.NonNull;

public class UsersAdapter extends RecyclerView.Adapter<UserCardViewHolder> {
    private List<UserDTO> modelList;
    private final OnItemClickListener listener;
    private final OnItemClickListener editUser;


    public UsersAdapter(List<UserDTO> users,
                        OnItemClickListener listener,
                        OnItemClickListener editUser) {
        this.modelList = users;
        this.listener=listener;
        this.editUser=editUser;
    }

    @NonNull
    @Override
    public UserCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.user_card,
                        parent, false);
        return new UserCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserCardViewHolder holder, int position) {
        if (modelList != null && position < modelList.size()) {
            UserDTO model = modelList.get(position);
            holder.userEmail.setText(model.getEmail());
            int i = (int) (new Date().getTime()/1000);
            String url = Urls.BASE + model.getPhoto() + "?data=" + i;
            Glide.with(HomeApplication.getAppContext())
                    .load(url)
                    //.circleCrop()
                    .apply(new RequestOptions().override(300, 300))
                    .into(holder.userPhoto);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(model);
                }
            });

            holder.userEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editUser.onItemClick(model);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
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

public class UserAdapter extends RecyclerView.Adapter<UserCardViewHolder> {
    private List<UserDTO> modelList;

    public UserAdapter(List<UserDTO> modelList) {
        this.modelList = modelList;
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
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
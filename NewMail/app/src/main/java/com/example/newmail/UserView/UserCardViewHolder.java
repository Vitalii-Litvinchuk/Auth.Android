package com.example.newmail.UserView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.newmail.R;

import lombok.NonNull;

public class UserCardViewHolder extends RecyclerView.ViewHolder {

    private View view;
    public ImageView userPhoto;
    public TextView userEmail;

    public UserCardViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        userPhoto = itemView.findViewById(R.id.userPhoto);
        userEmail = itemView.findViewById(R.id.userEmail);
    }

    public View getView() {
        return view;
    }
}

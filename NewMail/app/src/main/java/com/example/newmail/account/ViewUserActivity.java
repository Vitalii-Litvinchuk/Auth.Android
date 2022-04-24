package com.example.newmail.account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.newmail.R;
import com.example.newmail.application.HomeApplication;
import com.example.newmail.constants.Methods;
import com.example.newmail.constants.TextInputHelper;
import com.example.newmail.constants.Urls;
import com.example.newmail.network.request.DTOs.AccountDTOs.UserDTO;
import com.example.newmail.network.request.DTOs.AccountDTOs.UserResponseDTO;
import com.example.newmail.network.request.RequestService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewUserActivity extends AppCompatActivity {
    private long Id;
    private UserDTO user;

    private TextInputHelper email;
    private TextInputHelper firstName;
    private TextInputHelper secondName;
    private TextInputHelper phone;
    private ImageView myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        myImage = findViewById(R.id.myimgUserData);

        Bundle b = getIntent().getExtras();
        if (b != null)
            Id = b.getLong("userId");
        else finish();
        email = new TextInputHelper(findViewById(R.id.emailUserData), findViewById(R.id.txtEmailUserData));
        firstName = new TextInputHelper(findViewById(R.id.firstNameUserData), findViewById(R.id.txtFirstNameUserData));
        secondName = new TextInputHelper(findViewById(R.id.secondNameUserData), findViewById(R.id.txtSecondNameUserData));
        phone = new TextInputHelper(findViewById(R.id.phoneUserData), findViewById(R.id.txtPhoneUserData));

        RequestService.getInstance().jsonAccountApi().user(String.valueOf(Id))
                .enqueue(new Callback<UserResponseDTO>() {
                    @Override
                    public void onResponse(Call<UserResponseDTO> call, Response<UserResponseDTO> response) {
                        if (response.isSuccessful()) {
                            user = response.body().getUser();
                            email.editText.setText(user.getEmail());
                            firstName.editText.setText(user.getFirstName());
                            secondName.editText.setText(user.getSecondName());
                            phone.editText.setText(user.getPhone());

                            String url = Urls.BASE + user.getPhoto();
                            Glide.with(HomeApplication.getAppContext())
                                    .load(url)
                                    .apply(new RequestOptions().override(300, 300))
                                    .into(myImage);
                        } else {
                            try {
                            } catch (Exception ex) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponseDTO> call, Throwable t) {
                    }
                });

    }
}
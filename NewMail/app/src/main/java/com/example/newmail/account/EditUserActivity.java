package com.example.newmail.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.newmail.R;
import com.example.newmail.application.HomeApplication;
import com.example.newmail.constants.Methods;
import com.example.newmail.constants.TextInputHelper;
import com.example.newmail.constants.Urls;
import com.example.newmail.network.request.DTOs.AccountDTOs.ErrorDTO;
import com.example.newmail.network.request.DTOs.AccountDTOs.MessageResponseDTO;
import com.example.newmail.network.request.DTOs.AccountDTOs.UserDTO;
import com.example.newmail.network.request.DTOs.AccountDTOs.UserResponseDTO;
import com.example.newmail.network.request.RequestService;
import com.example.newmail.simplification.EasierActivity;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserActivity extends EasierActivity {
    private TextView imgError;
    private ProgressBar progBar;
    private Button btn;
    private long Id;
    private UserDTO user;

    private TextInputHelper email;
    private TextInputHelper firstName;
    private TextInputHelper secondName;
    private TextInputHelper phone;
    private String photoBase64 = "";

    private ImageView myImage;
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        imgError = findViewById(R.id.imgErrorEdit);
        myImage = findViewById(R.id.myimgEdit);

        progBar = findViewById(R.id.progBarEdit);
        btn = findViewById(R.id.buttonEdit);

        Bundle b = getIntent().getExtras();
        if (b != null)
            Id = b.getLong("userId");
        else finish();
        email = new TextInputHelper(findViewById(R.id.emailEdit), findViewById(R.id.txtEmailEdit));
        firstName = new TextInputHelper(findViewById(R.id.firstNameEdit), findViewById(R.id.txtFirstNameEdit));
        secondName = new TextInputHelper(findViewById(R.id.secondNameEdit), findViewById(R.id.txtSecondNameEdit));
        phone = new TextInputHelper(findViewById(R.id.phoneEdit), findViewById(R.id.txtPhoneEdit));

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
                                String json = response.errorBody().string();
                                jsonError(json);
                                Methods.disableProgressBarWithButton(progBar, btn);
                            } catch (Exception ex) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponseDTO> call, Throwable t) {
                    }
                });

    }


    public void handleClick(View view) {
        Methods.enableProgressBarWithButton(progBar, btn);
        UserDTO userDTO = new UserDTO();

        userDTO.setId(Id);
        userDTO.setEmail(email.editText.getText().toString());
        userDTO.setPhone(phone.editText.getText().toString());
        userDTO.setFirstName(firstName.editText.getText().toString());
        userDTO.setSecondName(secondName.editText.getText().toString());
        userDTO.setPhoto(photoBase64);

        RequestService.getInstance().jsonAccountApi().edit(userDTO)
                .enqueue(new Callback<MessageResponseDTO>() {
                    @Override
                    public void onResponse(Call<MessageResponseDTO> call, Response<MessageResponseDTO> response) {
                        if (response.isSuccessful()) {
                            String str = response.body().getMessage();
                            Toast.makeText(HomeApplication.getAppContext(), str, Toast.LENGTH_LONG).show();
                            Methods.disableProgressBarWithButton(progBar, btn);

                        } else {
                            try {
                                String json = response.errorBody().string();
                                jsonError(json);
                            } catch (Exception ex) {

                            }
                            Methods.disableProgressBarWithButton(progBar, btn);
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponseDTO> call, Throwable t) {
                        Methods.disableProgressBarWithButton(progBar, btn);
                    }
                });
    }

    public void onSelectImage(View view) {
        imageChooser();
    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri uri = data.getData();

                Bitmap bitmap = null;

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bytes = stream.toByteArray();
                String sImage = Base64.encodeToString(bytes, Base64.DEFAULT);
                photoBase64 = sImage;
                myImage.setImageBitmap(bitmap);
            }
        }
    }

    private void jsonError(String json) {
        Gson g = new Gson();
        ErrorDTO error = g.fromJson(json, ErrorDTO.class);

        String[] emailError = error.getErrors().getEmail();
        if (emailError != null) {
            email.layout.setError(emailError[0]);
        } else email.layout.setError(null);

        String[] firstNameError = error.getErrors().getFirstName();
        if (firstNameError != null) {
            firstName.layout.setError(firstNameError[0]);
        } else firstName.layout.setError(null);

        String[] photoError = error.getErrors().getPhoto();
        if (photoError != null) {
            imgError.setError(photoError[0]);
        } else imgError.setError(null);
    }
}
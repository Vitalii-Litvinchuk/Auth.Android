package com.example.newmail.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.example.newmail.R;
import com.example.newmail.constants.TextInputHelper;
import com.example.newmail.constants.Validator;
import com.example.newmail.network.request.DTOs.AccountDTOs.RegisterDTO;
import com.example.newmail.network.request.DTOs.AccountDTOs.RegisterResponseDTO;
import com.example.newmail.network.request.RequestService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    TextInputHelper email;
    TextInputHelper firstName;
    TextInputHelper secondName;
    TextInputHelper phone;
    TextInputHelper password;
    TextInputHelper confirmPassword;

    private ImageView myImage;
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = new TextInputHelper(findViewById(R.id.email), findViewById(R.id.txtEmail));
        firstName = new TextInputHelper(findViewById(R.id.firstName), findViewById(R.id.txtFirstName));
        secondName = new TextInputHelper(findViewById(R.id.secondName), findViewById(R.id.txtSecondName));
        phone = new TextInputHelper(findViewById(R.id.phone), findViewById(R.id.txtPhone));
        password = new TextInputHelper(findViewById(R.id.password), findViewById(R.id.txtPassword));
        confirmPassword = new TextInputHelper(findViewById(R.id.confirmPassword), findViewById(R.id.txtConfirmPassword));

        myImage = findViewById(R.id.myimg);
    }

    String photoBase64 = "";

    public void handleClick(View view) {
        boolean validData = true;

        if (!Validator.emailValidate(email.editText.getText().toString())) {
            email.layout.setError("Вкажіть пошту правильно");
            validData = false;
        } else email.layout.setError(null);

        if (!Validator.phoneNumberValidate(phone.editText.getText().toString())) {
            phone.layout.setError("Вкажіть номер телефону правильно");
            validData = false;
        } else phone.layout.setError(null);

        String password = this.password.editText.getText().toString();
        String confirmPassword = this.confirmPassword.editText.getText().toString();

        if (password.isEmpty()) {
            this.confirmPassword.layout.setError(null);
            this.password.layout.setError("Введіть пароль");
            validData = false;
        } else if (confirmPassword.isEmpty()) {
            this.password.layout.setError(null);
            this.confirmPassword.layout.setError("Підтвердіть пароль");
            validData = false;
        } else if (!Validator.passwordEqual(password, confirmPassword)) {
            this.password.layout.setError("Паролі повинні збігатися");
            this.confirmPassword.layout.setError("Паролі повинні збігатися");
            validData = false;
        } else {
            this.password.layout.setError(null);
            this.confirmPassword.layout.setError(null);
        }


        if (!validData && photoBase64.isEmpty() &&
                firstName.editText.getText().toString().isEmpty() &&
                secondName.editText.getText().toString().isEmpty()) return;

        RegisterDTO registerDTO = new RegisterDTO();

        registerDTO.setEmail(email.editText.getText().toString());
        registerDTO.setPhone(phone.editText.getText().toString());
        registerDTO.setFirstName(firstName.editText.getText().toString());
        registerDTO.setSecondName(secondName.editText.getText().toString());
        registerDTO.setPhoto(photoBase64);
        registerDTO.setPassword(password);
        registerDTO.setConfirmPassword(confirmPassword);

        RequestService.getInstance().jsonAccountApi().register(registerDTO)
                .enqueue(new Callback<RegisterResponseDTO>() {
                    @Override
                    public void onResponse(Call<RegisterResponseDTO> call, Response<RegisterResponseDTO> response) {
                        RegisterResponseDTO data = response.body();
                    }

                    @Override
                    public void onFailure(Call<RegisterResponseDTO> call, Throwable t) {
                        String str = t.toString();
                        int a = 12;
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
}
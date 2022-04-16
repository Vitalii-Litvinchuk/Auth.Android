package com.example.newmail.account;

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

import com.example.newmail.MainActivity;
import com.example.newmail.R;
import com.example.newmail.application.HomeApplication;
import com.example.newmail.constants.Methods;
import com.example.newmail.constants.TextInputHelper;
import com.example.newmail.constants.Validator;
import com.example.newmail.simplification.EasierActivity;
import com.example.newmail.network.request.DTOs.AccountDTOs.RegisterDTO;
import com.example.newmail.network.request.DTOs.AccountDTOs.ErrorDTO;
import com.example.newmail.network.request.DTOs.AccountDTOs.AccountResponseDTO;
import com.example.newmail.network.request.RequestService;
import com.example.newmail.security.JwtSecurityService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends EasierActivity {
    TextView imgError;
    ProgressBar progBar;
    Button btn;

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
        imgError = findViewById(R.id.imgErrorRegister);
        myImage = findViewById(R.id.myimgRegister);

        progBar = findViewById(R.id.progBarRegister);
        btn = findViewById(R.id.buttonRegister);


        email = new TextInputHelper(findViewById(R.id.emailRegister), findViewById(R.id.txtEmailRegister));
        firstName = new TextInputHelper(findViewById(R.id.firstNameRegister), findViewById(R.id.txtFirstNameRegister));
        secondName = new TextInputHelper(findViewById(R.id.secondNameRegister), findViewById(R.id.txtSecondNameRegister));
        phone = new TextInputHelper(findViewById(R.id.phoneRegister), findViewById(R.id.txtPhoneRegister));
        password = new TextInputHelper(findViewById(R.id.passwordRegister), findViewById(R.id.txtPasswordRegister));
        confirmPassword = new TextInputHelper(findViewById(R.id.confirmPasswordRegister), findViewById(R.id.txtConfirmPasswordRegister));

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

        String password1 = this.password.editText.getText().toString();
        String confirmPassword1 = this.confirmPassword.editText.getText().toString();

        if (password1.isEmpty()) {
            this.confirmPassword.layout.setError(null);
            this.password.layout.setError("Введіть пароль");
            validData = false;
        } else if (confirmPassword1.isEmpty()) {
            this.password.layout.setError(null);
            this.confirmPassword.layout.setError("Підтвердіть пароль");
            validData = false;
        } else if (!Validator.passwordEqual(password1, confirmPassword1)) {
            this.password.layout.setError("Паролі повинні збігатися");
            this.confirmPassword.layout.setError("Паролі повинні збігатися");
            validData = false;
        } else {
            this.password.layout.setError(null);
            this.confirmPassword.layout.setError(null);
        }


        if (validData) {
            Methods.enableProgressBarWithButton(progBar, btn);
            RegisterDTO registerDTO = new RegisterDTO();

            registerDTO.setEmail(email.editText.getText().toString());
            registerDTO.setPhone(phone.editText.getText().toString());
            registerDTO.setFirstName(firstName.editText.getText().toString());
            registerDTO.setSecondName(secondName.editText.getText().toString());
            registerDTO.setPhoto(photoBase64);
            registerDTO.setPassword(password1);
            registerDTO.setConfirmPassword(confirmPassword1);
            RequestService.getInstance().jsonAccountApi().register(registerDTO)
                    .enqueue(new Callback<AccountResponseDTO>() {
                        @Override
                        public void onResponse(Call<AccountResponseDTO> call, Response<AccountResponseDTO> response) {
                            if (response.isSuccessful()) {
                                AccountResponseDTO data = response.body();
                                String token = data.getToken();

                                JwtSecurityService jwtService = (JwtSecurityService) HomeApplication.getInstance();
                                jwtService.saveJwtToken(token);
                                auth();
                                openMainActivity();
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
                        public void onFailure(Call<AccountResponseDTO> call, Throwable t) {
                        }
                    });
        }
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
                    bitmap = Picasso.get()
                            .load(uri)
                                                   .resize(100, 100)
//                            .centerCrop()
                            .get();
                } catch (IOException e) {
//                    e.printStackTrace();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
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

        String[] passwordError = error.getErrors().getPassword();
        if (passwordError != null) {
            password.layout.setError(passwordError[0]);
        } else password.layout.setError(null);

        String[] confPasswordError = error.getErrors().getConfirmPassword();
        if (confPasswordError != null) {
            confirmPassword.layout.setError(confPasswordError[0]);
        } else confirmPassword.layout.setError(null);

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
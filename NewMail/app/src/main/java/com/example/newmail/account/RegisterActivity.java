package com.example.newmail.account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.newmail.R;
import com.example.newmail.constants.TextInputHelper;
import com.example.newmail.constants.Validator;

public class RegisterActivity extends AppCompatActivity {
    TextInputHelper email;
    TextInputHelper firstName;
    TextInputHelper secondName;
    TextInputHelper phone;
    TextInputHelper password;
    TextInputHelper confirmPassword;

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
    }

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



//        RegisterDTO registerDTO = new RegisterDTO();
//        registerDTO.setEmail("ss@gmail.com");
//
//        AccountService.getInstance()
//                .jsonApi()
//                .register(registerDTO)
//                .enqueue(new Callback<AccountResponseDTO>() {
//                    @Override
//                    public void onResponse(Call<AccountResponseDTO> call, Response<AccountResponseDTO> response) {
//                        AccountResponseDTO data = response.body();
////                        tvInfo.setText("response is good");
//                    }
//
//                    @Override
//                    public void onFailure(Call<AccountResponseDTO> call, Throwable t) {
//                        String str = t.toString();
//                        int a =12;
//                    }
//                });
    }
}
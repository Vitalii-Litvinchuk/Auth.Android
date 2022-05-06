package com.example.newmail.account;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.newmail.R;
import com.example.newmail.application.HomeApplication;
import com.example.newmail.constants.Methods;
import com.example.newmail.constants.TextInputHelper;
import com.example.newmail.constants.Validator;
import com.example.newmail.simplification.BaseActivity;
import com.example.newmail.network.request.DTOs.AccountDTOs.AccountResponseDTO;
import com.example.newmail.network.request.DTOs.AccountDTOs.ErrorDTO;
import com.example.newmail.network.request.DTOs.AccountDTOs.LoginDTO;
import com.example.newmail.network.request.RequestService;
import com.example.newmail.security.JwtSecurityService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    TextView txt;
    ProgressBar progBar;
    Button btn;

    TextInputHelper email;
    TextInputHelper password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txt = findViewById(R.id.textLogin);
        progBar = findViewById(R.id.progBarLogin);
        btn = findViewById(R.id.buttonLogin);

        email = new TextInputHelper(findViewById(R.id.emailLogin), findViewById(R.id.txtEmailLogin));
        password = new TextInputHelper(findViewById(R.id.passwordLogin), findViewById(R.id.txtPasswordLogin));

        email.editText.setText("qwerty@qwe.rty");
        password.editText.setText("qwerty");
    }

    public void handleClick(View view) {
        boolean validData = true;
        String emailText = email.editText.getText().toString();
        String passwordText = password.editText.getText().toString();

        if (!Validator.emailValidate(emailText)) {
            email.layout.setError("Вкажіть пошту правильно");
            validData = false;
        } else email.layout.setError(null);

        if (passwordText.isEmpty()) {
            password.layout.setError("Вкажіть пароль");
            validData = false;
        } else password.layout.setError(null);

        if (validData) {
            Methods.enableProgressBarWithButton(progBar, btn);
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setEmail(emailText);
            loginDTO.setPassword(passwordText);
//            (new Handler()).postDelayed(() -> {
                RequestService.getInstance().jsonAccountApi().login(loginDTO)
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
//            }, 2000);
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

        String global = error.getErrors().getGlobal();
        if (global != null) {
            txt.setText(global);
        } else txt.setText("Вхід");
    }
}
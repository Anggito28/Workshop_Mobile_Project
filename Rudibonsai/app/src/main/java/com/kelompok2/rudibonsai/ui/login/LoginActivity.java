package com.kelompok2.rudibonsai.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.kelompok2.rudibonsai.MainActivity;
import com.kelompok2.rudibonsai.R;
import com.kelompok2.rudibonsai.api.ApiClient;
import com.kelompok2.rudibonsai.api.LoginInterface;
import com.kelompok2.rudibonsai.model.login.LoginData;
import com.kelompok2.rudibonsai.model.login.LoginError;
import com.kelompok2.rudibonsai.model.login.LoginSuccess;
import com.kelompok2.rudibonsai.session.SessionManager;
import com.kelompok2.rudibonsai.ui.register.RegisterWebViewActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActionBar actionBar;
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private String email, password;
    private LoginInterface loginInterface;
    private SessionManager sessionManager;
    private TextView linkRegister;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(LoginActivity.this);

        actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.login);

        etEmail = findViewById(R.id.et_login_email);
        etPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login_login);
        linkRegister = findViewById(R.id.link_register);

        loading = new ProgressDialog(LoginActivity.this);
        loading.setTitle(R.string.loading);
        loading.setMessage(this.getResources().getString(R.string.please_wait));
        loading.setCancelable(false);

        btnLogin.setOnClickListener(this);
        linkRegister.setOnClickListener(this);
    }

    private void moveToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_login:
                loading.show();

                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                login(email, password);

                break;

            case R.id.link_register:
                moveToRegister();

                break;
        }
    }

    private void moveToRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterWebViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    private void login(String email, String password) {
        loginInterface = ApiClient.getClient().create(LoginInterface.class);
        Call<JsonElement> call = loginInterface.loginResponse(email, password);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()){
                    JsonElement res = response.body();
                    LoginSuccess loginSuccess = new Gson().fromJson(res.getAsJsonObject(), LoginSuccess.class);
                    LoginData loginData = loginSuccess.getData();

//                    sessionManager = new SessionManager(LoginActivity.this);
                    sessionManager.createLoginSession(loginData);

                    loading.dismiss();

                    moveToMain();

                } else {
                    
                    try {
                        String jsonString = response.errorBody().string();

                        Gson g = new Gson();
                        LoginError loginError = g.fromJson(jsonString, LoginError.class);

                        Toast.makeText(LoginActivity.this, loginError.getMessage(), Toast.LENGTH_SHORT).show();

                        Log.i("errorBody", jsonString);
                        Log.i("errorResponse", loginError.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                loading.dismiss();
            }
        });

    }
}
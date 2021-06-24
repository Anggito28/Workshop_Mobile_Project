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
import com.kelompok2.rudibonsai.model.login.LoginSuccess;
import com.kelompok2.rudibonsai.session.SessionManager;
import com.kelompok2.rudibonsai.ui.register.RegisterActivity;

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
        actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.login);

        etEmail = findViewById(R.id.et_login_email);
        etPassword = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login_login);
        linkRegister = findViewById(R.id.link_register);

        loading = new ProgressDialog(LoginActivity.this);
        loading.setTitle("Memuat");
        loading.setMessage("Mohon tunggu...");
        loading.setCancelable(false);

        btnLogin.setOnClickListener(this);
        linkRegister.setOnClickListener(this);
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
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
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

                    sessionManager = new SessionManager(LoginActivity.this);
                    sessionManager.createLoginSession(loginData);

                    loading.dismiss();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

//                    Toast.makeText(LoginActivity.this, "oke", Toast.LENGTH_SHORT).show();

                } else {
                    Log.i("tes", String.valueOf(response.raw()));
                    Log.i("tes", String.valueOf(response.body()));
                    Log.i("tes", String.valueOf(response.errorBody()));

                    loading.dismiss();

                    Toast.makeText(LoginActivity.this, "not oke", Toast.LENGTH_SHORT).show();
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
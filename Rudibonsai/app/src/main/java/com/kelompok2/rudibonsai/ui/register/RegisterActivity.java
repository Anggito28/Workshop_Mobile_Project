package com.kelompok2.rudibonsai.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.kelompok2.rudibonsai.R;

public class RegisterActivity extends AppCompatActivity {
    ActionBar actionBar;
    EditText etEmail, etPassword, etPasswordConfirm ;
    Button btnNext;
    String email, password, passwordConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.register);

        etEmail = findViewById(R.id.et_register_email);
        etPassword = findViewById(R.id.et_register_password);
        etPasswordConfirm = findViewById(R.id.et_register_password_confirm);
        btnNext = findViewById(R.id.btn_next);

        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        passwordConfirm = etPasswordConfirm.getText().toString();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!password.equals(passwordConfirm)){
                    etPassword.setError("passoword tidak sama");
                    return;
                }else {
                    Intent intent = new Intent(RegisterActivity.this , RegisterActivitySecond.class);
                    intent.putExtra("email",email);
                    intent.putExtra("password",password);

                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
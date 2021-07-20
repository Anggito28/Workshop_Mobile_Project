package com.kelompok2.rudibonsai.ui.register;

import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.kelompok2.rudibonsai.R;

public class RegisterActivitySecond extends AppCompatActivity {
    ActionBar actionBar;
    EditText etname, etphone, etaddress;
    RadioGroup etgender;
    Button etprov, etkab, etkec;
    String name, phone, gender, address, prov, kab, kec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Daftar");

        etname = findViewById(R.id.et_name);
        etphone = findViewById(R.id.et_phone);
        etaddress = findViewById(R.id.et_address);

        name = etname.getText().toString();
        phone = etphone.getText().toString();
        address = etaddress.getText().toString();

    }
}
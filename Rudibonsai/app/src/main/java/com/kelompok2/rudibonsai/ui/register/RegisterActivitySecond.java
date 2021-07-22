package com.kelompok2.rudibonsai.ui.register;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.kelompok2.rudibonsai.R;

public class RegisterActivitySecond extends AppCompatActivity {
    ActionBar actionBar;
    EditText etname, etphone, etaddress, etpilihprov, etpilihkab, etpilihkec;
    String name, phone, gender, address, prov, kab, kec;
    RadioGroup etgender;
    Button btn_daftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Daftar");

        etname = findViewById(R.id.et_name);
        etphone = findViewById(R.id.et_phone);
        etaddress = findViewById(R.id.et_address);
        etpilihprov = findViewById(R.id.pilihprov);
        etpilihkab = findViewById(R.id.pilihkab);
        etpilihkec = findViewById(R.id.pilihkec);
//        etgender = findViewById(R.id.et_gender);

        name = etname.getText().toString();
        phone = etphone.getText().toString();
        address = etaddress.getText().toString();
        prov = etpilihprov.getText().toString();
        kab = etpilihkab.getText().toString();
        kec = etpilihkec.getText().toString();
//        gender = etgender

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

    }
}
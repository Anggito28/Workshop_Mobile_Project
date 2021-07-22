package com.kelompok2.rudibonsai.ui.produk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelompok2.rudibonsai.R;
import com.kelompok2.rudibonsai.model.cart.get.CartsItem;

public class detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView image = findViewById(R.id.image);
        TextView detail = findViewById(R.id.detail);
        TextView home = findViewById(R.id.home);
        TextView harga = findViewById(R.id.harga);
        TextView satu = findViewById(R.id.satu);
        TextView dua = findViewById(R.id.dua);
        Button button1 = findViewById(R.id.button1);

        Button btn1 = (Button)findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent pindah = new Intent(com.kelompok2.rudibonsai.ui.produk.detail.this, CartsItem.class);
                startActivity(pindah);

            }
        });


    }
}
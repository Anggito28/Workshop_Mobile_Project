package com.kelompok2.rudibonsai.ui.produk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelompok2.rudibonsai.R;

public class DetailProduk extends AppCompatActivity {
public static final String ITEM_EXTRA = "item_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);

        ImageView imgproduk = findViewById(R.id.imgproduk);
        TextView imgproduk = findViewById(R.id.imgproduk);
        TextView tvdetailproduk = findViewById(R.id.tvdetailproduk);

        Produk produk = getIntent().getParcelableExtra(ITEM_EXTRA);
        if(produk != null){
            Glide.with(activity: this)
            .load(produk.getPhoto())
                    .into(imgproduk);
            tvdetailproduk.setText(produk.getName());
            tvdetailproduk.setText(produk.getName());

        }
        if(getSupportActionBar()!= null){
            getSupportActionBar().setTitle("detail produk");
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
    }
}
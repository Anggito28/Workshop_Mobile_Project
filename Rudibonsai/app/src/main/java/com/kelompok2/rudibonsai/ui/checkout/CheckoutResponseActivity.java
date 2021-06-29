package com.kelompok2.rudibonsai.ui.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.kelompok2.rudibonsai.MainActivity;
import com.kelompok2.rudibonsai.R;

public class CheckoutResponseActivity extends AppCompatActivity {

    TextView tvMessage;
    ImageView ivStatus;
    Button btnNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_response);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        tvMessage = findViewById(R.id.tv_checkout_response);
        ivStatus = findViewById(R.id.iv_checkout_response);
        btnNav = findViewById(R.id.btn_checkout_response_nav);
        Intent intent = getIntent();

        String status = intent.getStringExtra("checkout_status");
        String message = intent.getStringExtra("checkout_message");

        if (!status.equals("success")){
            tvMessage.setTextColor(getResources().getColor(R.color.design_default_color_error));
            ivStatus.setImageResource(R.drawable.ic_baseline_error_outline_256);
            ivStatus.setColorFilter(ContextCompat.getColor(CheckoutResponseActivity.this, R.color.design_default_color_error));
        }

        tvMessage.setText(message);

        btnNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckoutResponseActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
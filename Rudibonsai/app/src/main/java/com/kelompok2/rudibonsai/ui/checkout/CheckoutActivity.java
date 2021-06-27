package com.kelompok2.rudibonsai.ui.checkout;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.kelompok2.rudibonsai.R;
import com.kelompok2.rudibonsai.session.SessionManager;
import com.kelompok2.rudibonsai.ui.cart.CartAdapter;
import com.kelompok2.rudibonsai.utils.MyFormatter;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SessionManager sessionManager;
    TextView tvFullname, tvPhoneEmail, tvAddress, tvAddressDetail, tvSubtotal, tvTotal, tvShipping;
    Button btnOrder;
    TextInputLayout tilChooseCourier, tilChooseService;
    AutoCompleteTextView acCourier;
    ActionBar actionBar;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.checkout);
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rv_checkout_item);
        tvFullname = findViewById(R.id.tv_checkout_user_fullname);
        tvPhoneEmail = findViewById(R.id.tv_checkout_user_phone_email);
        tvAddress = findViewById(R.id.tv_checkout_address);
        tvAddressDetail = findViewById(R.id.tv_checkout_address_detail);
        tvSubtotal = findViewById(R.id.tv_checkout_subtotal);
        tvTotal = findViewById(R.id.tv_checkout_total);
        tvShipping = findViewById(R.id.tv_checkout_shipping_cost);
        btnOrder = findViewById(R.id.btn_checkout_order);
        sessionManager = new SessionManager(this);
        tilChooseCourier = findViewById(R.id.til_checkout_courier);
        acCourier = findViewById(R.id.ac_checkout_courier);

        tvFullname.setText(sessionManager.getFULLNAME());
        String phoneEmail = sessionManager.getPHONE() + " • " + sessionManager.getEMAIL();
        tvPhoneEmail.setText(phoneEmail);
        String address = sessionManager.getPROVINCE() + ", " + sessionManager.getCITY() + ", " + sessionManager.getSUBDISTRICT();
        tvAddress.setText(address);
        tvAddressDetail.setText(sessionManager.getAddressDetail());
        tvSubtotal.setText(getSubtotalAmount());

        CheckoutItemAdapter adapter = new CheckoutItemAdapter(this, CartAdapter.mData, CartAdapter.quantities, CartAdapter.subtotalItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        List<String> couriers = List.of("jne", "pos", "tiki");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item_courier, couriers);
        acCourier.setAdapter(arrayAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getSubtotalAmount() {
        int subtotal = 0;
        for (int item : CartAdapter.subtotalItem){
            subtotal += item;
        }

        return MyFormatter.idrFormat(subtotal);
    }
}
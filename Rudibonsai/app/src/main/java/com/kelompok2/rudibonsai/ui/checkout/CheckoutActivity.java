package com.kelompok2.rudibonsai.ui.checkout;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.kelompok2.rudibonsai.R;
import com.kelompok2.rudibonsai.api.ApiClient;
import com.kelompok2.rudibonsai.api.RajaongkirInterface;
import com.kelompok2.rudibonsai.model.rajaongkir.CostsItem;
import com.kelompok2.rudibonsai.model.rajaongkir.RajaongkirResponse;
import com.kelompok2.rudibonsai.session.SessionManager;
import com.kelompok2.rudibonsai.ui.cart.CartAdapter;
import com.kelompok2.rudibonsai.utils.MyFormatter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SessionManager sessionManager;
    TextView tvFullname, tvPhoneEmail, tvAddress, tvAddressDetail, tvSubtotal, tvTotal, tvShipping;
    Button btnOrder;
    AutoCompleteTextView acCourier, acService;
    ActionBar actionBar;
    RajaongkirInterface rajaongkirInterface;
    TextInputLayout tilService;
    List<CostsItem> shippingCost;

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
        acCourier = findViewById(R.id.ac_checkout_courier);
        acService = findViewById(R.id.ac_checkout_service);
        tilService = findViewById(R.id.til_checkout_service);

        tvFullname.setText(sessionManager.getFULLNAME());
        String phoneEmail = sessionManager.getPHONE() + " • " + sessionManager.getEMAIL();
        tvPhoneEmail.setText(phoneEmail);
        String address = sessionManager.getPROVINCE() + ", " + sessionManager.getCITY() + ", " + sessionManager.getSUBDISTRICT();
        tvAddress.setText(address);
        tvAddressDetail.setText(sessionManager.getAddressDetail());
        tvSubtotal.setText(getSubtotalAmount());
        setCheckoutItemList();
        setCourierList();

        acCourier.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] couriers = {"jne", "pos", "tiki"};

                btnOrder.setEnabled(false);
                tilService.setEnabled(false);
                tilService.setHint("Loading...");
                acService.setText("");
                tvTotal.setText("Rp 0");
                tvShipping.setText("Rp 0");

                String courier = couriers[position];
                int destination = sessionManager.getCityId();
                int totalWeight = countTotalWeight();

                rajaongkirInterface = ApiClient.getClient().create(RajaongkirInterface.class);
                Call<RajaongkirResponse> rajaongkirResponseCall = rajaongkirInterface.getShippingCost(courier, destination, totalWeight);
                rajaongkirResponseCall.enqueue(new Callback<RajaongkirResponse>() {
                    @Override
                    public void onResponse(Call<RajaongkirResponse> call, Response<RajaongkirResponse> response) {
                        if (response.isSuccessful()){
                            tilService.setEnabled(true);
                            tilService.setHint(R.string.choose_shipping_service);

                            shippingCost = response.body().getRajaongkir().getResults().get(0).getCosts();

                            setServiceList(shippingCost);
                        }else {
                            if (response.errorBody() != null){
                                Toast.makeText(CheckoutActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }

                            Toast.makeText(CheckoutActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                            tilService.setHint(R.string.choose_shipping_service);
                        }
                    }

                    @Override
                    public void onFailure(Call<RajaongkirResponse> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(CheckoutActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        tilService.setHint(R.string.choose_shipping_service);
                    }
                });

            }
        });

        acService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedCost = shippingCost.get(position).getCost().get(0).getValue();

                String ongkirText = MyFormatter.idrFormat(selectedCost);
                tvShipping.setText(ongkirText);

                String total = getTotalAmount(position, selectedCost);
                tvTotal.setText(total);

                btnOrder.setEnabled(true);
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setCheckoutItemList() {
        CheckoutItemAdapter adapter = new CheckoutItemAdapter(this, CartAdapter.mData, CartAdapter.itemQty, CartAdapter.itemSubtotal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private String getTotalAmount(int position, int selectedCost) {
        int subtotal = 0;
        for (int item : CartAdapter.itemSubtotal){
            subtotal += item;
        }

        int total = selectedCost + subtotal;

        return MyFormatter.idrFormat(total);
    }

    private void setServiceList(List<CostsItem> costs) {
        ArrayList<String> service = new ArrayList<>();

        int i = 0;
        for (CostsItem item : costs){
            int itemValue = item.getCost().get(0).getValue();
            String listItem = item.getService() + " | " + MyFormatter.idrFormat(itemValue);
            service.add(listItem);
        }

        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<>(this, R.layout.list_item_text_input_dropdown, service);
        acService.setAdapter(serviceAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void setCourierList() {
        List<String> couriers = List.of("JNE", "POS Indonesia", "TIKI");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item_text_input_dropdown, couriers);
        acCourier.setAdapter(arrayAdapter);
    }

    private int countTotalWeight() {
        int total = 0;

        for (int item : CartAdapter.itemWeight){
            total += item;
        }

        return total;
    }

    private String getSubtotalAmount() {
        int subtotal = 0;
        for (int item : CartAdapter.itemSubtotal){
            subtotal += item;
        }

        return MyFormatter.idrFormat(subtotal);
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
}
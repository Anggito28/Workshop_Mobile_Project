package com.kelompok2.rudibonsai.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kelompok2.rudibonsai.R;
import com.kelompok2.rudibonsai.api.ApiClient;
import com.kelompok2.rudibonsai.api.OrderInterface;
import com.kelompok2.rudibonsai.model.order.get.detail.Order;
import com.kelompok2.rudibonsai.model.order.get.detail.OrderDetail;
import com.kelompok2.rudibonsai.model.order.get.detail.OrderDetailItem;
import com.kelompok2.rudibonsai.session.SessionManager;
import com.kelompok2.rudibonsai.utils.MyFormatter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {

    SessionManager sessionManager;
    TextView status, total, subtotal, shippingCost, fullname, phoneEmail, address, addressDetail;
    AutoCompleteTextView courier, shippingService;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Detail Pesanan");
        actionBar.setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(this);
        status = findViewById(R.id.tv_order_detail_status);
        total = findViewById(R.id.tv_order_detail_total);
        subtotal = findViewById(R.id.tv_order_detail_subtotal);
        shippingCost = findViewById(R.id.tv_order_detail_shipping_cost);
        fullname = findViewById(R.id.tv_order_detail_user_fullname);
        phoneEmail = findViewById(R.id.tv_order_detail_user_phone_email);
        address = findViewById(R.id.tv_order_detail_address);
        addressDetail = findViewById(R.id.tv_order_detail_address_detail);
        courier = findViewById(R.id.ac_order_detail_courier);
        shippingService = findViewById(R.id.ac_order_detail_service);
        recyclerView = findViewById(R.id.rv_order_detail_item);

        Intent intent = getIntent();
        int orderId = intent.getIntExtra("order_id", 0);

        fetchOrderDetail(orderId);
    }

    private void fetchOrderDetail(int orderId) {
        String token = "Bearer " + sessionManager.getTOKEN();

        OrderInterface orderInterface = ApiClient.getClient().create(OrderInterface.class);
        Call<OrderDetail> orderDetailCall = orderInterface.getOrderDetail(token, orderId);
        orderDetailCall.enqueue(new Callback<OrderDetail>() {
            @Override
            public void onResponse(Call<OrderDetail> call, Response<OrderDetail> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(OrderDetailActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                    return;
                }

                putDataToActivity(response.body().getOrder());
                putDataToRecyclerView(response.body().getOrderDetail());
            }

            @Override
            public void onFailure(Call<OrderDetail> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(OrderDetailActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void putDataToActivity(Order order) {
        status.setText(order.getStatus());
        total.setText(MyFormatter.idrFormat(order.getGrandTotalAmount()));
        subtotal.setText(MyFormatter.idrFormat(order.getProductTotalAmount()));
        shippingCost.setText(MyFormatter.idrFormat(order.getShippingCost()));
        fullname.setText(sessionManager.getFULLNAME());

        String phoneMailStr = sessionManager.getPHONE() + " • " + sessionManager.getEMAIL();
        phoneEmail.setText(phoneMailStr);

        String addressStr = sessionManager.getPROVINCE() + ", " + sessionManager.getCITY() + ", " + sessionManager.getSUBDISTRICT();
        address.setText(addressStr);

        addressDetail.setText(sessionManager.getAddressDetail());

        String courierStr = "";
        switch (order.getShippingAgent()){
            case "jne":
                courierStr = "JNE";
                break;
            case "pos":
                courierStr = "POS Indonesia";
                break;
            case "tiki":
                courierStr = "TIKI";
                break;
        }
        courier.setHint(courierStr);
        shippingService.setHint(order.getShippingService());
    }

    private void putDataToRecyclerView(List<OrderDetailItem> orderDetail) {
        OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(orderDetail, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderDetailAdapter);
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
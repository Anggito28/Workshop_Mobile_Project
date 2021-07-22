package com.kelompok2.rudibonsai.ui.order;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kelompok2.rudibonsai.R;
import com.kelompok2.rudibonsai.api.ApiClient;
import com.kelompok2.rudibonsai.api.OrderInterface;
import com.kelompok2.rudibonsai.constant.ConstantValue;
import com.kelompok2.rudibonsai.model.order.get.detail.Order;
import com.kelompok2.rudibonsai.model.order.get.detail.OrderDetail;
import com.kelompok2.rudibonsai.model.order.get.detail.OrderDetailItem;
import com.kelompok2.rudibonsai.model.order.payment.OrderPaymentUploadResponse;
import com.kelompok2.rudibonsai.session.SessionManager;
import com.kelompok2.rudibonsai.utils.MyFormatter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import droidninja.filepicker.utils.ContentUriUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {

    SessionManager sessionManager;
    TextView status, total, subtotal, shippingCost, fullname, phoneEmail, address, addressDetail, expAt, courier, shippingService;
    RecyclerView recyclerView;
    ImageView ivChooseImg;
    Button btnUpload;
    String imgPath;
    String token;
    OrderInterface orderInterface;
    ProgressDialog loading;

    private static final int PICK_IMAGE_REQUEST = 9544;

    // Permissions for accessing the storage
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Detail Pesanan");
        actionBar.setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(this);
        token = "Bearer " + sessionManager.getTOKEN();
        orderInterface = ApiClient.getClient().create(OrderInterface.class);

        loading = new ProgressDialog(OrderDetailActivity.this);
        loading.setCancelable(false);

        status = findViewById(R.id.tv_order_detail_status);
        total = findViewById(R.id.tv_order_detail_total);
        subtotal = findViewById(R.id.tv_order_detail_subtotal);
        shippingCost = findViewById(R.id.tv_order_detail_shipping_cost);
        fullname = findViewById(R.id.tv_order_detail_user_fullname);
        phoneEmail = findViewById(R.id.tv_order_detail_user_phone_email);
        address = findViewById(R.id.tv_order_detail_address);
        addressDetail = findViewById(R.id.tv_order_detail_address_detail);
        courier = findViewById(R.id.tv_order_detail_courier);
        shippingService = findViewById(R.id.tv_order_detail_service);
        recyclerView = findViewById(R.id.rv_order_detail_item);
        ivChooseImg = findViewById(R.id.iv_order_detail_choose_img);
        btnUpload = findViewById(R.id.btn_order_detail_upload);
        expAt = findViewById(R.id.tv_order_detail_exp_at);

        btnUpload.setEnabled(false);

        Intent intent = getIntent();
        int orderId = intent.getIntExtra("order_id", 0);

        fetchOrderDetail(orderId);

        ivChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
                btnUpload.setEnabled(true);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(orderId);
            }
        });
    }

    private void uploadImage(int orderId) {
        loading.setMessage("Mengunggah file...");
        loading.show();
        File imageFile = new File(imgPath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("payment_proof", imageFile.getName(), requestBody);

        Call<OrderPaymentUploadResponse> uploadResponseCall = orderInterface.uploadPayment(token, orderId, partImage);
        uploadResponseCall.enqueue(new Callback<OrderPaymentUploadResponse>() {
            @Override
            public void onResponse(Call<OrderPaymentUploadResponse> call, Response<OrderPaymentUploadResponse> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    Toast.makeText(OrderDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    fetchOrderDetail(orderId);
                }else {
                    loading.dismiss();

                    Toast.makeText(OrderDetailActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    try {
                        Log.i("not_success", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderPaymentUploadResponse> call, Throwable t) {
                loading.dismiss();

                t.printStackTrace();
                Toast.makeText(OrderDetailActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pickImage() {
        verifyStoragePermissions(OrderDetailActivity.this);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Open Gallery"), PICK_IMAGE_REQUEST);
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case PICK_IMAGE_REQUEST:
                if (resultCode== Activity.RESULT_OK && data!=null) {
                    Uri selectedImg = data.getData();

                    ivChooseImg.setImageURI(selectedImg);

                    try {
                        imgPath = ContentUriUtils.INSTANCE.getFilePath(OrderDetailActivity.this, selectedImg);

                        Toast.makeText(this, imgPath, Toast.LENGTH_SHORT).show();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                }
                if (data == null){
                    btnUpload.setEnabled(false);
                }
                break;
        }

    }

    private void fetchOrderDetail(int orderId) {
        loading.setMessage("Memuat...");
        loading.show();

        Call<OrderDetail> orderDetailCall = orderInterface.getOrderDetail(token, orderId);
        orderDetailCall.enqueue(new Callback<OrderDetail>() {
            @Override
            public void onResponse(Call<OrderDetail> call, Response<OrderDetail> response) {
                if (!response.isSuccessful()){
                    loading.dismiss();
                    Toast.makeText(OrderDetailActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                    return;
                }

                putDataToActivity(response.body().getOrder());
                putDataToRecyclerView(response.body().getOrderDetail());
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<OrderDetail> call, Throwable t) {
                loading.dismiss();
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

        courier.setText(courierStr);
        shippingService.setText(order.getShippingService());

        String imgName = order.getPaymentProof();

        if (!imgName.equals("empty")){
            String imgPath = ConstantValue.BASE_URL + "orders/payment-proof/" + imgName;

            Glide.with(getApplication())
                    .load(imgPath)
                    .into(ivChooseImg);
        }

        String expDateStr = order.getExpiredAt();

        expAt.setText(expDateStr);

        checkExpDate(expDateStr);

    }

    private void checkExpDate(String expDateStr) {
        SimpleDateFormat dateTimeInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateOutput = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateOutput.setTimeZone(TimeZone.getTimeZone("GMT+7"));

        Date expDate = null;

        try
        {
            expDate = dateTimeInput.parse(expDateStr);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        if (new Date().after(expDate)){
            btnUpload.setVisibility(View.GONE);
            ivChooseImg.setOnClickListener(null);
        }

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
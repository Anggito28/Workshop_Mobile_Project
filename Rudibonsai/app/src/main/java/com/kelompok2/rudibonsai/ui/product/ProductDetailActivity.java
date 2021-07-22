package com.kelompok2.rudibonsai.ui.product;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonElement;
import com.kelompok2.rudibonsai.R;
import com.kelompok2.rudibonsai.api.ApiClient;
import com.kelompok2.rudibonsai.api.CartInterface;
import com.kelompok2.rudibonsai.api.ProductInterface;
import com.kelompok2.rudibonsai.model.cart.get.CartGetResponse;
import com.kelompok2.rudibonsai.model.cart.get.CartsItem;
import com.kelompok2.rudibonsai.model.product.detail.ProductDetailResponse;
import com.kelompok2.rudibonsai.model.product.detail.ProductImagesItem;
import com.kelompok2.rudibonsai.session.SessionManager;
import com.kelompok2.rudibonsai.utils.MyFormatter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    TextView title, price, stock, dimension, weight, category, desc;
    Button btnAddToCart;
    RecyclerView rvImgs;
    SessionManager sessionManager;
    String token;
    private ProgressDialog progressDialog;
    ProductDetailResponse dataProduct;
    CartGetResponse dataCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Detail Produk");
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Memuat...");

        title = findViewById(R.id.tv_product_detail_title);
        price = findViewById(R.id.tv_product_detail_price);
        stock = findViewById(R.id.tv_product_detail_stock);
        dimension = findViewById(R.id.tv_product_detail_dimension);
        weight = findViewById(R.id.tv_product_detail_weight);
        category = findViewById(R.id.tv_product_detail_category);
        desc = findViewById(R.id.tv_product_detail_desc);
        btnAddToCart = findViewById(R.id.btn_product_detail_add_to_cart);
        rvImgs = findViewById(R.id.rv_product_detail_imgs);

        sessionManager = new SessionManager(this);
        token = "Bearer " + sessionManager.getTOKEN();

        Intent intent = getIntent();
        int productId = intent.getIntExtra("product_id", 0);

        fetchProductDetail(productId);

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoCart();
            }
        });
    }

    private void addtoCart() {
        progressDialog.show();

        CartInterface cartInterface = ApiClient.getClient().create(CartInterface.class);
        Call<JsonElement> addToCartCall = cartInterface.addToCart(token, sessionManager.getUserId(), dataProduct.getId());
        addToCartCall.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (!response.isSuccessful()){
                    Log.i("fail", String.valueOf(response));
                    Toast.makeText(ProductDetailActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();
                    return;
                }

                progressDialog.dismiss();
                btnAddToCart.setEnabled(false);
                btnAddToCart.setText("Produk Ditambahkan");

                Toast.makeText(ProductDetailActivity.this, "Produk berhasil ditambahkan ke keranjang", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(ProductDetailActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchProductDetail(int productId) {
        progressDialog.show();

        ProductInterface productInterface = ApiClient.getClient().create(ProductInterface.class);
        Call<ProductDetailResponse> productDetailResponseCall = productInterface.getProductDetail(token, productId);
        productDetailResponseCall.enqueue(new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                if (!response.isSuccessful()){
                    Log.i("fail", String.valueOf(response));
                    Toast.makeText(ProductDetailActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();
                    return;
                }

                dataProduct = response.body();

                getCarts();

                putDataToActivity(dataProduct);
                putImageToRecyclerView(response.body().getProductImages());
            }

            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(ProductDetailActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void putImageToRecyclerView(List<ProductImagesItem> productImages) {
        ProductImageAdapter productImageAdapter = new ProductImageAdapter(ProductDetailActivity.this, productImages);
        rvImgs.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvImgs.setAdapter(productImageAdapter);
    }

    private void putDataToActivity(ProductDetailResponse body) {
        title.setText(body.getName());
        price.setText(MyFormatter.idrFormat(body.getPrice()));

        String strStock = "Stok : " + body.getStock();
        stock.setText(strStock);

        String length = body.getLength() + "cm X ";
        String width = body.getWidth() + "cm X ";
        String height = body.getHeight() + "cm";
        String strDimension = "Dimensi : " + length + width + height;
        dimension.setText(strDimension);

        String strWeight = "Berat : " + String.valueOf(body.getWeight()) + "gram";
        weight.setText(strWeight);

        category.setText(body.getProductCategory().getName());
        desc.setText(body.getDescription());
    }

    private void getCarts() {
        CartInterface cartInterface = ApiClient.getClient().create(CartInterface.class);
        Call<CartGetResponse> cartCall = cartInterface.getCarts(token);
        cartCall.enqueue(new Callback<CartGetResponse>() {
            @Override
            public void onResponse(Call<CartGetResponse> call, Response<CartGetResponse> response) {
                if (!response.isSuccessful()){
                    progressDialog.dismiss();
                    Log.i("fail", String.valueOf(response));
//                    Toast.makeText(ProductDetailActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    return;
                }

                dataCart = response.body();

                for (CartsItem item : dataCart.getCarts()){
                    if (item.getProductId() == dataProduct.getId()){
                        btnAddToCart.setEnabled(false);
                        btnAddToCart.setText("Produk Ditambahkan");
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CartGetResponse> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
//                Toast.makeText(ProductDetailActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
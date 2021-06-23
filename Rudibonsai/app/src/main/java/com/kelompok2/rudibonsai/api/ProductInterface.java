package com.kelompok2.rudibonsai.api;

import com.kelompok2.rudibonsai.model.product.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ProductInterface {

    @Headers({
            "Accept: application/json"
    })
    @GET("products")
    Call<ProductResponse> getProducts();
}

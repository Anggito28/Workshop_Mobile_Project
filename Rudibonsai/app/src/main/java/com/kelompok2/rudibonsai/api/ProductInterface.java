package com.kelompok2.rudibonsai.api;

import com.kelompok2.rudibonsai.model.product.detail.ProductDetailResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ProductInterface {

    @Headers("Accept: application/json")
    @GET("products/{id}")
    Call<ProductDetailResponse> getProductDetail(
            @Header("Authorization") String token,
            @Path("id") int id
    );
}

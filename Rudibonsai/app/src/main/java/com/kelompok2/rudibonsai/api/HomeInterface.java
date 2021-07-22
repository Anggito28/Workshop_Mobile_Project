package com.kelompok2.rudibonsai.api;

import com.kelompok2.rudibonsai.model.product.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface HomeInterface {

    @Headers("Accept: application/json")
    @GET("products")
    Call<ProductResponse> getProducts(
            @Header("Authorization") String token
    );

    @Headers("Accept: application/json")
    @GET("products")
    Call<ProductResponse> getNextProducts(
            @Header("Authorization") String token,
            @Query("page") int page
    );
}

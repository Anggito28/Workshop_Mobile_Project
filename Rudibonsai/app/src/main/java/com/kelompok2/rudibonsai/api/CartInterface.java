package com.kelompok2.rudibonsai.api;

import com.kelompok2.rudibonsai.model.cart.CartResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface CartInterface {

    @Headers("Accept: application/json")
    @GET("carts")
    Call<CartResponse> getCarts(
            @Header("Authorization") String token
    );
}

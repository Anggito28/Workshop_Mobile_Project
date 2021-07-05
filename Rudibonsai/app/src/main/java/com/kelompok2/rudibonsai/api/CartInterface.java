package com.kelompok2.rudibonsai.api;

import com.kelompok2.rudibonsai.model.cart.get.CartDeleteResponse;
import com.kelompok2.rudibonsai.model.cart.get.CartGetResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface CartInterface {

    @Headers("Accept: application/json")
    @GET("carts")
    Call<CartGetResponse> getCarts(
            @Header("Authorization") String token
    );

    @Headers("Accept: application/json")
    @DELETE("carts/{cart}")
    Call<CartDeleteResponse> deleteCart(
            @Header("Authorization") String token,
            @Path("cart") int cart
    );
}

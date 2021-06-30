package com.kelompok2.rudibonsai.api;

import com.google.gson.JsonElement;
import com.kelompok2.rudibonsai.model.order.OrderPost;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OrderInterface {

    @Headers("Accept: application/json")
    @POST("orders")
    Call<JsonElement> makeOrder(
            @Header("Authorization") String token,
            @Body OrderPost order
    );
}

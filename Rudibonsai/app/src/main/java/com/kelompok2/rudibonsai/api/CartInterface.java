package com.kelompok2.rudibonsai.api;

import com.google.gson.JsonElement;
import com.kelompok2.rudibonsai.model.cart.get.CartDeleteResponse;
import com.kelompok2.rudibonsai.model.cart.get.CartGetResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("carts")
    Call<JsonElement> addToCart(
            @Header("Authorization") String token,
            @Field("user_id") int userId,
            @Field("product_id") int productId
    );
}

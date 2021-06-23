package com.kelompok2.rudibonsai.api;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginInterface {

    @FormUrlEncoded
    @Headers({
            "Accept: application/json"
    })
    @POST("login")
    Call<JsonElement> loginResponse(
            @Field("email") String email,
            @Field("password") String password
    );
}

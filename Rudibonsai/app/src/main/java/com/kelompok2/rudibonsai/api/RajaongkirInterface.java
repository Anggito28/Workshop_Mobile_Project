package com.kelompok2.rudibonsai.api;

import com.kelompok2.rudibonsai.model.rajaongkir.RajaongkirResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface RajaongkirInterface {

    @Headers("Accept: application/json")
    @GET("cost/{courier}/{destination}/{weight}")
    Call<RajaongkirResponse> getShippingCost(
            @Path("courier") String courier,
            @Path("destination") int destination,
            @Path("weight") int weight
    );
}

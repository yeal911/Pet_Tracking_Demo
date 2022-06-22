package com.example.pettrackingdemo.host.internal;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RouteService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("/mapApi/v1/routeService/{type}")
    Call<Routes> getRoute(@Path("type") String type,
                          @Query("key") String key,
                          @Body JsonObject body);
}

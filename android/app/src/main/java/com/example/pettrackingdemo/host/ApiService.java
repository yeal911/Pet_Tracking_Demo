package com.example.pettrackingdemo.host;

import com.example.pettrackingdemo.host.response.PetTrackingItem;
import com.example.pettrackingdemo.host.response.UserInfoResponse;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("/users")
    Call<ArrayList<UserInfoResponse>> getUserByUserId(@Body JsonObject body);

    @POST("/pets")
    Call<ArrayList<PetTrackingItem>> getPetTracking(@Body JsonObject body);

}

package com.example.pettrackingdemo.host.internal;

import com.example.pettrackingdemo.db.Pet;
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

    @POST("/users")
    Call<Void> putUserInfo(@Body JsonObject body);

    @POST("/pets")
    Call<ArrayList<PetTrackingItem>> getPetTracking(@Body JsonObject body);

    @POST("/pets")
    Call<Void> putPet(@Body JsonObject body);

    @POST("/pets")
    Call<ArrayList<Pet>> getPetForUserId(@Body JsonObject body);

    @POST("/pets")
    Call<Void> putPetLocation(@Body JsonObject body);

    @POST("/pets")
    Call<Void> deletePetByPetId(@Body JsonObject body);

    @POST("/pets")
    Call<Void> clearAllLocationsByPetId(@Body JsonObject body);


}

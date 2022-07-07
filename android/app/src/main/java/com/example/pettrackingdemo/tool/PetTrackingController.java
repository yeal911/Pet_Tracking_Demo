package com.example.pettrackingdemo.tool;

import com.example.pettrackingdemo.host.internal.HostAdapter;
import com.example.pettrackingdemo.host.internal.PetTrackingItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetTrackingController {
    private static PetTrackingController instance;

    public interface PetTrackingCallback {
        void onPetTrackingResponse(ArrayList<PetTrackingItem> trackingItems);

        void onError(Throwable throwable);
    }

    public interface PetPutLocationCallback {
        void onSuccess();

        void onError(Throwable throwable);
    }

    public void getTracking(String petId, PetTrackingCallback callback) {
        HostAdapter client = new HostAdapter();
        client.getPetTracking(petId).enqueue(new Callback<ArrayList<PetTrackingItem>>() {
            @Override
            public void onResponse(Call<ArrayList<PetTrackingItem>> call,
                                   Response<ArrayList<PetTrackingItem>> response) {
                callback.onPetTrackingResponse(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<PetTrackingItem>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void putPetLocation(String petId, String lat, String lon, PetPutLocationCallback callback) {
        HostAdapter client = new HostAdapter();
        client.putPetLocation(petId, lat, lon).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callback.onSuccess();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t);
            }
        });
    }


    public static PetTrackingController getInstance() {
        if (instance == null) {
            instance = new PetTrackingController();
        }
        return instance;
    }


}

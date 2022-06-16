package com.example.pettrackingdemo.tool;

import com.example.pettrackingdemo.host.HostAdapter;
import com.example.pettrackingdemo.host.response.PetTrackingItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetTrackingController {
    private static PetTrackingController instance;

    public interface PetTrackingCallback{
        void onPetTrackingResponse(ArrayList<PetTrackingItem> trackingItems);
        void onError (Throwable throwable);
    }

    public  void getTracking(PetTrackingCallback callback){
        HostAdapter client = new HostAdapter();
        client.getPetTracking("5906742c-7cb8-4b47-9804-f4788d94d89c").enqueue(new Callback<ArrayList<PetTrackingItem>>() {
            @Override
            public void onResponse(Call<ArrayList<PetTrackingItem>> call, Response<ArrayList<PetTrackingItem>> response) {
                callback.onPetTrackingResponse(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<PetTrackingItem>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public static PetTrackingController getInstance(){
        if (instance == null){
            instance = new PetTrackingController();
        }
        return instance;
    }
}

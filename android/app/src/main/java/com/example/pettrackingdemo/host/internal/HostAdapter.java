package com.example.pettrackingdemo.host.internal;

import com.example.pettrackingdemo.db.Pet;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class HostAdapter {
    private ApiService apiService;
    private static final String BASE_URL = "http://192.168.1.41:8080";
//    private static final String BASE_URL = "http://161.35.88.213";
    private Retrofit retrofit;

    public HostAdapter() {
        initApiService();
    }

    private void initApiService() {
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build()) // <-- set log level
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public Call<ArrayList<UserInfoResponse>> getUserInfo(String userId) {
        ApiService service = retrofit.create(ApiService.class);
        JsonObject joc = new JsonObject();
        try {
            JsonObject values = new JsonObject();
            values.addProperty("user_id", userId);
            joc.addProperty("action", "GETUSERBYUSERID");
            joc.add("values", values);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return service.getUserByUserId(joc);
    }

    public Call<Void> putUserInfo(String uid, String name, String email) {
        ApiService service = retrofit.create(ApiService.class);
        JsonObject joc = new JsonObject();
        try {
            JsonObject values = new JsonObject();
            values.addProperty("uid", uid);
            values.addProperty("name", name);
            values.addProperty("email", email);
            joc.addProperty("action", "PUTUSERINFO");
            joc.add("values", values);
            System.out.println("---value: "+joc.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return service.putUserInfo(joc);
    }

    public Call<ArrayList<PetTrackingItem>> getPetTracking(String petId) {
        ApiService service = retrofit.create(ApiService.class);
        JsonObject joc = new JsonObject();
        try {
            JsonObject values = new JsonObject();
            values.addProperty("pet_id", petId);
            joc.addProperty("action", "GETPETLOCATIONBYPETID");
            joc.add("values", values);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return service.getPetTracking(joc);
    }

    public Call<Void> putPet(String userId, String petName, String petType) {
        ApiService service = retrofit.create(ApiService.class);
        JsonObject joc = new JsonObject();
        try {
            JsonObject values = new JsonObject();
            values.addProperty("user_id", userId);
            values.addProperty("pet_name", petName);
            values.addProperty("pet_type", petType);
            joc.addProperty("action", "PUTPET");
            joc.add("values", values);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return service.putPet(joc);
    }

    public Call<ArrayList<Pet>> getPetForUserId(String userId){
        ApiService service = retrofit.create(ApiService.class);
        JsonObject joc = new JsonObject();
        try {
            JsonObject values = new JsonObject();
            values.addProperty("user_id", userId);
            joc.addProperty("action", "GETPETSBYUSERID");
            joc.add("values", values);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return service.getPetForUserId(joc);
    }

    public Call<Void> putPetLocation(String petId, String lat, String lon){
        ApiService service = retrofit.create(ApiService.class);
        JsonObject joc = new JsonObject();
        try {
            JsonObject values = new JsonObject();
            values.addProperty("pet_id", petId);
            values.addProperty("lat", lat);
            values.addProperty("lon", lon);
            joc.addProperty("action", "PUTPETLOCATION");
            joc.add("values", values);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return service.putPetLocation(joc);
    }


    public Call<Void> deletePetByPetId(String petId){
        ApiService service = retrofit.create(ApiService.class);
        JsonObject joc = new JsonObject();
        try {
            JsonObject values = new JsonObject();
            values.addProperty("pet_id", petId);
            joc.addProperty("action", "DELETEPETBYPETID");
            joc.add("values", values);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return service.putPetLocation(joc);
    }

    public Call<Void> clearAllLocationsByPetId(String petId){
        ApiService service = retrofit.create(ApiService.class);
        JsonObject joc = new JsonObject();
        try {
            JsonObject values = new JsonObject();
            values.addProperty("pet_id", petId);
            joc.addProperty("action", "CLEARALLPETLOCATIONSBYPETID");
            joc.add("values", values);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return service.clearAllLocationsByPetId(joc);
    }

}

package com.example.pettrackingdemo.host.internal;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HostAdapter {
    private ApiService apiService;
    private static final String BASE_URL = "http://192.168.1.43:8080";
    private Retrofit retrofit;

    public HostAdapter() {
        initApiService();
    }

    private void initApiService() {
        // Creamos un interceptor y le indicamos el log level a usar
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Asociamos el interceptor a las peticiones
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

    public Call<ArrayList<PetTrackingItem>> getPetTracking(String petId) {
        ApiService service = retrofit.create(ApiService.class);
        JsonObject joc = new JsonObject();
        try {
            JsonObject values = new JsonObject();
            values.addProperty("PetID", petId);
            joc.addProperty("action", "GETPETLOCATIONBYPETID");
            joc.add("values", values);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return service.getPetTracking(joc);
    }
}

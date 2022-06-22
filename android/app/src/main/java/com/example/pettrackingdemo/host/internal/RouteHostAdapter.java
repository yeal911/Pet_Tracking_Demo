package com.example.pettrackingdemo.host.internal;


import com.google.gson.JsonObject;
import com.huawei.hms.maps.model.LatLng;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RouteHostAdapter {

    private static RouteService routeService;
    private static final String BASE_URL = "https://mapapi.cloud.huawei.com/";
    private Retrofit retrofit;

    public RouteHostAdapter() {
        initService();
    }

    private void initService() {
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build()) // <-- set log level
                .build();

        routeService = retrofit.create(RouteService.class);
        retrofit.create(ApiService.class);
    }

    public Call<Routes> getRoute(String type, LatLng orig, LatLng dest) {
        RouteService service = retrofit.create(RouteService.class);
        try {

            JsonObject body = new JsonObject();
            JsonObject jOrig = new JsonObject();
            JsonObject jDest = new JsonObject();
            jOrig.addProperty("lat", orig.latitude);
            jOrig.addProperty("lng", orig.longitude);
            jDest.addProperty("lat", dest.latitude);
            jDest.addProperty("lng", dest.longitude);
            body.add("origin", jOrig);
            body.add("destination", jDest);

            return service.getRoute(type, Constants.API_KEY, body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

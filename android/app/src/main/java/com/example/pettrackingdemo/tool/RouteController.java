package com.example.pettrackingdemo.tool;

import com.example.pettrackingdemo.host.internal.RouteHostAdapter;
import com.example.pettrackingdemo.host.internal.Routes;
import com.huawei.hms.maps.model.LatLng;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteController {
    private static RouteController instance;
    RouteHostAdapter adapter;

    public interface RouteCallback {
        void onRouteResult(Routes route);

        void onError(Throwable throwable);
    }

    public RouteController() {
        adapter = new RouteHostAdapter();
    }

    public void getRoute(String type, LatLng orig, LatLng dest, RouteCallback callback) {
        adapter.getRoute(type, orig, dest).enqueue(new Callback<Routes>() {
            @Override
            public void onResponse(Call<Routes> call, Response<Routes> response) {
                callback.onRouteResult(response.body());
            }

            @Override
            public void onFailure(Call<Routes> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public static RouteController getInstance() {
        if (instance == null) {
            instance = new RouteController();
        }
        return instance;
    }
}

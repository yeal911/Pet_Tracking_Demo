package com.example.pettrackingdemo.tool;

import android.content.Context;
import android.location.Location;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.maps.model.LatLng;

public class LocationManager {

    public interface LocationCallback {
        void onLocationResult(LatLng latLng);
    }

    public void getLocation(Context context, LocationCallback callback) {
        FusedLocationProviderClient fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(context);
        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(location -> {
            try {
                callback.onLocationResult(new LatLng(location.getLatitude(),
                        location.getLongitude()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        task.addOnFailureListener(e -> e.printStackTrace());
    }

    private static LocationManager instance;

    public static LocationManager getInstance() {
        if (instance == null) {
            instance = new LocationManager();
        }
        return instance;
    }
}

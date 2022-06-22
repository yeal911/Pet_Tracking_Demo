package com.example.pettrackingdemo.host.internal;

import com.google.gson.annotations.SerializedName;

public class Position {
    @SerializedName("lat")
    double lat = 0.0;

    @SerializedName("lng")
    double lng = 0.0;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}

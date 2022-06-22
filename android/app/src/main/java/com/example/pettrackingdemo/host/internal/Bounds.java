package com.example.pettrackingdemo.host.internal;


import android.location.Location;

import com.google.gson.annotations.SerializedName;

class Bounds {
    @SerializedName("northeast")
    Location northeast;

    @SerializedName("southwest")
    Location southwest;

    public Location getNortheast() {
        return northeast;
    }

    public void setNortheast(Location northeast) {
        this.northeast = northeast;
    }

    public Location getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Location southwest) {
        this.southwest = southwest;
    }
}

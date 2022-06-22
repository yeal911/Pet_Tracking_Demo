package com.example.pettrackingdemo.host.internal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Route {
    @SerializedName("bounds")
    Bounds bounds;
    @SerializedName("paths")
    List<Path> paths;

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }
}

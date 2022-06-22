package com.example.pettrackingdemo.host.internal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Step {
    @SerializedName("action")
    String action;

    @SerializedName("distance")
    double distance = 0.0;

    @SerializedName("duration")
    double duration = 0.0;

    @SerializedName("startLocation")
    Position startLocation;

    @SerializedName("endLocation")
    Position endLocation;

    @SerializedName("orientation")
    double orientation = 0;

    @SerializedName("polyline")
    List<Position> polyline;

    @SerializedName("roadName")
    String roadName;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public Position getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Position startLocation) {
        this.startLocation = startLocation;
    }

    public Position getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Position endLocation) {
        this.endLocation = endLocation;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    public List<Position> getPolyline() {
        return polyline;
    }

    public void setPolyline(List<Position> polyline) {
        this.polyline = polyline;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }
}

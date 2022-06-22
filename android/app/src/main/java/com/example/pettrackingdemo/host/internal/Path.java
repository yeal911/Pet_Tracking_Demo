package com.example.pettrackingdemo.host.internal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Path {

    @SerializedName("distance")
    double distance = 0.0;

    @SerializedName("duration")
    double duration = 0.0;

    @SerializedName("durationInTraffic")
    double durationInTraffic = 0.0;

    @SerializedName("endLocation")
    Position endLocation;

    @SerializedName("startLocation")
    Position startLocation;

    @SerializedName("steps")
    List<Step> steps;

    @SerializedName("durationInTrafficText")
    String durationInTrafficText;

    @SerializedName("distanceText")
    String distanceText;

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

    public double getDurationInTraffic() {
        return durationInTraffic;
    }

    public void setDurationInTraffic(double durationInTraffic) {
        this.durationInTraffic = durationInTraffic;
    }

    public Position getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Position endLocation) {
        this.endLocation = endLocation;
    }

    public Position getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Position startLocation) {
        this.startLocation = startLocation;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public String getDurationInTrafficText() {
        return durationInTrafficText;
    }

    public void setDurationInTrafficText(String durationInTrafficText) {
        this.durationInTrafficText = durationInTrafficText;
    }

    public String getDistanceText() {
        return distanceText;
    }

    public void setDistanceText(String distanceText) {
        this.distanceText = distanceText;
    }
}

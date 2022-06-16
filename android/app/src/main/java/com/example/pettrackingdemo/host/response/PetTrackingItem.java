package com.example.pettrackingdemo.host.response;

import com.google.gson.annotations.SerializedName;

public class PetTrackingItem {
    @SerializedName("RecordID")
    private String recordId;
    @SerializedName("PetID")
    private String petId;
    @SerializedName("Date")
    private String date;
    @SerializedName("Latitude")
    private String lat;
    @SerializedName("Longitude")
    private String lon;
    @SerializedName("Remark")
    private String remark;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

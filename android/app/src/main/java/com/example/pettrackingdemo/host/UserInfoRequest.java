package com.example.pettrackingdemo.host;

import com.google.gson.annotations.SerializedName;

class UserInfoRequest {
    @SerializedName("userID")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

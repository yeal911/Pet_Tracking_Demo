package com.example.pettrackingdemo.host.response;

import com.google.gson.annotations.SerializedName;

public class UserInfoResponse {
    @SerializedName("userID")
    private String userId;
    @SerializedName("remark")
    private String remark;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

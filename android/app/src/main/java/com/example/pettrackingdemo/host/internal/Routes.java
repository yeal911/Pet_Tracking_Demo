package com.example.pettrackingdemo.host.internal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Routes {
    @SerializedName("returnCode")
    String returnCode;

    @SerializedName("returnDesc")
    String returnDesc;

    @SerializedName("routes")
    List<Route> routes;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnDesc() {
        return returnDesc;
    }

    public void setReturnDesc(String returnDesc) {
        this.returnDesc = returnDesc;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}

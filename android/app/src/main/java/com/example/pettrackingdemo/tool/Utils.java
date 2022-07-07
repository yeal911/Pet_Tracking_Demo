package com.example.pettrackingdemo.tool;

public class Utils {

    public static double[] generateRandomLocation(double lat, double lon) {
        double[] coords = new double[2];
        double seed = Math.random() * 0.5;
        coords[0] = lat - seed;
        coords[1] = lon - seed;
        return coords;
    }
}

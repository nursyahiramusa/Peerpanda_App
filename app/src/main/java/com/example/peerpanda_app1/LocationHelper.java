package com.example.peerpanda_app1;

public class LocationHelper {
    private double Logitude;
    private double Latitude;
    private String stuID;

    public LocationHelper(double logitude, double latitude, String stuID) {
        Logitude = logitude;
        Latitude = latitude;
        this.stuID = stuID;
    }

    public double getLogitude() {
        return Logitude;
    }

    public void setLogitude(double logitude) {
        Logitude = logitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }
}

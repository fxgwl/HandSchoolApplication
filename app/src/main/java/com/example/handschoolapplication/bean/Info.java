package com.example.handschoolapplication.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/7.
 */

public class Info implements Serializable{

    public Info(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private double latitude;
    private double longitude;


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

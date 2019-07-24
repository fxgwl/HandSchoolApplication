package com.example.handschoolapplication.bean;

/**
 * Created by Axehome_Mr.z on 2019/2/28 16:32
 * $Describe
 */
public class CurrentCityChangeBeanS {

    private String currentCity;

    public String getCurrentCity() {
        return currentCity == null ? "" : currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }
}

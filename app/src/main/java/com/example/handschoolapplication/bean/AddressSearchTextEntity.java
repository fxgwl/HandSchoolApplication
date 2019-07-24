package com.example.handschoolapplication.bean;

import com.amap.api.services.core.LatLonPoint;

/**
 * Created by axehome on 2018/2/5.
 */

public class AddressSearchTextEntity {
    private String title;
    private String snippet;
    private boolean isChoose;
    private LatLonPoint latLonPoint;

    public AddressSearchTextEntity() {
    }

    public AddressSearchTextEntity(String title, String snippet, boolean isChoose, LatLonPoint latLonPoint) {
        this.title = title;
        this.snippet = snippet;
        this.isChoose = isChoose;
        this.latLonPoint = latLonPoint;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public LatLonPoint getLatLonPoint() {
        return latLonPoint;
    }

    public void setLatLonPoint(LatLonPoint latLonPoint) {
        this.latLonPoint = latLonPoint;
    }
}

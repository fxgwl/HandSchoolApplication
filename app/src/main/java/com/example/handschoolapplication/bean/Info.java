package com.example.handschoolapplication.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/7.
 */

public class Info implements Serializable {

    public Info(double latitude, double longitude, CourseSortBean courseBean, ClassSortBean classBean) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.courseBean = courseBean;
        this.classBean = classBean;
    }

    private double latitude;
    private double longitude;
    private CourseSortBean courseBean;
    private ClassSortBean classBean;


    public CourseSortBean getCourseBean() {
        return courseBean;
    }

    public void setCourseBean(CourseSortBean courseBean) {
        this.courseBean = courseBean;
    }

    public ClassSortBean getClassBean() {
        return classBean;
    }

    public void setClassBean(ClassSortBean classBean) {
        this.classBean = classBean;
    }

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

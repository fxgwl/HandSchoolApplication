package com.example.handschoolapplication.bean;

/**
 * Created by Administrator on 2017/7/25.
 */

public class CourseListBean {

    private boolean isChoose;
    private double price;
    private int count;

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

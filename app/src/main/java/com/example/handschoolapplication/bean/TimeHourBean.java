package com.example.handschoolapplication.bean;

/**
 * Created by Administrator on 2017/9/2.
 */

public class TimeHourBean {
    private boolean isChecked;
    private String time;

    public TimeHourBean() {
    }

    public TimeHourBean(boolean isChecked, String time) {
        this.isChecked = isChecked;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "TimeHourBean{" +
                "isChecked=" + isChecked +
                ", time='" + time + '\'' +
                '}';
    }
}

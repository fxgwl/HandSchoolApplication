package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/2.
 */

public class TimeBean {
    private List<TimeHourBean> mlist;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TimeHourBean> getMlist() {
        return mlist;
    }

    public void setMlist(List<TimeHourBean> mlist) {
        this.mlist = mlist;
    }

    @Override
    public String toString() {
        return "TimeBean{" +
                "mlist=" + mlist +
                ", name='" + name + '\'' +
                '}';
    }
}

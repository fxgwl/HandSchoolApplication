package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */

public class TimeChooseBean {

    /**
     * data : [{"course_id":"1505888221350","create_time":"2017-10-21 16:33:07","ctime_id":12,"ctime_times":"8:30-10:00,10:30-12:00,13:30-15:00,15:30-17:00","ctime_week":"周一"},{"course_id":"1505888221350","create_time":"2017-10-21 16:33:07","ctime_id":13,"ctime_times":"8:30-10:00,10:30-12:00,13:30-15:00,15:30-17:00","ctime_week":"周二"}]
     * msg : 成功
     * result : 0
     */

    private String msg;
    private int result;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * course_id : 1505888221350
         * create_time : 2017-10-21 16:33:07
         * ctime_id : 12
         * ctime_times : 8:30-10:00,10:30-12:00,13:30-15:00,15:30-17:00
         * ctime_week : 周一
         */

        private String course_id;
        private String create_time;
        private int ctime_id;
        private String ctime_times;
        private String ctime_week;

        public String getCourse_id() {
            return course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getCtime_id() {
            return ctime_id;
        }

        public void setCtime_id(int ctime_id) {
            this.ctime_id = ctime_id;
        }

        public String getCtime_times() {
            return ctime_times;
        }

        public void setCtime_times(String ctime_times) {
            this.ctime_times = ctime_times;
        }

        public String getCtime_week() {
            return ctime_week;
        }

        public void setCtime_week(String ctime_week) {
            this.ctime_week = ctime_week;
        }
    }
}

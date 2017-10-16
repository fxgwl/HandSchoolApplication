package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */

public class SchoolSaveBean {


    /**
     * data : [{"collect_sid":44,"collect_time":"2017-10-16 10:02:11","school_id":"1505805630235","school_jifen":"0","school_logo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1506565555092.jpg","school_name":"我的小学堂b","user_id":"205c2f34bc064712bf2e0157c142b6dd","user_name":"aedf","user_phone":"1"},{"collect_sid":43,"collect_time":"2017-10-16 10:02:02","school_id":"1505805630236","school_jifen":"1","school_logo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","school_name":"我的小学堂a","user_id":"205c2f34bc064712bf2e0157c142b6dd","user_name":"aedf","user_phone":"1"}]
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
         * collect_sid : 44
         * collect_time : 2017-10-16 10:02:11
         * school_id : 1505805630235
         * school_jifen : 0
         * school_logo : bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1506565555092.jpg
         * school_name : 我的小学堂b
         * user_id : 205c2f34bc064712bf2e0157c142b6dd
         * user_name : aedf
         * user_phone : 1
         */

        private int collect_sid;
        private String collect_time;
        private String school_id;
        private String school_jifen;
        private String school_logo;
        private String school_name;
        private String user_id;
        private String user_name;
        private String user_phone;

        public int getCollect_sid() {
            return collect_sid;
        }

        public void setCollect_sid(int collect_sid) {
            this.collect_sid = collect_sid;
        }

        public String getCollect_time() {
            return collect_time;
        }

        public void setCollect_time(String collect_time) {
            this.collect_time = collect_time;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getSchool_jifen() {
            return school_jifen;
        }

        public void setSchool_jifen(String school_jifen) {
            this.school_jifen = school_jifen;
        }

        public String getSchool_logo() {
            return school_logo;
        }

        public void setSchool_logo(String school_logo) {
            this.school_logo = school_logo;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }
    }
}

package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class ClassCourse {

    /**
     * data : [{"age_range":"的","course_capacity":"的","course_id":"1507603212791","course_info":"","course_money":"啊","course_name":"v的v的的","course_photo":"2017/10/10/1507603212769.jpg,2017/10/10/1507603212773.png,2017/10/10/1507603212779.png,2017/10/10/1507603212785.png","course_state":"0","course_time":"2017-10-10 10:40:12","enrol_num":"的","original_price":"v但是","popularity_num":"的","preferential_price":"4","school_id":"1507602671757","school_name":"1","study_num":"1507603212791","user_id":"816939bd23dc4296a4d1f3cd731b3143"},{"age_range":"c","course_capacity":"v","course_id":"1507603247255","course_info":"","course_money":"现在","course_name":"再在","course_photo":"2017/10/10/1507603247236.png,2017/10/10/1507603247243.png,2017/10/10/1507603247248.png","course_state":"0","course_time":"2017-10-10 10:40:47","enrol_num":" v","original_price":"中v","popularity_num":"  传","preferential_price":"2","school_id":"1507602671757","school_name":"1","study_num":"1507603247255","user_id":"816939bd23dc4296a4d1f3cd731b3143"}]
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
         * age_range : 的
         * course_capacity : 的
         * course_id : 1507603212791
         * course_info :
         * course_money : 啊
         * course_name : v的v的的
         * course_photo : 2017/10/10/1507603212769.jpg,2017/10/10/1507603212773.png,2017/10/10/1507603212779.png,2017/10/10/1507603212785.png
         * course_state : 0
         * course_time : 2017-10-10 10:40:12
         * enrol_num : 的
         * original_price : v但是
         * popularity_num : 的
         * preferential_price : 4
         * school_id : 1507602671757
         * school_name : 1
         * study_num : 1507603212791
         * user_id : 816939bd23dc4296a4d1f3cd731b3143
         */

        private String age_range;
        private String course_capacity;
        private String course_id;
        private String course_info;
        private String course_money;
        private String course_name;
        private String course_photo;
        private String course_state;
        private String course_time;
        private String enrol_num;
        private String original_price;
        private String popularity_num;
        private String preferential_price;
        private String school_id;
        private String school_name;
        private String study_num;
        private String user_id;

        public String getAge_range() {
            return age_range;
        }

        public void setAge_range(String age_range) {
            this.age_range = age_range;
        }

        public String getCourse_capacity() {
            return course_capacity;
        }

        public void setCourse_capacity(String course_capacity) {
            this.course_capacity = course_capacity;
        }

        public String getCourse_id() {
            return course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public String getCourse_info() {
            return course_info;
        }

        public void setCourse_info(String course_info) {
            this.course_info = course_info;
        }

        public String getCourse_money() {
            return course_money;
        }

        public void setCourse_money(String course_money) {
            this.course_money = course_money;
        }

        public String getCourse_name() {
            return course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }

        public String getCourse_photo() {
            return course_photo;
        }

        public void setCourse_photo(String course_photo) {
            this.course_photo = course_photo;
        }

        public String getCourse_state() {
            return course_state;
        }

        public void setCourse_state(String course_state) {
            this.course_state = course_state;
        }

        public String getCourse_time() {
            return course_time;
        }

        public void setCourse_time(String course_time) {
            this.course_time = course_time;
        }

        public String getEnrol_num() {
            return enrol_num;
        }

        public void setEnrol_num(String enrol_num) {
            this.enrol_num = enrol_num;
        }

        public String getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(String original_price) {
            this.original_price = original_price;
        }

        public String getPopularity_num() {
            return popularity_num;
        }

        public void setPopularity_num(String popularity_num) {
            this.popularity_num = popularity_num;
        }

        public String getPreferential_price() {
            return preferential_price;
        }

        public void setPreferential_price(String preferential_price) {
            this.preferential_price = preferential_price;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getStudy_num() {
            return study_num;
        }

        public void setStudy_num(String study_num) {
            this.study_num = study_num;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}

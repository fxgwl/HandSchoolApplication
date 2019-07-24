package com.example.handschoolapplication.base;

import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */

public class SaveCourseBean {

    /**
     * data : [{"collect_course":"音乐3","collect_id":9,"collect_time":"2017-10-16 09:18:46","course_id":"1506153961723","course_money":"330","course_name":"音乐3","course_photo":"bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg","user_id":"205c2f34bc064712bf2e0157c142b6dd","user_name":"aedf","user_phone":"1"},{"collect_course":"音乐","collect_id":8,"collect_time":"2017-10-16 09:18:33","course_id":"1506153404911","course_money":"330","course_name":"音乐","course_photo":"bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg","user_id":"205c2f34bc064712bf2e0157c142b6dd","user_name":"aedf","user_phone":"1"},{"collect_course":"舞蹈","collect_id":7,"collect_time":"2017-10-16 09:18:08","course_id":"1505888150982","course_money":"330","course_name":"舞蹈","course_photo":"bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg","user_id":"205c2f34bc064712bf2e0157c142b6dd","user_name":"aedf","user_phone":"1"}]
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
         * collect_course : 音乐3
         * collect_id : 9
         * collect_time : 2017-10-16 09:18:46
         * course_id : 1506153961723
         * course_money : 330
         * course_name : 音乐3
         * course_photo : bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg
         * user_id : 205c2f34bc064712bf2e0157c142b6dd
         * user_name : aedf
         * user_phone : 1
         */

        private String collect_course;
        private int collect_id;
        private String collect_time;
        private String course_id;
        private String course_money;
        private String course_name;
        private String course_photo;
        private String picture_one;
        private String picture_two;
        private String picture_three;
        private String picture_four;
        private String picture_five;
        private String user_id;
        private String user_name;
        private String user_phone;

        public String getCollect_course() {
            return collect_course;
        }

        public void setCollect_course(String collect_course) {
            this.collect_course = collect_course;
        }

        public int getCollect_id() {
            return collect_id;
        }

        public void setCollect_id(int collect_id) {
            this.collect_id = collect_id;
        }

        public String getCollect_time() {
            return collect_time;
        }

        public void setCollect_time(String collect_time) {
            this.collect_time = collect_time;
        }

        public String getCourse_id() {
            return course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
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

        public String getPicture_one() {
            return picture_one == null ? "" : picture_one;
        }

        public void setPicture_one(String picture_one) {
            this.picture_one = picture_one;
        }

        public String getPicture_two() {
            return picture_two == null ? "" : picture_two;
        }

        public void setPicture_two(String picture_two) {
            this.picture_two = picture_two;
        }

        public String getPicture_three() {
            return picture_three == null ? "" : picture_three;
        }

        public void setPicture_three(String picture_three) {
            this.picture_three = picture_three;
        }

        public String getPicture_four() {
            return picture_four == null ? "" : picture_four;
        }

        public void setPicture_four(String picture_four) {
            this.picture_four = picture_four;
        }

        public String getPicture_five() {
            return picture_five == null ? "" : picture_five;
        }

        public void setPicture_five(String picture_five) {
            this.picture_five = picture_five;
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

package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */

public class MyBroswerBean {

    /**
     * data : [{"course_id":"1505888150982","course_money":"330","course_name":"舞蹈","footprint_date":"2017-10-16 10:02:07","footprint_id":21,"footprint_name":"bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg","footprint_time":"2017-10-16","user_id":"205c2f34bc064712bf2e0157c142b6dd"},{"course_id":"1506153961723","course_money":"330","course_name":"音乐3","footprint_date":"2017-10-16 10:01:53","footprint_id":22,"footprint_name":"bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg","footprint_time":"2017-10-16","user_id":"205c2f34bc064712bf2e0157c142b6dd"},{"course_id":"1506153404911","course_money":"330","course_name":"音乐","footprint_date":"2017-10-16 10:01:43","footprint_id":20,"footprint_name":"bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg","footprint_time":"2017-10-16","user_id":"205c2f34bc064712bf2e0157c142b6dd"},{"course_id":"1505888221350","course_money":"330","course_name":"艺术1","footprint_date":"2017-10-16 09:45:55","footprint_id":23,"footprint_name":"bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg","footprint_time":"2017-10-16","user_id":"205c2f34bc064712bf2e0157c142b6dd"}]
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
         * course_id : 1505888150982
         * course_money : 330
         * course_name : 舞蹈
         * footprint_date : 2017-10-16 10:02:07
         * footprint_id : 21
         * footprint_name : bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg
         * footprint_time : 2017-10-16
         * user_id : 205c2f34bc064712bf2e0157c142b6dd
         */

        private String course_id;
        private String course_money;
        private String course_name;
        private String footprint_date;
        private int footprint_id;
        private String footprint_name;
        private String picture_one;
        private String picture_two;
        private String picture_three;
        private String picture_four;
        private String picture_five;
        private String footprint_time;
        private String user_id;

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

        public String getFootprint_date() {
            return footprint_date;
        }

        public void setFootprint_date(String footprint_date) {
            this.footprint_date = footprint_date;
        }

        public int getFootprint_id() {
            return footprint_id;
        }

        public void setFootprint_id(int footprint_id) {
            this.footprint_id = footprint_id;
        }

        public String getFootprint_name() {
            return footprint_name;
        }

        public void setFootprint_name(String footprint_name) {
            this.footprint_name = footprint_name;
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

        public String getFootprint_time() {
            return footprint_time;
        }

        public void setFootprint_time(String footprint_time) {
            this.footprint_time = footprint_time;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}

package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class ClassCourse {


    /**
     * data : [{"age_range":"2-6岁","course_address":"天津市北京市","course_capacity":"28","course_id":"1506153404911","course_info":"<p>bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg<\/p>","course_money":"800元/4节","course_name":"音乐","course_photo":"2017/11/10/1510302644926.jpg,2017/11/10/1510302644931.jpg,2017/11/10/1510302644935.jpg,2017/11/10/1510302644950.jpg,2017/11/10/1510302644953.jpg","course_state":"1","course_teacher":"李小明","course_time":"2017-11-10 16:47:55","course_type":"文体艺术/书画,学习辅导/中小学教育","dengji":"5","enrol_num":"53","hot_time":"2017-09-20 14:15:50","original_price":"660","popularity_num":"646","preferential_price":"330","school_id":"1505805630236","school_jing":"119.40403592998626","school_name":"我的小学堂a","school_wei":"39.91386028193478","study_num":"1506153404911","user_id":"8841f54f7b574f06a470ee9002043f8d"},{"age_range":"2-6岁","course_address":"天津市北京市","course_capacity":"28","course_id":"1506153854854","course_info":"<p>bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg<\/p>","course_money":"800元/4节","course_name":"音乐2","course_photo":"2017/11/10/1510302704831.jpg,2017/11/10/1510302704834.jpg","course_state":"2","course_teacher":"李小红","course_time":"2017-11-10 16:48:04","course_type":"文体艺术/球类/足球/乒乓球","enrol_num":"25","hot_time":"2017-09-20 14:15:50","original_price":"660","popularity_num":"98","preferential_price":"330","school_id":"1505805630236","school_jing":"116.40403592998626","school_name":"我的小学堂a","school_wei":"19.91386028193478","study_num":"1506153854854","user_id":"8841f54f7b574f06a470ee9002043f8d"},{"age_range":"2-6岁","course_address":"天津市北京市","course_capacity":"28","course_id":"1506153961723","course_info":"<p>bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg<\/p>","course_money":"800元/4节","course_name":"音乐3","course_photo":"2017/11/10/1510302723335.jpg,2017/11/10/1510302723346.jpg,2017/11/10/1510302723357.jpg","course_state":"2","course_teacher":"李小明","course_time":"2017-11-10 16:48:08","course_type":"文体艺术/书画/球类/足球/篮球","dengji":"5","enrol_num":"37","hot_time":"2017-09-20 14:15:50","original_price":"660","popularity_num":"258","preferential_price":"330","school_id":"1505805630236","school_jing":"116.40403592998626","school_name":"我的小学堂a","school_wei":"29.91386028193478","study_num":"1506153961723","user_id":"8841f54f7b574f06a470ee9002043f8d"}]
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
         * age_range : 2-6岁
         * course_address : 天津市北京市
         * course_capacity : 28
         * course_id : 1506153404911
         * course_info : <p>bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg</p>
         * course_money : 800元/4节
         * course_name : 音乐
         * course_photo : 2017/11/10/1510302644926.jpg,2017/11/10/1510302644931.jpg,2017/11/10/1510302644935.jpg,2017/11/10/1510302644950.jpg,2017/11/10/1510302644953.jpg
         * course_state : 1
         * course_teacher : 李小明
         * course_time : 2017-11-10 16:47:55
         * course_type : 文体艺术/书画,学习辅导/中小学教育
         * dengji : 5
         * enrol_num : 53
         * hot_time : 2017-09-20 14:15:50
         * original_price : 660
         * popularity_num : 646
         * preferential_price : 330
         * school_id : 1505805630236
         * school_jing : 119.40403592998626
         * school_name : 我的小学堂a
         * school_wei : 39.91386028193478
         * study_num : 1506153404911
         * user_id : 8841f54f7b574f06a470ee9002043f8d
         */

        private String age_range;
        private String course_address;
        private String course_capacity;
        private String course_id;
        private String course_info;
        private String course_money;
        private String course_name;
        private String course_photo;
        private String picture_one;
        private String picture_two;
        private String picture_three;
        private String picture_four;
        private String picture_five;
        private String course_state;
        private String course_teacher;
        private String course_time;
        private String course_type;
        private String dengji;
        private String enrol_num;
        private String hot_time;
        private String original_price;
        private String popularity_num;
        private String preferential_price;
        private String school_id;
        private String school_jing;
        private String school_name;
        private String school_wei;
        private String study_num;
        private String user_id;

        public String getAge_range() {
            return age_range;
        }

        public void setAge_range(String age_range) {
            this.age_range = age_range;
        }

        public String getCourse_address() {
            return course_address;
        }

        public void setCourse_address(String course_address) {
            this.course_address = course_address;
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

        public String getPicture_one() {
            return picture_one;
        }

        public void setPicture_one(String picture_one) {
            this.picture_one = picture_one;
        }

        public String getPicture_two() {
            return picture_two;
        }

        public void setPicture_two(String picture_two) {
            this.picture_two = picture_two;
        }

        public String getPicture_three() {
            return picture_three;
        }

        public void setPicture_three(String picture_three) {
            this.picture_three = picture_three;
        }

        public String getPicture_four() {
            return picture_four;
        }

        public void setPicture_four(String picture_four) {
            this.picture_four = picture_four;
        }

        public String getPicture_five() {
            return picture_five;
        }

        public void setPicture_five(String picture_five) {
            this.picture_five = picture_five;
        }

        public String getCourse_state() {
            return course_state;
        }

        public void setCourse_state(String course_state) {
            this.course_state = course_state;
        }

        public String getCourse_teacher() {
            return course_teacher;
        }

        public void setCourse_teacher(String course_teacher) {
            this.course_teacher = course_teacher;
        }

        public String getCourse_time() {
            return course_time;
        }

        public void setCourse_time(String course_time) {
            this.course_time = course_time;
        }

        public String getCourse_type() {
            return course_type;
        }

        public void setCourse_type(String course_type) {
            this.course_type = course_type;
        }

        public String getDengji() {
            return dengji;
        }

        public void setDengji(String dengji) {
            this.dengji = dengji;
        }

        public String getEnrol_num() {
            return enrol_num;
        }

        public void setEnrol_num(String enrol_num) {
            this.enrol_num = enrol_num;
        }

        public String getHot_time() {
            return hot_time;
        }

        public void setHot_time(String hot_time) {
            this.hot_time = hot_time;
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

        public String getSchool_jing() {
            return school_jing;
        }

        public void setSchool_jing(String school_jing) {
            this.school_jing = school_jing;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getSchool_wei() {
            return school_wei;
        }

        public void setSchool_wei(String school_wei) {
            this.school_wei = school_wei;
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

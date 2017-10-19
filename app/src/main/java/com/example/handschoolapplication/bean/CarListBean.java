package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 */

public class CarListBean {

    @Override
    public String toString() {
        return "CarListBean{" +
                "msg='" + msg + '\'' +
                ", result=" + result +
                ", data=" + data +
                '}';
    }

    /**
     * data : [{"class_money":"","class_name":"音乐","class_people":"","class_photo":"bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg","class_size":"","class_teacher":"1506153404911","class_time":"","course_id":"1506153404911","course_num":"1","is_coupons":0,"is_gold":"0","order_id":"1507541019423","order_money":"330","order_state":"0","order_type":"","ordre_time":"2017-10-09 17:23:39","pay_num":"","pay_type":"","school_id":"1505805630236","school_logo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1506565555092.jpg","school_name":"我的小学堂a","user_id":"772d42a5a0014faa8706f7cd3f6e8828","user_name":"chuan","user_phone":"13930989708"},{"class_name":"艺术","class_photo":"bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg","class_teacher":"1505888221351","course_id":"1505888221351","course_num":"1","order_id":"1508138138631","order_money":"330","order_state":"0","ordre_time":"2017-10-16 15:15:38","school_id":"1505805630235","school_logo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1506565555092.jpg","school_name":"我的小学堂b","user_id":"772d42a5a0014faa8706f7cd3f6e8828","user_phone":"18330695171"},{"class_name":"艺术1","class_photo":"bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg","class_teacher":"1505888221351","course_id":"1505888221350","course_num":"1","order_id":"1508138365483","order_money":"330","order_state":"0","ordre_time":"2017-10-16 15:19:25","school_id":"1505805630235","school_logo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1506565555092.jpg","school_name":"我的小学堂b","user_id":"772d42a5a0014faa8706f7cd3f6e8828","user_phone":"18330695171"}]
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

    public static class DataBean extends  BaseInfo{
        @Override
        public String toString() {
            return "DataBean{" +
                    "class_money='" + class_money + '\'' +
                    ", class_name='" + class_name + '\'' +
                    ", class_people='" + class_people + '\'' +
                    ", class_photo='" + class_photo + '\'' +
                    ", class_size='" + class_size + '\'' +
                    ", class_teacher='" + class_teacher + '\'' +
                    ", class_time='" + class_time + '\'' +
                    ", course_id='" + course_id + '\'' +
                    ", course_num='" + course_num + '\'' +
                    ", is_coupons=" + is_coupons +
                    ", is_gold='" + is_gold + '\'' +
                    ", order_id='" + order_id + '\'' +
                    ", order_money='" + order_money + '\'' +
                    ", order_state='" + order_state + '\'' +
                    ", order_type='" + order_type + '\'' +
                    ", ordre_time='" + ordre_time + '\'' +
                    ", pay_num='" + pay_num + '\'' +
                    ", pay_type='" + pay_type + '\'' +
                    ", school_id='" + school_id + '\'' +
                    ", school_logo='" + school_logo + '\'' +
                    ", school_name='" + school_name + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", user_phone='" + user_phone + '\'' +
                    '}';
        }

        /**
         * class_money :
         * class_name : 音乐
         * class_people :
         * class_photo : bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg
         * class_size :
         * class_teacher : 1506153404911
         * class_time :
         * course_id : 1506153404911
         * course_num : 1
         * is_coupons : 0
         * is_gold : 0
         * order_id : 1507541019423
         * order_money : 330
         * order_state : 0
         * order_type :
         * ordre_time : 2017-10-09 17:23:39
         * pay_num :
         * pay_type :
         * school_id : 1505805630236
         * school_logo : bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1506565555092.jpg
         * school_name : 我的小学堂a
         * user_id : 772d42a5a0014faa8706f7cd3f6e8828
         * user_name : chuan
         * user_phone : 13930989708
         */


        private String class_money;
        private String class_name;
        private String class_people;
        private String class_photo;
        private String class_size;
        private String class_teacher;
        private String class_time;
        private String course_id;
        private String course_num;
        private int is_coupons;
        private String is_gold;
        private String order_id;
        private String order_money;
        private String order_state;
        private String order_type;
        private String ordre_time;
        private String pay_num;
        private String pay_type;
        private String school_id;
        private String school_logo;
        private String school_name;
        private String user_id;
        private String user_name;
        private String user_phone;

        public String getClass_money() {
            return class_money;
        }

        public void setClass_money(String class_money) {
            this.class_money = class_money;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getClass_people() {
            return class_people;
        }

        public void setClass_people(String class_people) {
            this.class_people = class_people;
        }

        public String getClass_photo() {
            return class_photo;
        }

        public void setClass_photo(String class_photo) {
            this.class_photo = class_photo;
        }

        public String getClass_size() {
            return class_size;
        }

        public void setClass_size(String class_size) {
            this.class_size = class_size;
        }

        public String getClass_teacher() {
            return class_teacher;
        }

        public void setClass_teacher(String class_teacher) {
            this.class_teacher = class_teacher;
        }

        public String getClass_time() {
            return class_time;
        }

        public void setClass_time(String class_time) {
            this.class_time = class_time;
        }

        public String getCourse_id() {
            return course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public String getCourse_num() {
            return course_num;
        }

        public void setCourse_num(String course_num) {
            this.course_num = course_num;
        }

        public int getIs_coupons() {
            return is_coupons;
        }

        public void setIs_coupons(int is_coupons) {
            this.is_coupons = is_coupons;
        }

        public String getIs_gold() {
            return is_gold;
        }

        public void setIs_gold(String is_gold) {
            this.is_gold = is_gold;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_money() {
            return order_money;
        }

        public void setOrder_money(String order_money) {
            this.order_money = order_money;
        }

        public String getOrder_state() {
            return order_state;
        }

        public void setOrder_state(String order_state) {
            this.order_state = order_state;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getOrdre_time() {
            return ordre_time;
        }

        public void setOrdre_time(String ordre_time) {
            this.ordre_time = ordre_time;
        }

        public String getPay_num() {
            return pay_num;
        }

        public void setPay_num(String pay_num) {
            this.pay_num = pay_num;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
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

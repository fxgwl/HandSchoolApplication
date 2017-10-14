package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/2.
 */

public class ClassBean {

    /**
     * data : [{"data_integrity":"100","head_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1506565555092.jpg","mechanism_address":"1505459605958","mechanism_name":"我的小学堂b","mechanism_type":"早教","member_name":"","mid_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg,bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605993.jpg","qualification_prove":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605961.jpg,bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605967.jpg,bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605971.jpg,bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605977.jpg,bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605983.jpg","school_id":"1505805630235","signed_num":"0","user_code":"000000","user_create_time":"2017-09-15 15:07:22","user_dengji":"0","user_gold":"0","user_id":"4c975ad7eeb646f7b0e06cabc8290ddd","user_integral":"0","user_password":"0","user_phone":"15122947309","user_renqi":"4","user_state":"0","user_type":"1"},{"data_integrity":"0","head_photo":"head/naruto@2x.png","id_number":"3","mechanism_address":"1507602671716","mechanism_city":"北京顺义区旺泉街道","mechanism_ctime":"2","mechanism_name":"1","mechanism_type":"文体艺术/早教/托管","mid_photo":"bicths/816939bd23dc4296a4d1f3cd731b3143/1507602671749.jpg","qualification_prove":"bicths/816939bd23dc4296a4d1f3cd731b3143/1507602671739.jpg","school_id":"1507602671757","signed_num":"0","user_code":"000000","user_create_time":"2017-10-10 10:30:47","user_dengji":"0","user_gold":"0","user_id":"816939bd23dc4296a4d1f3cd731b3143","user_integral":"0","user_password":"1","user_phone":"27","user_renqi":"3","user_state":"0","user_type":"1"},{"data_integrity":"0","head_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","mechanism_name":"我的小学堂a","mechanism_type":"早教,托管","member_name":"","school_id":"1505805630236","signed_num":"0","user_code":"000000","user_create_time":"2017-09-15 14:41:51","user_dengji":"1","user_gold":"0","user_id":"8841f54f7b574f06a470ee9002043f8d","user_integral":"0","user_name":"自行车","user_password":"1","user_phone":"333333333333","user_renqi":"7","user_state":"0","user_type":"1"},{"data_integrity":"100","head_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","id_number":"123456789000123","mechanism_address":"1498475188492","mechanism_city":"天津市","mechanism_ctime":"2017-09-13","mechanism_name":"星星书画班","mechanism_type":"早教,托管,家教","member_name":"小星星","mid_photo":"1","qualification_prove":"1","school_id":"1505805630237","signed_num":"1","user_area":"天津市","user_code":"787303","user_create_time":"2017-09-13 15:34:49","user_dengji":"1","user_gold":"90","user_id":"8bda857e3f3c4acdb43ae2a7d1cdc412","user_integral":"90","user_name":"星","user_password":"1","user_phone":"15244166253","user_renqi":"0","user_sex":"男","user_state":"0","user_type":"1"},{"data_integrity":"100","head_photo":"bicths/ba28cc5ec0c449098285583a5f1efc4d/1507603975481.jpg","id_number":"3","mechanism_address":"1507602844937","mechanism_city":"北京顺义区旺泉街道","mechanism_ctime":"2","mechanism_name":"1","mechanism_type":"文体艺术/早教/托管","mid_photo":"bicths/ba28cc5ec0c449098285583a5f1efc4d/1507602844958.jpg","qualification_prove":"bicths/ba28cc5ec0c449098285583a5f1efc4d/1507602844941.jpg","school_id":"1507602844964","signed_num":"0","user_code":"000000","user_create_time":"2017-10-10 10:33:40","user_dengji":"0","user_gold":"0","user_id":"ba28cc5ec0c449098285583a5f1efc4d","user_integral":"0","user_password":"1","user_phone":"2","user_renqi":"0","user_state":"0","user_type":"1"}]
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
         * data_integrity : 100
         * head_photo : bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1506565555092.jpg
         * mechanism_address : 1505459605958
         * mechanism_name : 我的小学堂b
         * mechanism_type : 早教
         * member_name :
         * mid_photo : bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg,bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605993.jpg
         * qualification_prove : bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605961.jpg,bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605967.jpg,bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605971.jpg,bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605977.jpg,bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605983.jpg
         * school_id : 1505805630235
         * signed_num : 0
         * user_code : 000000
         * user_create_time : 2017-09-15 15:07:22
         * user_dengji : 0
         * user_gold : 0
         * user_id : 4c975ad7eeb646f7b0e06cabc8290ddd
         * user_integral : 0
         * user_password : 0
         * user_phone : 15122947309
         * user_renqi : 4
         * user_state : 0
         * user_type : 1
         * id_number : 3
         * mechanism_city : 北京顺义区旺泉街道
         * mechanism_ctime : 2
         * user_name : 自行车
         * user_area : 天津市
         * user_sex : 男
         */

        private String data_integrity;
        private String head_photo;
        private String mechanism_address;
        private String mechanism_name;
        private String mechanism_type;
        private String member_name;
        private String mid_photo;
        private String qualification_prove;
        private String school_id;
        private String signed_num;
        private String user_code;
        private String user_create_time;
        private String user_dengji;
        private String user_gold;
        private String user_id;
        private String user_integral;
        private String user_password;
        private String user_phone;
        private String user_renqi;
        private String user_state;
        private String user_type;
        private String id_number;
        private String mechanism_city;
        private String mechanism_ctime;
        private String user_name;
        private String user_area;
        private String user_sex;

        public String getData_integrity() {
            return data_integrity;
        }

        public void setData_integrity(String data_integrity) {
            this.data_integrity = data_integrity;
        }

        public String getHead_photo() {
            return head_photo;
        }

        public void setHead_photo(String head_photo) {
            this.head_photo = head_photo;
        }

        public String getMechanism_address() {
            return mechanism_address;
        }

        public void setMechanism_address(String mechanism_address) {
            this.mechanism_address = mechanism_address;
        }

        public String getMechanism_name() {
            return mechanism_name;
        }

        public void setMechanism_name(String mechanism_name) {
            this.mechanism_name = mechanism_name;
        }

        public String getMechanism_type() {
            return mechanism_type;
        }

        public void setMechanism_type(String mechanism_type) {
            this.mechanism_type = mechanism_type;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getMid_photo() {
            return mid_photo;
        }

        public void setMid_photo(String mid_photo) {
            this.mid_photo = mid_photo;
        }

        public String getQualification_prove() {
            return qualification_prove;
        }

        public void setQualification_prove(String qualification_prove) {
            this.qualification_prove = qualification_prove;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getSigned_num() {
            return signed_num;
        }

        public void setSigned_num(String signed_num) {
            this.signed_num = signed_num;
        }

        public String getUser_code() {
            return user_code;
        }

        public void setUser_code(String user_code) {
            this.user_code = user_code;
        }

        public String getUser_create_time() {
            return user_create_time;
        }

        public void setUser_create_time(String user_create_time) {
            this.user_create_time = user_create_time;
        }

        public String getUser_dengji() {
            return user_dengji;
        }

        public void setUser_dengji(String user_dengji) {
            this.user_dengji = user_dengji;
        }

        public String getUser_gold() {
            return user_gold;
        }

        public void setUser_gold(String user_gold) {
            this.user_gold = user_gold;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_integral() {
            return user_integral;
        }

        public void setUser_integral(String user_integral) {
            this.user_integral = user_integral;
        }

        public String getUser_password() {
            return user_password;
        }

        public void setUser_password(String user_password) {
            this.user_password = user_password;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUser_renqi() {
            return user_renqi;
        }

        public void setUser_renqi(String user_renqi) {
            this.user_renqi = user_renqi;
        }

        public String getUser_state() {
            return user_state;
        }

        public void setUser_state(String user_state) {
            this.user_state = user_state;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getId_number() {
            return id_number;
        }

        public void setId_number(String id_number) {
            this.id_number = id_number;
        }

        public String getMechanism_city() {
            return mechanism_city;
        }

        public void setMechanism_city(String mechanism_city) {
            this.mechanism_city = mechanism_city;
        }

        public String getMechanism_ctime() {
            return mechanism_ctime;
        }

        public void setMechanism_ctime(String mechanism_ctime) {
            this.mechanism_ctime = mechanism_ctime;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_area() {
            return user_area;
        }

        public void setUser_area(String user_area) {
            this.user_area = user_area;
        }

        public String getUser_sex() {
            return user_sex;
        }

        public void setUser_sex(String user_sex) {
            this.user_sex = user_sex;
        }
    }
}

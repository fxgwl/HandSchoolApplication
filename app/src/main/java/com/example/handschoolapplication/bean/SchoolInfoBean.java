package com.example.handschoolapplication.bean;

/**
 * Created by Administrator on 2017/10/10.
 */

public class SchoolInfoBean {

    /**
     * data : {"data_integrity":"100","head_photo":"head/naruto@2x.png","id_number":"3","mechanism_address":"1507602844937","mechanism_city":"北京顺义区旺泉街道","mechanism_ctime":"2","mechanism_name":"1","mechanism_type":"文体艺术/早教/托管","mid_photo":"bicths/ba28cc5ec0c449098285583a5f1efc4d/1507602844958.jpg","qualification_prove":"bicths/ba28cc5ec0c449098285583a5f1efc4d/1507602844941.jpg","school_id":"1507602844964","signed_num":"0","user_code":"000000","user_create_time":"2017-10-10 10:33:40","user_dengji":"0","user_gold":"0","user_id":"ba28cc5ec0c449098285583a5f1efc4d","user_integral":"0","user_password":"1","user_phone":"2","user_renqi":"0","user_state":"0","user_type":"1"}
     * msg : 成功
     * result : 0
     */

    private DataBean data;
    private String msg;
    private int result;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * data_integrity : 100
         * head_photo : head/naruto@2x.png
         * id_number : 3
         * mechanism_address : 1507602844937
         * mechanism_city : 北京顺义区旺泉街道
         * mechanism_ctime : 2
         * mechanism_name : 1
         * mechanism_type : 文体艺术/早教/托管
         * mid_photo : bicths/ba28cc5ec0c449098285583a5f1efc4d/1507602844958.jpg
         * qualification_prove : bicths/ba28cc5ec0c449098285583a5f1efc4d/1507602844941.jpg
         * school_id : 1507602844964
         * signed_num : 0
         * user_code : 000000
         * user_create_time : 2017-10-10 10:33:40
         * user_dengji : 0
         * user_gold : 0
         * user_id : ba28cc5ec0c449098285583a5f1efc4d
         * user_integral : 0
         * user_password : 1
         * user_phone : 2
         * user_renqi : 0
         * user_state : 0
         * user_type : 1
         * pingjia
         * totle_sign
         * glmoney
         * change_state
         */

        private String data_integrity;
        private String head_photo;
        private String id_number;
        private String id_numbers;
        private String mechanism_address;
        private String mechanism_city;
        private String mechanism_ctime;
        private String mechanism_name;
        private String mechanism_type;
        private String midphoto_card;
        private String midphoto_cards;
        private String mid_photos;
        private String mid_photo;
        private String qualification_prove;
        private String qualification_proves;
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
        private String user_name;
        private String pingjia;
        private String totle_sign;
        private String glmoney;
        private RatiosBean ratios;
        private String change_state;
        private String qualiprove_state;
        private String midphoto_state;
        private String mechanism_name_des;
        private String mechanism_name_img;
        private String mechanism_name_new;
        private String mechanism_name_state;

        public String getData_integrity() {
            return data_integrity == null ? "" : data_integrity;
        }

        public void setData_integrity(String data_integrity) {
            this.data_integrity = data_integrity;
        }

        public String getHead_photo() {
            return head_photo == null ? "" : head_photo;
        }

        public void setHead_photo(String head_photo) {
            this.head_photo = head_photo;
        }

        public String getId_number() {
            return id_number == null ? "" : id_number;
        }

        public void setId_number(String id_number) {
            this.id_number = id_number;
        }

        public String getMechanism_address() {
            return mechanism_address == null ? "" : mechanism_address;
        }

        public void setMechanism_address(String mechanism_address) {
            this.mechanism_address = mechanism_address;
        }

        public String getMechanism_city() {
            return mechanism_city == null ? "" : mechanism_city;
        }

        public void setMechanism_city(String mechanism_city) {
            this.mechanism_city = mechanism_city;
        }

        public String getMechanism_ctime() {
            return mechanism_ctime == null ? "" : mechanism_ctime;
        }

        public void setMechanism_ctime(String mechanism_ctime) {
            this.mechanism_ctime = mechanism_ctime;
        }

        public String getMechanism_name() {
            return mechanism_name == null ? "" : mechanism_name;
        }

        public void setMechanism_name(String mechanism_name) {
            this.mechanism_name = mechanism_name;
        }

        public String getMechanism_type() {
            return mechanism_type == null ? "" : mechanism_type;
        }

        public void setMechanism_type(String mechanism_type) {
            this.mechanism_type = mechanism_type;
        }

        public String getMidphoto_card() {
            return midphoto_card == null ? "" : midphoto_card;
        }

        public void setMidphoto_card(String midphoto_card) {
            this.midphoto_card = midphoto_card;
        }

        public String getMidphoto_cards() {
            return midphoto_cards == null ? "" : midphoto_cards;
        }

        public void setMidphoto_cards(String midphoto_cards) {
            this.midphoto_cards = midphoto_cards;
        }

        public String getQualification_prove() {
            return qualification_prove == null ? "" : qualification_prove;
        }

        public void setQualification_prove(String qualification_prove) {
            this.qualification_prove = qualification_prove;
        }

        public String getSchool_id() {
            return school_id == null ? "" : school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getSigned_num() {
            return signed_num == null ? "" : signed_num;
        }

        public void setSigned_num(String signed_num) {
            this.signed_num = signed_num;
        }

        public String getUser_code() {
            return user_code == null ? "" : user_code;
        }

        public void setUser_code(String user_code) {
            this.user_code = user_code;
        }

        public String getUser_create_time() {
            return user_create_time == null ? "" : user_create_time;
        }

        public void setUser_create_time(String user_create_time) {
            this.user_create_time = user_create_time;
        }

        public String getUser_dengji() {
            return user_dengji == null ? "" : user_dengji;
        }

        public void setUser_dengji(String user_dengji) {
            this.user_dengji = user_dengji;
        }

        public String getUser_gold() {
            return user_gold == null ? "" : user_gold;
        }

        public void setUser_gold(String user_gold) {
            this.user_gold = user_gold;
        }

        public String getUser_id() {
            return user_id == null ? "" : user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_integral() {
            return user_integral == null ? "" : user_integral;
        }

        public void setUser_integral(String user_integral) {
            this.user_integral = user_integral;
        }

        public String getUser_password() {
            return user_password == null ? "" : user_password;
        }

        public void setUser_password(String user_password) {
            this.user_password = user_password;
        }

        public String getUser_phone() {
            return user_phone == null ? "" : user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUser_renqi() {
            return user_renqi == null ? "" : user_renqi;
        }

        public void setUser_renqi(String user_renqi) {
            this.user_renqi = user_renqi;
        }

        public String getUser_state() {
            return user_state == null ? "" : user_state;
        }

        public void setUser_state(String user_state) {
            this.user_state = user_state;
        }

        public String getUser_type() {
            return user_type == null ? "" : user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getUser_name() {
            return user_name == null ? "" : user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getPingjia() {
            return pingjia == null ? "" : pingjia;
        }

        public void setPingjia(String pingjia) {
            this.pingjia = pingjia;
        }

        public String getTotle_sign() {
            return totle_sign == null ? "" : totle_sign;
        }

        public void setTotle_sign(String totle_sign) {
            this.totle_sign = totle_sign;
        }

        public String getGlmoney() {
            return glmoney == null ? "" : glmoney;
        }

        public void setGlmoney(String glmoney) {
            this.glmoney = glmoney;
        }

        public RatiosBean getRatios() {
            return ratios;
        }

        public void setRatios(RatiosBean ratios) {
            this.ratios = ratios;
        }

        public String getChange_state() {
            return change_state == null ? "" : change_state;
        }

        public void setChange_state(String change_state) {
            this.change_state = change_state;
        }

        public String getQualiprove_state() {
            return qualiprove_state == null ? "" : qualiprove_state;
        }

        public void setQualiprove_state(String qualiprove_state) {
            this.qualiprove_state = qualiprove_state;
        }

        public String getMidphoto_state() {
            return midphoto_state == null ? "" : midphoto_state;
        }

        public void setMidphoto_state(String midphoto_state) {
            this.midphoto_state = midphoto_state;
        }

        public String getMechanism_name_des() {
            return mechanism_name_des == null ? "" : mechanism_name_des;
        }

        public void setMechanism_name_des(String mechanism_name_des) {
            this.mechanism_name_des = mechanism_name_des;
        }

        public String getMechanism_name_img() {
            return mechanism_name_img == null ? "" : mechanism_name_img;
        }

        public void setMechanism_name_img(String mechanism_name_img) {
            this.mechanism_name_img = mechanism_name_img;
        }

        public String getMechanism_name_new() {
            return mechanism_name_new == null ? "" : mechanism_name_new;
        }

        public void setMechanism_name_new(String mechanism_name_new) {
            this.mechanism_name_new = mechanism_name_new;
        }

        public String getMechanism_name_state() {
            return mechanism_name_state == null ? "" : mechanism_name_state;
        }

        public void setMechanism_name_state(String mechanism_name_state) {
            this.mechanism_name_state = mechanism_name_state;
        }

        public String getId_numbers() {
            return id_numbers == null ? "" : id_numbers;
        }

        public void setId_numbers(String id_numbers) {
            this.id_numbers = id_numbers;
        }

        public String getMid_photos() {
            return mid_photos == null ? "" : mid_photos;
        }

        public void setMid_photos(String mid_photos) {
            this.mid_photos = mid_photos;
        }

        public String getMid_photo() {
            return mid_photo == null ? "" : mid_photo;
        }

        public void setMid_photo(String mid_photo) {
            this.mid_photo = mid_photo;
        }

        public String getQualification_proves() {
            return qualification_proves == null ? "" : qualification_proves;
        }

        public void setQualification_proves(String qualification_proves) {
            this.qualification_proves = qualification_proves;
        }

        public class RatiosBean{

            /**
             * goldnum : 100
             * ratio_id : 888
             * updated : 2018-03-22 12:01:51
             */

            private String goldnum;
            private String ratio_id;
            private String updated;

            public String getGoldnum() {
                return goldnum;
            }

            public void setGoldnum(String goldnum) {
                this.goldnum = goldnum;
            }

            public String getRatio_id() {
                return ratio_id;
            }

            public void setRatio_id(String ratio_id) {
                this.ratio_id = ratio_id;
            }

            public String getUpdated() {
                return updated;
            }

            public void setUpdated(String updated) {
                this.updated = updated;
            }
        }
    }
}

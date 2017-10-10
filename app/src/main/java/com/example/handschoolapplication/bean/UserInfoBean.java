package com.example.handschoolapplication.bean;

/**
 * Created by Administrator on 2017/10/9.
 */

public class UserInfoBean {

    /**
     * data : {"data_integrity":"83","head_photo":"bicths/205c2f34bc064712bf2e0157c142b6dd/1506758941250.jpg","id_number":"12342342412","member_name":"wo","signed_num":"0","user_area":"天津","user_code":"000000","user_create_time":"2017-09-15 12:17:28","user_dengji":"1","user_gold":"0","user_id":"205c2f34bc064712bf2e0157c142b6dd","user_integral":"0","user_name":"aedf","user_password":"1","user_phone":"1","user_sex":"男","user_state":"0","user_type":"0"}
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
        @Override
        public String toString() {
            return "DataBean{" +
                    "data_integrity='" + data_integrity + '\'' +
                    ", head_photo='" + head_photo + '\'' +
                    ", id_number='" + id_number + '\'' +
                    ", member_name='" + member_name + '\'' +
                    ", signed_num='" + signed_num + '\'' +
                    ", user_area='" + user_area + '\'' +
                    ", user_code='" + user_code + '\'' +
                    ", user_create_time='" + user_create_time + '\'' +
                    ", user_dengji='" + user_dengji + '\'' +
                    ", user_gold='" + user_gold + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", user_integral='" + user_integral + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", user_password='" + user_password + '\'' +
                    ", user_phone='" + user_phone + '\'' +
                    ", user_sex='" + user_sex + '\'' +
                    ", user_state='" + user_state + '\'' +
                    ", user_type='" + user_type + '\'' +
                    '}';
        }

        /**
         * data_integrity : 83
         * head_photo : bicths/205c2f34bc064712bf2e0157c142b6dd/1506758941250.jpg
         * id_number : 12342342412
         * member_name : wo
         * signed_num : 0
         * user_area : 天津
         * user_code : 000000
         * user_create_time : 2017-09-15 12:17:28
         * user_dengji : 1
         * user_gold : 0
         * user_id : 205c2f34bc064712bf2e0157c142b6dd
         * user_integral : 0
         * user_name : aedf
         * user_password : 1
         * user_phone : 1
         * user_sex : 男
         * user_state : 0
         * user_type : 0
         */


        private String data_integrity;
        private String head_photo;
        private String id_number;
        private String member_name;
        private String signed_num;
        private String user_area;
        private String user_code;
        private String user_create_time;
        private String user_dengji;
        private String user_gold;
        private String user_id;
        private String user_integral;
        private String user_name;
        private String user_password;
        private String user_phone;
        private String user_sex;
        private String user_state;
        private String user_type;

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

        public String getId_number() {
            return id_number;
        }

        public void setId_number(String id_number) {
            this.id_number = id_number;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getSigned_num() {
            return signed_num;
        }

        public void setSigned_num(String signed_num) {
            this.signed_num = signed_num;
        }

        public String getUser_area() {
            return user_area;
        }

        public void setUser_area(String user_area) {
            this.user_area = user_area;
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

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
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

        public String getUser_sex() {
            return user_sex;
        }

        public void setUser_sex(String user_sex) {
            this.user_sex = user_sex;
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
    }
}

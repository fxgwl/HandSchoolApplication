package com.example.handschoolapplication.bean;

/**
 * Created by Administrator on 2017/12/25.
 */

public class ThreeUserBean {


    /**
     * data : {"data_integrity":"0","glmoney":"0","head_photo":"head/1513936711806.jpg","pingjia":"0","signed_num":"0","totle_sign":"0","user_create_time":"2017-12-22 17:58:31","user_dengji":"0","user_gold":"0","user_id":"9b057144e36e4d6c961de982655f4a2f","user_integral":"0","user_name":"考虑好了咯哇魔域","user_renqi":"0","user_type":"0","weixin_id":"od4ok1ILsPeo9JKuWxs5nP0zW-FQ"}
     * msg : 登录成功
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
         * data_integrity : 0
         * glmoney : 0
         * head_photo : head/1513936711806.jpg
         * pingjia : 0
         * signed_num : 0
         * totle_sign : 0
         * user_create_time : 2017-12-22 17:58:31
         * user_dengji : 0
         * user_gold : 0
         * user_id : 9b057144e36e4d6c961de982655f4a2f
         * user_integral : 0
         * user_name : 考虑好了咯哇魔域
         * user_renqi : 0
         * user_type : 0
         * weixin_id : od4ok1ILsPeo9JKuWxs5nP0zW-FQ
         * user_phone : od4ok1ILsPeo9JKuWxs5nP0zW-FQ
         */

        private String data_integrity;
        private String glmoney;
        private String head_photo;
        private String pingjia;
        private String signed_num;
        private String totle_sign;
        private String user_create_time;
        private String user_dengji;
        private String user_gold;
        private String user_id;
        private String user_integral;
        private String user_name;
        private String user_renqi;
        private String user_type;
        private String weixin_id;
        private String user_phone;

        public String getData_integrity() {
            return data_integrity == null ? "" : data_integrity;
        }

        public void setData_integrity(String data_integrity) {
            this.data_integrity = data_integrity;
        }

        public String getGlmoney() {
            return glmoney == null ? "" : glmoney;
        }

        public void setGlmoney(String glmoney) {
            this.glmoney = glmoney;
        }

        public String getHead_photo() {
            return head_photo == null ? "" : head_photo;
        }

        public void setHead_photo(String head_photo) {
            this.head_photo = head_photo;
        }

        public String getPingjia() {
            return pingjia == null ? "" : pingjia;
        }

        public void setPingjia(String pingjia) {
            this.pingjia = pingjia;
        }

        public String getSigned_num() {
            return signed_num == null ? "" : signed_num;
        }

        public void setSigned_num(String signed_num) {
            this.signed_num = signed_num;
        }

        public String getTotle_sign() {
            return totle_sign == null ? "" : totle_sign;
        }

        public void setTotle_sign(String totle_sign) {
            this.totle_sign = totle_sign;
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

        public String getUser_name() {
            return user_name == null ? "" : user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_renqi() {
            return user_renqi == null ? "" : user_renqi;
        }

        public void setUser_renqi(String user_renqi) {
            this.user_renqi = user_renqi;
        }

        public String getUser_type() {
            return user_type == null ? "" : user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getWeixin_id() {
            return weixin_id == null ? "" : weixin_id;
        }

        public void setWeixin_id(String weixin_id) {
            this.weixin_id = weixin_id;
        }

        public String getUser_phone() {
            return user_phone == null ? "" : user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }
    }
}

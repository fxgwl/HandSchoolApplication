package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */

public class DiscountBean {


    /**
     * data : [{"coupons_id":7,"coupons_name":"chuan10","coupons_state":"0","coupons_type":"0","cuser_id":2,"discount_amount":"1","end_time":"2017-11-15 ","have_time":"2017-10-16 19:33:42","max_money":"10","start_time":"2017-09-15 ","user_id":"772d42a5a0014faa8706f7cd3f6e8828","user_name":"12","user_phone":"18330695171"},{"coupons_id":7,"coupons_name":"chuan10","coupons_state":"0","coupons_type":"0","cuser_id":3,"discount_amount":"1","end_time":"2017-11-15 ","have_time":"2017-10-19 12:04:38","max_money":"10","start_time":"2017-09-15 ","user_id":"772d42a5a0014faa8706f7cd3f6e8828","user_phone":"18330695171"},{"coupons_id":7,"coupons_name":"chuan10","coupons_state":"0","coupons_type":"0","cuser_id":4,"discount_amount":"1","end_time":"2017-11-15 ","have_time":"2017-10-19 12:06:21","max_money":"10","start_time":"2017-09-15 ","user_id":"772d42a5a0014faa8706f7cd3f6e8828","user_phone":"18330695171"},{"coupons_id":8,"coupons_name":"chuan11","coupons_state":"0","coupons_type":"0","cuser_id":5,"discount_amount":"10","end_time":"2017-11-16","have_time":"2017-10-19 12:07:25","max_money":"100","start_time":"2017-09-16","user_id":"772d42a5a0014faa8706f7cd3f6e8828","user_phone":"18330695171"},{"coupons_id":6,"coupons_name":"chuan1","coupons_state":"0","coupons_type":"0","cuser_id":6,"discount_amount":"10","end_time":"2017-10-25","have_time":"2017-10-19 12:12:39","max_money":"100","start_time":"2017-09-15","user_id":"772d42a5a0014faa8706f7cd3f6e8828","user_phone":"18330695171"},{"coupons_id":6,"coupons_name":"chuan1","coupons_state":"0","coupons_type":"0","cuser_id":7,"discount_amount":"10","end_time":"2017-10-25","have_time":"2017-10-19 12:13:00","max_money":"100","start_time":"2017-09-15","user_id":"772d42a5a0014faa8706f7cd3f6e8828","user_phone":"18330695171"},{"coupons_id":7,"coupons_name":"ccc","coupons_state":"0","coupons_type":"1","cuser_id":12,"discount_amount":"8","end_time":"2017-11-15","have_time":"2017-10-19 12:18:50","max_money":"18","school_id":"1505805630235","start_time":"2017-10-11","user_id":"772d42a5a0014faa8706f7cd3f6e8828","user_name":"","user_phone":"18330695171"},{"coupons_id":8,"coupons_name":"ccc","coupons_state":"0","coupons_type":"1","cuser_id":13,"discount_amount":"8","end_time":"2017-11-15","have_time":"2017-10-19 12:18:50","max_money":"18","school_id":"1505805630235","start_time":"2017-10-11","user_id":"772d42a5a0014faa8706f7cd3f6e8828","user_name":"","user_phone":"18330695171"}]
     * msg : 获取成功！
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
         * coupons_id : 7
         * coupons_name : chuan10
         * coupons_state : 0
         * coupons_type : 0
         * cuser_id : 2
         * discount_amount : 1
         * end_time : 2017-11-15
         * have_time : 2017-10-16 19:33:42
         * max_money : 10
         * start_time : 2017-09-15
         * user_id : 772d42a5a0014faa8706f7cd3f6e8828
         * user_name : 12
         * user_phone : 18330695171
         * school_id : 1505805630235
         */

        private int coupons_id;
        private String coupons_name;
        private String coupons_state;
        private String coupons_type;
        private int cuser_id;
        private String discount_amount;
        private String end_time;
        private String have_time;
        private String max_money;
        private String start_time;
        private String user_id;
        private String user_name;
        private String user_phone;
        private String school_id;

        public int getCoupons_id() {
            return coupons_id;
        }

        public void setCoupons_id(int coupons_id) {
            this.coupons_id = coupons_id;
        }

        public String getCoupons_name() {
            return coupons_name;
        }

        public void setCoupons_name(String coupons_name) {
            this.coupons_name = coupons_name;
        }

        public String getCoupons_state() {
            return coupons_state;
        }

        public void setCoupons_state(String coupons_state) {
            this.coupons_state = coupons_state;
        }

        public String getCoupons_type() {
            return coupons_type;
        }

        public void setCoupons_type(String coupons_type) {
            this.coupons_type = coupons_type;
        }

        public int getCuser_id() {
            return cuser_id;
        }

        public void setCuser_id(int cuser_id) {
            this.cuser_id = cuser_id;
        }

        public String getDiscount_amount() {
            return discount_amount;
        }

        public void setDiscount_amount(String discount_amount) {
            this.discount_amount = discount_amount;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getHave_time() {
            return have_time;
        }

        public void setHave_time(String have_time) {
            this.have_time = have_time;
        }

        public String getMax_money() {
            return max_money;
        }

        public void setMax_money(String max_money) {
            this.max_money = max_money;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
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

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }
    }
}

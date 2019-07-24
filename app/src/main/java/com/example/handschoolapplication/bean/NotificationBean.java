package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/2.
 */

public class NotificationBean {


    /**
     * data : [{"coupons_id":12,"coupons_name":"全店通用券","discount_amount":"5","end_time":"2018-06-23","inform_content":"点击此处领取","inform_id":746,"inform_name":"亲，点此领取优惠券","inform_state":"0","inform_type":"1","max_money":"100","message_time":"2018-06-22 16:51:00","start_time":"2018-06-22","user_id":"024e4f77d19342739136052148e22478"},{"coupons_id":9,"coupons_name":"测试","discount_amount":"10","end_time":"2018-06-22","inform_content":"点击此处领取","inform_id":726,"inform_name":"亲，点此领取优惠券","inform_state":"1","inform_type":"1","max_money":"10","message_time":"2018-06-22 12:06:21","start_time":"2018-06-05","user_id":"024e4f77d19342739136052148e22478"},{"coupons_id":8,"coupons_name":"测试","discount_amount":"10","end_time":"2018-06-22","inform_content":"点击此处领取","inform_id":723,"inform_name":"亲，点此领取优惠券","inform_state":"1","inform_type":"1","max_money":"10","message_time":"2018-06-22 12:06:17","start_time":"2018-06-05","user_id":"024e4f77d19342739136052148e22478"},{"inform_content":"恭喜您签到成功，获得金币","inform_id":718,"inform_name":"亲！","inform_state":"1","inform_type":"0","message_time":"2018-06-22 11:27:41","user_id":"024e4f77d19342739136052148e22478"},{"inform_content":"恭喜您签到成功，获得金币","inform_id":710,"inform_name":"亲！","inform_state":"1","inform_type":"0","message_time":"2018-06-21 17:06:02","user_id":"024e4f77d19342739136052148e22478"},{"coupons_id":7,"coupons_name":"系统优惠券2","discount_amount":"11","end_time":"2018-06-29","inform_content":"点击此处领取","inform_id":699,"inform_name":"亲，点此领取优惠券","inform_state":"1","inform_type":"1","max_money":"100","message_time":"2018-06-21 15:45:01","start_time":"2018-06-04","user_id":"024e4f77d19342739136052148e22478"}]
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
         * coupons_id : 12
         * coupons_name : 全店通用券
         * discount_amount : 5
         * end_time : 2018-06-23
         * inform_content : 点击此处领取
         * inform_id : 746
         * inform_name : 亲，点此领取优惠券
         * inform_state : 0
         * inform_type : 1
         * max_money : 100
         * message_time : 2018-06-22 16:51:00
         * start_time : 2018-06-22
         * user_id : 024e4f77d19342739136052148e22478
         */

        private int coupons_id;
        private String coupons_name;
        private String coupons_type;
        private String discount_amount;
        private String end_time;
        private String inform_content;
        private int inform_id;
        private String inform_name;
        private String inform_state;
        private String inform_type;
        private String max_money;
        private String message_time;
        private String start_time;
        private String user_id;

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

        public String getCoupons_type() {
            return coupons_type;
        }

        public void setCoupons_type(String coupons_type) {
            this.coupons_type = coupons_type;
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

        public String getInform_content() {
            return inform_content;
        }

        public void setInform_content(String inform_content) {
            this.inform_content = inform_content;
        }

        public int getInform_id() {
            return inform_id;
        }

        public void setInform_id(int inform_id) {
            this.inform_id = inform_id;
        }

        public String getInform_name() {
            return inform_name;
        }

        public void setInform_name(String inform_name) {
            this.inform_name = inform_name;
        }

        public String getInform_state() {
            return inform_state;
        }

        public void setInform_state(String inform_state) {
            this.inform_state = inform_state;
        }

        public String getInform_type() {
            return inform_type;
        }

        public void setInform_type(String inform_type) {
            this.inform_type = inform_type;
        }

        public String getMax_money() {
            return max_money;
        }

        public void setMax_money(String max_money) {
            this.max_money = max_money;
        }

        public String getMessage_time() {
            return message_time;
        }

        public void setMessage_time(String message_time) {
            this.message_time = message_time;
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
    }
}

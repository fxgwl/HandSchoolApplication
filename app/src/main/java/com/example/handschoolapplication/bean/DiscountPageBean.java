package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by zhw on 2018/6/12.
 */

public class DiscountPageBean {


    /**
     * data : [{"coupons_id":39,"coupons_name":"满减优惠券","coupons_num":"2","coupons_state":"0","coupons_time":"2018-06-13 09:58:21","coupons_type":"2","discount_amount":"5","discount_ratio":"0.05","end_time":"2018-06-15","max_money":"100","remaining_num":"2","school_id":"1528688197719","school_name":"测试学堂zhw","start_time":"2018-06-13"}]
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
         * coupons_id : 39
         * coupons_name : 满减优惠券
         * coupons_num : 2
         * coupons_state : 0
         * coupons_time : 2018-06-13 09:58:21
         * coupons_type : 2
         * discount_amount : 5
         * discount_ratio : 0.05
         * end_time : 2018-06-15
         * max_money : 100
         * remaining_num : 2
         * school_id : 1528688197719
         * school_name : 测试学堂zhw
         * start_time : 2018-06-13
         */

        private int coupons_id;
        private String coupons_name;
        private String coupons_num;
        private String coupons_state;
        private String coupon_state;
        private String coupons_time;
        private String coupons_type;
        private String discount_amount;
        private String discount_ratio;
        private String end_time;
        private String max_money;
        private String remaining_num;
        private String school_id;
        private String school_name;
        private String start_time;

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

        public String getCoupons_num() {
            return coupons_num;
        }

        public void setCoupons_num(String coupons_num) {
            this.coupons_num = coupons_num;
        }

        public String getCoupons_state() {
            return coupons_state;
        }

        public void setCoupons_state(String coupons_state) {
            this.coupons_state = coupons_state;
        }

        public String getCoupon_state() {
            return coupon_state;
        }

        public void setCoupon_state(String coupon_state) {
            this.coupon_state = coupon_state;
        }

        public String getCoupons_time() {
            return coupons_time;
        }

        public void setCoupons_time(String coupons_time) {
            this.coupons_time = coupons_time;
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

        public String getDiscount_ratio() {
            return discount_ratio;
        }

        public void setDiscount_ratio(String discount_ratio) {
            this.discount_ratio = discount_ratio;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getMax_money() {
            return max_money;
        }

        public void setMax_money(String max_money) {
            this.max_money = max_money;
        }

        public String getRemaining_num() {
            return remaining_num;
        }

        public void setRemaining_num(String remaining_num) {
            this.remaining_num = remaining_num;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }
    }
}

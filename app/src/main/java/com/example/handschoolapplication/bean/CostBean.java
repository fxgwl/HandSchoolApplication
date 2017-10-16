package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/14.
 */

public class CostBean {

    /**
     * data : [{"course_id":"1505888150982","price_id":1,"price_num":"400元/4节","price_time":"2017-09-20 14:44:34"},{"course_id":"1505888150982","price_id":2,"price_num":"800元/8节","price_time":"2017-09-20 14:45:11"}]
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
         * price_id : 1
         * price_num : 400元/4节
         * price_time : 2017-09-20 14:44:34
         */

        private String course_id;
        private int price_id;
        private String price_num;
        private String price_time;

        public String getCourse_id() {
            return course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public int getPrice_id() {
            return price_id;
        }

        public void setPrice_id(int price_id) {
            this.price_id = price_id;
        }

        public String getPrice_num() {
            return price_num;
        }

        public void setPrice_num(String price_num) {
            this.price_num = price_num;
        }

        public String getPrice_time() {
            return price_time;
        }

        public void setPrice_time(String price_time) {
            this.price_time = price_time;
        }
    }
}

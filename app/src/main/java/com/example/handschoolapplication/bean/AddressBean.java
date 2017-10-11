package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */

public class AddressBean {

    /**
     * data : [{"c_time":"2017-10-10 10:34:04","mechanism_address":"1507602844937","sd_city":"北京顺义区旺泉街道","sd_content":"4","sd_id":20},{"c_time":"2017-10-10 11:47:41","mechanism_address":"1507602844937","school_jing":"0","school_wei":"0","sd_city":"2812","sd_content":"123","sd_id":21},{"c_time":"2017-10-10 11:50:21","mechanism_address":"1507602844937","school_jing":"0","school_wei":"0","sd_city":"351038","sd_content":"2","sd_id":22}]
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
        @Override
        public String toString() {
            return "DataBean{" +
                    "c_time='" + c_time + '\'' +
                    ", mechanism_address='" + mechanism_address + '\'' +
                    ", sd_city='" + sd_city + '\'' +
                    ", sd_content='" + sd_content + '\'' +
                    ", sd_id=" + sd_id +
                    ", school_jing='" + school_jing + '\'' +
                    ", school_wei='" + school_wei + '\'' +
                    '}';
        }

        /**
         * c_time : 2017-10-10 10:34:04
         * mechanism_address : 1507602844937
         * sd_city : 北京顺义区旺泉街道
         * sd_content : 4
         * sd_id : 20
         * school_jing : 0
         * school_wei : 0
         */


        private String c_time;
        private String mechanism_address;
        private String sd_city;
        private String sd_content;
        private int sd_id;
        private String school_jing;
        private String school_wei;

        public String getC_time() {
            return c_time;
        }

        public void setC_time(String c_time) {
            this.c_time = c_time;
        }

        public String getMechanism_address() {
            return mechanism_address;
        }

        public void setMechanism_address(String mechanism_address) {
            this.mechanism_address = mechanism_address;
        }

        public String getSd_city() {
            return sd_city;
        }

        public void setSd_city(String sd_city) {
            this.sd_city = sd_city;
        }

        public String getSd_content() {
            return sd_content;
        }

        public void setSd_content(String sd_content) {
            this.sd_content = sd_content;
        }

        public int getSd_id() {
            return sd_id;
        }

        public void setSd_id(int sd_id) {
            this.sd_id = sd_id;
        }

        public String getSchool_jing() {
            return school_jing;
        }

        public void setSchool_jing(String school_jing) {
            this.school_jing = school_jing;
        }

        public String getSchool_wei() {
            return school_wei;
        }

        public void setSchool_wei(String school_wei) {
            this.school_wei = school_wei;
        }
    }
}

package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by zhw on 2018/7/10.
 */

public class ClassSchoolBean {


    /**
     * code : 0
     * data : [{"city":"北京市","district":"东城区","gakuen_id":"1535611048","gakuen_name":"1","gakuen_time":"2018-08-30 14:37:28","gakuen_type":"little","province":"北京市"},{"city":"北京市","district":"东城区","gakuen_id":"1535611057","gakuen_name":"2","gakuen_time":"2018-08-30 14:37:37","gakuen_type":"little","province":"北京市"},{"city":"北京市","district":"东城区","gakuen_id":"1535611064","gakuen_name":"3","gakuen_time":"2018-08-30 14:37:44","gakuen_type":"little","province":"北京市"},{"city":"北京市","district":"东城区","gakuen_id":"1535611072","gakuen_name":"4","gakuen_time":"2018-08-30 14:37:52","gakuen_type":"little","province":"北京市"},{"city":"北京市","district":"东城区","gakuen_id":"1535611022","gakuen_name":"11","gakuen_time":"2018-08-30 14:37:02","gakuen_type":"low_centre","province":"北京市"},{"city":"北京市","district":"东城区","gakuen_id":"1535611030","gakuen_name":"22","gakuen_time":"2018-08-30 14:37:10","gakuen_type":"low_centre","province":"北京市"},{"city":"北京市","district":"东城区","gakuen_id":"1535611037","gakuen_name":"33","gakuen_time":"2018-08-30 14:37:17","gakuen_type":"low_centre","province":"北京市"}]
     * msg : success
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * city : 北京市
         * district : 东城区
         * gakuen_id : 1535611048
         * gakuen_name : 1
         * gakuen_time : 2018-08-30 14:37:28
         * gakuen_type : little
         * province : 北京市
         */

        private String city;
        private String district;
        private String gakuen_id;
        private String gakuen_name;
        private String gakuen_time;
        private String gakuen_type;
        private String province;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getGakuen_id() {
            return gakuen_id;
        }

        public void setGakuen_id(String gakuen_id) {
            this.gakuen_id = gakuen_id;
        }

        public String getGakuen_name() {
            return gakuen_name;
        }

        public void setGakuen_name(String gakuen_name) {
            this.gakuen_name = gakuen_name;
        }

        public String getGakuen_time() {
            return gakuen_time;
        }

        public void setGakuen_time(String gakuen_time) {
            this.gakuen_time = gakuen_time;
        }

        public String getGakuen_type() {
            return gakuen_type;
        }

        public void setGakuen_type(String gakuen_type) {
            this.gakuen_type = gakuen_type;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }
    }
}

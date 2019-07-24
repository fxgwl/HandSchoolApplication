package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by zhw on 2018/7/10.
 */

public class PrimaryBean {


    /**
     * code : 0
     * data : [{"bind_city":"北京市","bind_district":"东城区","bind_id":33,"bind_name":"1","bind_province":"北京市","bind_time":"2018-08-30 14:38:48","bind_type":"little","gakuen_id":"1535611048","school_id":"1533264228251"},{"bind_city":"北京市","bind_district":"东城区","bind_id":34,"bind_name":"2","bind_province":"北京市","bind_time":"2018-08-30 14:38:48","bind_type":"little","gakuen_id":"1535611057","school_id":"1533264228251"},{"bind_city":"北京市","bind_district":"东城区","bind_id":35,"bind_name":"3","bind_province":"北京市","bind_time":"2018-08-30 14:38:48","bind_type":"little","gakuen_id":"1535611064","school_id":"1533264228251"},{"bind_city":"北京市","bind_district":"东城区","bind_id":36,"bind_name":"4","bind_province":"北京市","bind_time":"2018-08-30 14:38:48","bind_type":"little","gakuen_id":"1535611072","school_id":"1533264228251"},{"bind_city":"北京市","bind_district":"东城区","bind_id":30,"bind_name":"22","bind_province":"北京市","bind_time":"2018-08-30 14:38:32","bind_type":"low_centre","gakuen_id":"1535611030","school_id":"1533264228251"},{"bind_city":"北京市","bind_district":"东城区","bind_id":32,"bind_name":"33","bind_province":"北京市","bind_time":"2018-08-30 14:38:48","bind_type":"low_centre","gakuen_id":"1535611037","school_id":"1533264228251"},{"bind_city":"北京市","bind_district":"东城区","bind_id":37,"bind_name":"11","bind_province":"北京市","bind_time":"2018-08-30 14:38:48","bind_type":"low_centre","gakuen_id":"1535611022","school_id":"1533264228251"}]
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
         * bind_city : 北京市
         * bind_district : 东城区
         * bind_id : 33
         * bind_name : 1
         * bind_province : 北京市
         * bind_time : 2018-08-30 14:38:48
         * bind_type : little
         * gakuen_id : 1535611048
         * school_id : 1533264228251
         */

        private String bind_city;
        private String bind_district;
        private int bind_id;
        private String bind_name;
        private String bind_province;
        private String bind_time;
        private String bind_type;
        private String gakuen_id;
        private String school_id;

        public String getBind_city() {
            return bind_city;
        }

        public void setBind_city(String bind_city) {
            this.bind_city = bind_city;
        }

        public String getBind_district() {
            return bind_district;
        }

        public void setBind_district(String bind_district) {
            this.bind_district = bind_district;
        }

        public int getBind_id() {
            return bind_id;
        }

        public void setBind_id(int bind_id) {
            this.bind_id = bind_id;
        }

        public String getBind_name() {
            return bind_name;
        }

        public void setBind_name(String bind_name) {
            this.bind_name = bind_name;
        }

        public String getBind_province() {
            return bind_province;
        }

        public void setBind_province(String bind_province) {
            this.bind_province = bind_province;
        }

        public String getBind_time() {
            return bind_time;
        }

        public void setBind_time(String bind_time) {
            this.bind_time = bind_time;
        }

        public String getBind_type() {
            return bind_type;
        }

        public void setBind_type(String bind_type) {
            this.bind_type = bind_type;
        }

        public String getGakuen_id() {
            return gakuen_id;
        }

        public void setGakuen_id(String gakuen_id) {
            this.gakuen_id = gakuen_id;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }
    }
}

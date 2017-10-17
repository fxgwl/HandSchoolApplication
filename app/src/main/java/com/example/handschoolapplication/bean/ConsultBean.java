package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 */

public class ConsultBean {

    /**
     * data : [{"consult_content":"1","consult_id":41,"consult_name":"aedf","consult_photo":"bicths/205c2f34bc064712bf2e0157c142b6dd/1506758941250.jpg","consult_state":"0","consult_time":"2017-10-16 17:16:14","consult_type":"0","consult_userstate":"0","course_id":"1506153404911","school_name":"我的小学堂a","school_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","send_id":"8841f54f7b574f06a470ee9002043f8d","user_id":"205c2f34bc064712bf2e0157c142b6dd"},{"consult_content":"你们","consult_id":42,"consult_name":"aedf","consult_photo":"bicths/205c2f34bc064712bf2e0157c142b6dd/1506758941250.jpg","consult_state":"0","consult_time":"2017-10-16 17:18:17","consult_type":"0","consult_userstate":"0","course_id":"1506153404911","school_name":"我的小学堂a","school_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","send_id":"8841f54f7b574f06a470ee9002043f8d","user_id":"205c2f34bc064712bf2e0157c142b6dd"},{"consult_content":"阿萨德飞洒发士大夫","consult_id":43,"consult_name":"aedf","consult_photo":"bicths/205c2f34bc064712bf2e0157c142b6dd/1506758941250.jpg","consult_state":"0","consult_time":"2017-10-16 17:18:55","consult_type":"0","consult_userstate":"0","course_id":"1506153404911","school_name":"我的小学堂a","school_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","send_id":"8841f54f7b574f06a470ee9002043f8d","user_id":"205c2f34bc064712bf2e0157c142b6dd"},{"consult_content":"asdfsdafasd","consult_id":44,"consult_name":"aedf","consult_photo":"bicths/205c2f34bc064712bf2e0157c142b6dd/1506758941250.jpg","consult_state":"0","consult_time":"2017-10-17 09:03:26","consult_type":"0","consult_userstate":"0","course_id":"1506153404911","school_name":"我的小学堂a","school_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","send_id":"8841f54f7b574f06a470ee9002043f8d","user_id":"205c2f34bc064712bf2e0157c142b6dd"},{"consult_content":"sdafasf","consult_id":45,"consult_name":"aedf","consult_photo":"bicths/205c2f34bc064712bf2e0157c142b6dd/1506758941250.jpg","consult_state":"0","consult_time":"2017-10-17 09:05:24","consult_type":"0","consult_userstate":"0","course_id":"1506153404911","school_name":"我的小学堂a","school_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","send_id":"8841f54f7b574f06a470ee9002043f8d","user_id":"205c2f34bc064712bf2e0157c142b6dd"},{"consult_content":"阿斯顿发发撒打发士大夫","consult_id":46,"consult_name":"aedf","consult_photo":"bicths/205c2f34bc064712bf2e0157c142b6dd/1506758941250.jpg","consult_state":"0","consult_time":"2017-10-17 09:05:46","consult_type":"0","consult_userstate":"0","course_id":"1506153404911","school_name":"我的小学堂a","school_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","send_id":"8841f54f7b574f06a470ee9002043f8d","user_id":"205c2f34bc064712bf2e0157c142b6dd"},{"consult_content":"撒打发士大夫","consult_id":47,"consult_name":"aedf","consult_photo":"bicths/205c2f34bc064712bf2e0157c142b6dd/1506758941250.jpg","consult_state":"0","consult_time":"2017-10-17 09:14:58","consult_type":"0","consult_userstate":"0","course_id":"1506153404911","school_name":"我的小学堂a","school_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg","send_id":"8841f54f7b574f06a470ee9002043f8d","user_id":"205c2f34bc064712bf2e0157c142b6dd"}]
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
         * consult_content : 1
         * consult_id : 41
         * consult_name : aedf
         * consult_photo : bicths/205c2f34bc064712bf2e0157c142b6dd/1506758941250.jpg
         * consult_state : 0
         * consult_time : 2017-10-16 17:16:14
         * consult_type : 0
         * consult_userstate : 0
         * course_id : 1506153404911
         * school_name : 我的小学堂a
         * school_photo : bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1505459605990.jpg
         * send_id : 8841f54f7b574f06a470ee9002043f8d
         * user_id : 205c2f34bc064712bf2e0157c142b6dd
         */

        private String consult_content;
        private int consult_id;
        private String consult_name;
        private String consult_photo;
        private String consult_state;
        private String consult_time;
        private String consult_type;
        private String consult_userstate;
        private String course_id;
        private String school_name;
        private String school_photo;
        private String send_id;
        private String user_id;

        public String getConsult_content() {
            return consult_content;
        }

        public void setConsult_content(String consult_content) {
            this.consult_content = consult_content;
        }

        public int getConsult_id() {
            return consult_id;
        }

        public void setConsult_id(int consult_id) {
            this.consult_id = consult_id;
        }

        public String getConsult_name() {
            return consult_name;
        }

        public void setConsult_name(String consult_name) {
            this.consult_name = consult_name;
        }

        public String getConsult_photo() {
            return consult_photo;
        }

        public void setConsult_photo(String consult_photo) {
            this.consult_photo = consult_photo;
        }

        public String getConsult_state() {
            return consult_state;
        }

        public void setConsult_state(String consult_state) {
            this.consult_state = consult_state;
        }

        public String getConsult_time() {
            return consult_time;
        }

        public void setConsult_time(String consult_time) {
            this.consult_time = consult_time;
        }

        public String getConsult_type() {
            return consult_type;
        }

        public void setConsult_type(String consult_type) {
            this.consult_type = consult_type;
        }

        public String getConsult_userstate() {
            return consult_userstate;
        }

        public void setConsult_userstate(String consult_userstate) {
            this.consult_userstate = consult_userstate;
        }

        public String getCourse_id() {
            return course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getSchool_photo() {
            return school_photo;
        }

        public void setSchool_photo(String school_photo) {
            this.school_photo = school_photo;
        }

        public String getSend_id() {
            return send_id;
        }

        public void setSend_id(String send_id) {
            this.send_id = send_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}

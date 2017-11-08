package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ContactServiceBean {


    /**
     * data : [{"consult_content":"女性向","consult_id":142,"consult_name":"菲菲","consult_photo":"bicths/772d42a5a0014faa8706f7cd3f6e8828/1510036670890.jpg","consult_state":"0","consult_time":"2017-11-04 17:18:45","consult_type":"0","consult_userstate":"0","course_id":"1505888221351","school_name":"我的小学堂b","school_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1509764639902.jpg","send_id":"4c975ad7eeb646f7b0e06cabc8290ddd","user_id":"772d42a5a0014faa8706f7cd3f6e8828"},{"consult_content":"在哪都觉得","consult_id":143,"consult_name":"菲菲","consult_photo":"bicths/772d42a5a0014faa8706f7cd3f6e8828/1510036670890.jpg","consult_state":"0","consult_time":"2017-11-04 17:19:35","consult_type":"0","consult_userstate":"0","course_id":"1505888221351","school_name":"我的小学堂b","school_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1509764639902.jpg","send_id":"4c975ad7eeb646f7b0e06cabc8290ddd","user_id":"772d42a5a0014faa8706f7cd3f6e8828"},{"consult_content":"想你附近的","consult_id":144,"consult_name":"菲菲","consult_photo":"bicths/772d42a5a0014faa8706f7cd3f6e8828/1510036670890.jpg","consult_state":"0","consult_time":"2017-11-04 17:19:43","consult_type":"0","consult_userstate":"0","course_id":"1505888221351","school_name":"我的小学堂b","school_photo":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1509764639902.jpg","send_id":"4c975ad7eeb646f7b0e06cabc8290ddd","user_id":"772d42a5a0014faa8706f7cd3f6e8828"}]
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
         * consult_content : 女性向
         * consult_id : 142
         * consult_name : 菲菲
         * consult_photo : bicths/772d42a5a0014faa8706f7cd3f6e8828/1510036670890.jpg
         * consult_state : 0
         * consult_time : 2017-11-04 17:18:45
         * consult_type : 0
         * consult_userstate : 0
         * course_id : 1505888221351
         * school_name : 我的小学堂b
         * school_photo : bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1509764639902.jpg
         * send_id : 4c975ad7eeb646f7b0e06cabc8290ddd
         * user_id : 772d42a5a0014faa8706f7cd3f6e8828
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

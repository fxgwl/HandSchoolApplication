package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */

public class NewsBean {


    /**
     * data : [{"consult_content":"这是","consult_id":0,"consult_time":"2017-10-21 18:57:54","course_id":"1505888150982","school_name":"我的小学堂b","school_photo":"images/head.jpg","send_id":"4c975ad7eeb646f7b0e06cabc8290ddd","user_id":"772d42a5a0014faa8706f7cd3f6e8828"}]
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
                    "consult_content='" + consult_content + '\'' +
                    ", consult_id=" + consult_id +
                    ", consult_time='" + consult_time + '\'' +
                    ", course_id='" + course_id + '\'' +
                    ", school_name='" + school_name + '\'' +
                    ", school_photo='" + school_photo + '\'' +
                    ", send_id='" + send_id + '\'' +
                    ", user_id='" + user_id + '\'' +
                    '}';
        }

        /**
         * consult_content : 这是
         * consult_id : 0
         * consult_time : 2017-10-21 18:57:54
         * course_id : 1505888150982
         * school_name : 我的小学堂b
         * school_photo : images/head.jpg
         * send_id : 4c975ad7eeb646f7b0e06cabc8290ddd
         * user_id : 772d42a5a0014faa8706f7cd3f6e8828
         */


        private String consult_content;
        private int consult_id;
        private String consult_time;
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

        public String getConsult_time() {
            return consult_time;
        }

        public void setConsult_time(String consult_time) {
            this.consult_time = consult_time;
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

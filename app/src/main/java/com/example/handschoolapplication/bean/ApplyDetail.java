package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/2.
 */

public class ApplyDetail {

    /**
     * data : [{"all_class":"4","course_id":"1505888221350","created":"2017-11-06","csign_id":2,"study_class":"4","user_head":"bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1509764639902.jpg","user_id":"4c975ad7eeb646f7b0e06cabc8290ddd","user_name":"dasff"},{"all_class":"4","course_id":"1505888221350","created":"2017-11-06","csign_id":3,"study_class":"4","user_head":"images/head.jpg","user_id":"772d42a5a0014faa8706f7cd3f6e8828","user_name":"自行车"}]
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
         * all_class : 4
         * course_id : 1505888221350
         * created : 2017-11-06
         * csign_id : 2
         * study_class : 4
         * user_head : bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1509764639902.jpg
         * user_id : 4c975ad7eeb646f7b0e06cabc8290ddd
         * user_name : dasff
         */

        private String all_class;
        private String course_id;
        private String created;
        private int csign_id;
        private String study_class;
        private String user_head;
        private String user_id;
        private String user_name;

        public String getAll_class() {
            return all_class;
        }

        public void setAll_class(String all_class) {
            this.all_class = all_class;
        }

        public String getCourse_id() {
            return course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public int getCsign_id() {
            return csign_id;
        }

        public void setCsign_id(int csign_id) {
            this.csign_id = csign_id;
        }

        public String getStudy_class() {
            return study_class;
        }

        public void setStudy_class(String study_class) {
            this.study_class = study_class;
        }

        public String getUser_head() {
            return user_head;
        }

        public void setUser_head(String user_head) {
            this.user_head = user_head;
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
    }
}

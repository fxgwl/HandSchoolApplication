package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */

public class ClassInfoCHP {


    /**
     * data : {"head_photo":"images/head.jpg","schoolDataMap":[{"class_num":"3","collect_num":"1","school_name":"我的小学堂a"}],"school_id":"1505805630236","user_dengji":"0","user_id":"8841f54f7b574f06a470ee9002043f8d"}
     * msg : 成功
     * result : 0
     */

    private DataBean data;
    private String msg;
    private int result;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * head_photo : images/head.jpg
         * schoolDataMap : [{"class_num":"3","collect_num":"1","school_name":"我的小学堂a"}]
         * school_id : 1505805630236
         * user_dengji : 0
         * user_id : 8841f54f7b574f06a470ee9002043f8d
         */

        private String head_photo;
        private String school_id;
        private String user_dengji;
        private String user_id;
        private List<SchoolDataMapBean> schoolDataMap;

        public String getHead_photo() {
            return head_photo;
        }

        public void setHead_photo(String head_photo) {
            this.head_photo = head_photo;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getUser_dengji() {
            return user_dengji;
        }

        public void setUser_dengji(String user_dengji) {
            this.user_dengji = user_dengji;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public List<SchoolDataMapBean> getSchoolDataMap() {
            return schoolDataMap;
        }

        public void setSchoolDataMap(List<SchoolDataMapBean> schoolDataMap) {
            this.schoolDataMap = schoolDataMap;
        }

        public static class SchoolDataMapBean {
            /**
             * class_num : 3
             * collect_num : 1
             * school_name : 我的小学堂a
             */

            private String class_num;
            private String collect_num;
            private String school_name;

            public String getClass_num() {
                return class_num;
            }

            public void setClass_num(String class_num) {
                this.class_num = class_num;
            }

            public String getCollect_num() {
                return collect_num;
            }

            public void setCollect_num(String collect_num) {
                this.collect_num = collect_num;
            }

            public String getSchool_name() {
                return school_name;
            }

            public void setSchool_name(String school_name) {
                this.school_name = school_name;
            }
        }
    }
}

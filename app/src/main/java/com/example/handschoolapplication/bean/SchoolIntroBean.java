package com.example.handschoolapplication.bean;

/**
 * Created by Administrator on 2017/10/12.
 */

public class SchoolIntroBean {

    /**
     * data : {"head_photo":"bicths/ba28cc5ec0c449098285583a5f1efc4d/1507603975481.jpg","schoolData":{"school_name":"1","school_renqi":"0","school_time":"2017-10-10 10:34:04"},"school_id":"1507602844964","user_dengji":"0","user_id":"ba28cc5ec0c449098285583a5f1efc4d","user_renqi":"0"}
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
         * head_photo : bicths/ba28cc5ec0c449098285583a5f1efc4d/1507603975481.jpg
         * schoolData : {"school_name":"1","school_renqi":"0","school_time":"2017-10-10 10:34:04"}
         * school_id : 1507602844964
         * user_dengji : 0
         * user_id : ba28cc5ec0c449098285583a5f1efc4d
         * user_renqi : 0
         */

        private String head_photo;
        private SchoolDataBean schoolData;
        private String school_id;
        private String user_dengji;
        private String user_id;
        private String user_renqi;

        public String getHead_photo() {
            return head_photo;
        }

        public void setHead_photo(String head_photo) {
            this.head_photo = head_photo;
        }

        public SchoolDataBean getSchoolData() {
            return schoolData;
        }

        public void setSchoolData(SchoolDataBean schoolData) {
            this.schoolData = schoolData;
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

        public String getUser_renqi() {
            return user_renqi;
        }

        public void setUser_renqi(String user_renqi) {
            this.user_renqi = user_renqi;
        }

        public static class SchoolDataBean {
            /**
             * school_name : 1
             * school_renqi : 0
             * school_time : 2017-10-10 10:34:04
             */

            private String school_name;
            private String school_renqi;
            private String school_time;
            private String school_environment;
            private String school_jing;
            private String school_wei;
            private String school_num;
            private String school_profile;

            public String getSchool_name() {
                return school_name;
            }

            public void setSchool_name(String school_name) {
                this.school_name = school_name;
            }

            public String getSchool_renqi() {
                return school_renqi;
            }

            public void setSchool_renqi(String school_renqi) {
                this.school_renqi = school_renqi;
            }

            public String getSchool_time() {
                return school_time;
            }

            public void setSchool_time(String school_time) {
                this.school_time = school_time;
            }

            public String getSchool_environment() {
                return school_environment;
            }

            public void setSchool_environment(String school_environment) {
                this.school_environment = school_environment;
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

            public String getSchool_num() {
                return school_num;
            }

            public void setSchool_num(String school_num) {
                this.school_num = school_num;
            }

            public String getSchool_profile() {
                return school_profile;
            }

            public void setSchool_profile(String school_profile) {
                this.school_profile = school_profile;
            }
        }
    }
}

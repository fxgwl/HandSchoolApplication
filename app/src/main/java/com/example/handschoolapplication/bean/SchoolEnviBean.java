package com.example.handschoolapplication.bean;

import java.util.List;

public class SchoolEnviBean {


    /**
     * data : {"head_photo":"head/initial.jpg","mechanism_address":"1539746997764","pingjia":"0","schoolAddresses":[{"c_time":"2018-10-17 11:29:57","mechanism_address":"1539746997764","school_jing":"117.216109","school_wei":"39.113605","sd_city":"天津天津河东区","sd_content":"南京路与马场道交汇处天津国际贸易中心","sd_id":35}],"schoolData":{"picture_one":"2018/10/17/1539749142608.jpg","picture_three":"","picture_two":"2018/10/17/1539749150704.jpg","school_jing":"117.216109","school_name":"测试学堂zhw","school_profile":"<p>说不定就爱哭不是看见第八届，失败的，家，寸步难行装甲车不是大家可否不打算，你付款机电设备发链接看电视呢会计法VBask环境是霸气外滑动文件萨满的卢卡斯南大街那是多久不撒谎不打卡节哀顺变打开就暗示你的快乐不是重新美女不是可敬的部分开始的你的， 你不打死你的空间搞丢你发了让那个佛你还不到时间发布速度快解放后期五年的会计师VB擦拭的不放假不可端倪；我拿的挎包内容分工森福罗你<\/p>","school_renqi":"0","school_time":"2018-10-17 11:29:57","school_wei":"39.113605","sd_id":0},"school_id":"1539746997803","user_dengji":"0","user_id":"dba5c60e36234f92a9580c26c0fd5bf2","user_renqi":"0"}
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
         * head_photo : head/initial.jpg
         * mechanism_address : 1539746997764
         * pingjia : 0
         * schoolAddresses : [{"c_time":"2018-10-17 11:29:57","mechanism_address":"1539746997764","school_jing":"117.216109","school_wei":"39.113605","sd_city":"天津天津河东区","sd_content":"南京路与马场道交汇处天津国际贸易中心","sd_id":35}]
         * schoolData : {"picture_one":"2018/10/17/1539749142608.jpg","picture_three":"","picture_two":"2018/10/17/1539749150704.jpg","school_jing":"117.216109","school_name":"测试学堂zhw","school_profile":"<p>说不定就爱哭不是看见第八届，失败的，家，寸步难行装甲车不是大家可否不打算，你付款机电设备发链接看电视呢会计法VBask环境是霸气外滑动文件萨满的卢卡斯南大街那是多久不撒谎不打卡节哀顺变打开就暗示你的快乐不是重新美女不是可敬的部分开始的你的， 你不打死你的空间搞丢你发了让那个佛你还不到时间发布速度快解放后期五年的会计师VB擦拭的不放假不可端倪；我拿的挎包内容分工森福罗你<\/p>","school_renqi":"0","school_time":"2018-10-17 11:29:57","school_wei":"39.113605","sd_id":0}
         * school_id : 1539746997803
         * user_dengji : 0
         * user_id : dba5c60e36234f92a9580c26c0fd5bf2
         * user_renqi : 0
         */

        private String head_photo;
        private String mechanism_address;
        private String pingjia;
        private SchoolDataBean schoolData;
        private String school_id;
        private String user_dengji;
        private String user_id;
        private String user_renqi;
        private List<SchoolAddressesBean> schoolAddresses;

        public String getHead_photo() {
            return head_photo;
        }

        public void setHead_photo(String head_photo) {
            this.head_photo = head_photo;
        }

        public String getMechanism_address() {
            return mechanism_address;
        }

        public void setMechanism_address(String mechanism_address) {
            this.mechanism_address = mechanism_address;
        }

        public String getPingjia() {
            return pingjia;
        }

        public void setPingjia(String pingjia) {
            this.pingjia = pingjia;
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

        public List<SchoolAddressesBean> getSchoolAddresses() {
            return schoolAddresses;
        }

        public void setSchoolAddresses(List<SchoolAddressesBean> schoolAddresses) {
            this.schoolAddresses = schoolAddresses;
        }

        public static class SchoolDataBean {
            /**
             * picture_one : 2018/10/17/1539749142608.jpg
             * picture_three :
             * picture_two : 2018/10/17/1539749150704.jpg
             * school_jing : 117.216109
             * school_name : 测试学堂zhw
             * school_profile : <p>说不定就爱哭不是看见第八届，失败的，家，寸步难行装甲车不是大家可否不打算，你付款机电设备发链接看电视呢会计法VBask环境是霸气外滑动文件萨满的卢卡斯南大街那是多久不撒谎不打卡节哀顺变打开就暗示你的快乐不是重新美女不是可敬的部分开始的你的， 你不打死你的空间搞丢你发了让那个佛你还不到时间发布速度快解放后期五年的会计师VB擦拭的不放假不可端倪；我拿的挎包内容分工森福罗你</p>
             * school_renqi : 0
             * school_time : 2018-10-17 11:29:57
             * school_wei : 39.113605
             * sd_id : 0
             */

            private String picture_one;
            private String picture_three;
            private String picture_two;
            private String school_jing;
            private String school_name;
            private String school_profile;
            private String school_renqi;
            private String school_time;
            private String school_wei;
            private int sd_id;

            public String getPicture_one() {
                return picture_one;
            }

            public void setPicture_one(String picture_one) {
                this.picture_one = picture_one;
            }

            public String getPicture_three() {
                return picture_three;
            }

            public void setPicture_three(String picture_three) {
                this.picture_three = picture_three;
            }

            public String getPicture_two() {
                return picture_two;
            }

            public void setPicture_two(String picture_two) {
                this.picture_two = picture_two;
            }

            public String getSchool_jing() {
                return school_jing;
            }

            public void setSchool_jing(String school_jing) {
                this.school_jing = school_jing;
            }

            public String getSchool_name() {
                return school_name;
            }

            public void setSchool_name(String school_name) {
                this.school_name = school_name;
            }

            public String getSchool_profile() {
                return school_profile;
            }

            public void setSchool_profile(String school_profile) {
                this.school_profile = school_profile;
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

            public String getSchool_wei() {
                return school_wei;
            }

            public void setSchool_wei(String school_wei) {
                this.school_wei = school_wei;
            }

            public int getSd_id() {
                return sd_id;
            }

            public void setSd_id(int sd_id) {
                this.sd_id = sd_id;
            }
        }

        public static class SchoolAddressesBean {
            /**
             * c_time : 2018-10-17 11:29:57
             * mechanism_address : 1539746997764
             * school_jing : 117.216109
             * school_wei : 39.113605
             * sd_city : 天津天津河东区
             * sd_content : 南京路与马场道交汇处天津国际贸易中心
             * sd_id : 35
             */

            private String c_time;
            private String mechanism_address;
            private String school_jing;
            private String school_wei;
            private String sd_city;
            private String sd_content;
            private int sd_id;

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
        }
    }
}

package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/16.
 */

public class SchoolFans {


    /**
     * data : [{"collect_sid":17,"collect_time":"2018-03-17 14:14:39","school_id":"1512784945461","school_jifen":"0","school_logo":"bicths/246ab4ef31c24c22aefb4880fa2248d0/1512813797750.jpg","school_name":"安鑫一家","userInfo":{"data_integrity":"50","glmoney":"9876543210","head_photo":"bicths/d2da9c8b335a4bcfba87e29052e288ad/1512813734497.jpg","pingjia":"0","signed_num":"1","totle_sign":"3","user_area":"地区","user_code":"000000","user_create_time":"2017-12-09 11:54:28","user_dengji":"0","user_gold":"30","user_id":"d2da9c8b335a4bcfba87e29052e288ad","user_integral":"30","user_name":"用户昵称","user_password":"0","user_phone":"18333616097","user_renqi":"0","user_sex":"男","user_state":"0","user_type":"0"},"user_id":"d2da9c8b335a4bcfba87e29052e288ad","user_name":"用户昵称","user_phone":"18333616097"},{"collect_sid":18,"collect_time":"2018-03-17 14:14:44","school_id":"1512784945461","school_jifen":"0","school_logo":"bicths/246ab4ef31c24c22aefb4880fa2248d0/1512813797750.jpg","school_name":"安鑫一家","userInfo":{"data_integrity":"50","glmoney":"5","head_photo":"bicths/e5eee6c6dd15479298bf258030d80585/1512785767202.jpg","pingjia":"0","signed_num":"1","totle_sign":"11","user_area":"地区","user_code":"000000","user_create_time":"2017-12-09 09:50:46","user_dengji":"0","user_gold":"10040","user_id":"e5eee6c6dd15479298bf258030d80585","user_integral":"110","user_name":"用户昵称","user_password":"1","user_phone":"17695545423","user_renqi":"0","user_sex":"男","user_state":"0","user_type":"0"},"user_id":"e5eee6c6dd15479298bf258030d80585","user_name":"用户昵称","user_phone":"17695545423"},{"collect_sid":19,"collect_time":"2018-03-17 15:49:35","school_id":"1512784945461","school_jifen":"0","school_logo":"bicths/10c5bb01a3c8480da20ee09367fdb111/1512790579136.jpg","school_name":"安鑫一家","userInfo":{"data_integrity":"100","glmoney":"0","head_photo":"bicths/10c5bb01a3c8480da20ee09367fdb111/1512790579136.jpg","id_number":"130524199205133025","mechanism_address":"1512784945258","mechanism_city":"天津市-河西区","mechanism_ctime":"20170513","mechanism_name":"安鑫一家","mechanism_type":"文体艺术//音乐:二胡;文体艺术//书画;学习辅导//中小学教育:小学/中学/数学/语文/物理;活动拓展//夏令营;早教;托教;家教","member_name":"123456789","mid_photo":"bicths/10c5bb01a3c8480da20ee09367fdb111/1512784945456.jpg","pingjia":"5","qualification_prove":"bicths/10c5bb01a3c8480da20ee09367fdb111/1512784945362.jpg","school_id":"1512784945461","signed_num":"1","totle_sign":"2","user_area":"39.12","user_code":"000000","user_create_time":"2017-12-09 09:49:38","user_dengji":"0","user_gold":"20","user_id":"10c5bb01a3c8480da20ee09367fdb111","user_integral":"20","user_name":"117.22 ","user_password":"qqqqqq1","user_phone":"15122947309","user_renqi":"29","user_state":"0","user_type":"1"},"user_id":"10c5bb01a3c8480da20ee09367fdb111","user_name":"安鑫一家","user_phone":"15122947309"},{"collect_sid":20,"collect_time":"2018-03-17 16:21:35","school_id":"1512784945461","school_jifen":"0","school_logo":"bicths/10c5bb01a3c8480da20ee09367fdb111/1512790579136.jpg","school_name":"安鑫一家","userInfo":{"data_integrity":"67","glmoney":"0","head_photo":"bicths/d198f3efb0444c7580052ad3ec6bfce1/1521274797682.jpg","member_name":"m\u2006p\u2006p","pingjia":"0","signed_num":"0","totle_sign":"0","user_area":"地区","user_code":"000000","user_create_time":"2018-03-17 15:52:30","user_dengji":"0","user_gold":"0","user_id":"d198f3efb0444c7580052ad3ec6bfce1","user_integral":"0","user_name":"呃呃呃","user_password":"qqqqqq1","user_phone":"15122947406","user_renqi":"0","user_sex":"男","user_state":"0","user_type":"0"},"user_id":"d198f3efb0444c7580052ad3ec6bfce1","user_name":"呃呃呃","user_phone":"15122947406"}]
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
         * collect_sid : 17
         * collect_time : 2018-03-17 14:14:39
         * school_id : 1512784945461
         * school_jifen : 0
         * school_logo : bicths/246ab4ef31c24c22aefb4880fa2248d0/1512813797750.jpg
         * school_name : 安鑫一家
         * userInfo : {"data_integrity":"50","glmoney":"9876543210","head_photo":"bicths/d2da9c8b335a4bcfba87e29052e288ad/1512813734497.jpg","pingjia":"0","signed_num":"1","totle_sign":"3","user_area":"地区","user_code":"000000","user_create_time":"2017-12-09 11:54:28","user_dengji":"0","user_gold":"30","user_id":"d2da9c8b335a4bcfba87e29052e288ad","user_integral":"30","user_name":"用户昵称","user_password":"0","user_phone":"18333616097","user_renqi":"0","user_sex":"男","user_state":"0","user_type":"0"}
         * user_id : d2da9c8b335a4bcfba87e29052e288ad
         * user_name : 用户昵称
         * user_phone : 18333616097
         */

        private int collect_sid;
        private String collect_time;
        private String school_id;
        private String school_jifen;
        private String school_logo;
        private String school_name;
        private UserInfoBean userInfo;
        private String user_id;
        private String user_name;
        private String user_phone;

        public int getCollect_sid() {
            return collect_sid;
        }

        public void setCollect_sid(int collect_sid) {
            this.collect_sid = collect_sid;
        }

        public String getCollect_time() {
            return collect_time;
        }

        public void setCollect_time(String collect_time) {
            this.collect_time = collect_time;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getSchool_jifen() {
            return school_jifen;
        }

        public void setSchool_jifen(String school_jifen) {
            this.school_jifen = school_jifen;
        }

        public String getSchool_logo() {
            return school_logo;
        }

        public void setSchool_logo(String school_logo) {
            this.school_logo = school_logo;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
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

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public static class UserInfoBean {
            /**
             * data_integrity : 50
             * glmoney : 9876543210
             * head_photo : bicths/d2da9c8b335a4bcfba87e29052e288ad/1512813734497.jpg
             * pingjia : 0
             * signed_num : 1
             * totle_sign : 3
             * user_area : 地区
             * user_code : 000000
             * user_create_time : 2017-12-09 11:54:28
             * user_dengji : 0
             * user_gold : 30
             * user_id : d2da9c8b335a4bcfba87e29052e288ad
             * user_integral : 30
             * user_name : 用户昵称
             * user_password : 0
             * user_phone : 18333616097
             * user_renqi : 0
             * user_sex : 男
             * user_state : 0
             * user_type : 0
             */

            private String data_integrity;
            private String glmoney;
            private String head_photo;
            private String pingjia;
            private String signed_num;
            private String totle_sign;
            private String user_area;
            private String user_code;
            private String user_create_time;
            private String user_dengji;
            private String user_gold;
            private String user_id;
            private String user_integral;
            private String user_name;
            private String user_password;
            private String user_phone;
            private String user_renqi;
            private String user_sex;
            private String user_state;
            private String user_type;

            public String getData_integrity() {
                return data_integrity;
            }

            public void setData_integrity(String data_integrity) {
                this.data_integrity = data_integrity;
            }

            public String getGlmoney() {
                return glmoney;
            }

            public void setGlmoney(String glmoney) {
                this.glmoney = glmoney;
            }

            public String getHead_photo() {
                return head_photo;
            }

            public void setHead_photo(String head_photo) {
                this.head_photo = head_photo;
            }

            public String getPingjia() {
                return pingjia;
            }

            public void setPingjia(String pingjia) {
                this.pingjia = pingjia;
            }

            public String getSigned_num() {
                return signed_num;
            }

            public void setSigned_num(String signed_num) {
                this.signed_num = signed_num;
            }

            public String getTotle_sign() {
                return totle_sign;
            }

            public void setTotle_sign(String totle_sign) {
                this.totle_sign = totle_sign;
            }

            public String getUser_area() {
                return user_area;
            }

            public void setUser_area(String user_area) {
                this.user_area = user_area;
            }

            public String getUser_code() {
                return user_code;
            }

            public void setUser_code(String user_code) {
                this.user_code = user_code;
            }

            public String getUser_create_time() {
                return user_create_time;
            }

            public void setUser_create_time(String user_create_time) {
                this.user_create_time = user_create_time;
            }

            public String getUser_dengji() {
                return user_dengji;
            }

            public void setUser_dengji(String user_dengji) {
                this.user_dengji = user_dengji;
            }

            public String getUser_gold() {
                return user_gold;
            }

            public void setUser_gold(String user_gold) {
                this.user_gold = user_gold;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getUser_integral() {
                return user_integral;
            }

            public void setUser_integral(String user_integral) {
                this.user_integral = user_integral;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getUser_password() {
                return user_password;
            }

            public void setUser_password(String user_password) {
                this.user_password = user_password;
            }

            public String getUser_phone() {
                return user_phone;
            }

            public void setUser_phone(String user_phone) {
                this.user_phone = user_phone;
            }

            public String getUser_renqi() {
                return user_renqi;
            }

            public void setUser_renqi(String user_renqi) {
                this.user_renqi = user_renqi;
            }

            public String getUser_sex() {
                return user_sex;
            }

            public void setUser_sex(String user_sex) {
                this.user_sex = user_sex;
            }

            public String getUser_state() {
                return user_state;
            }

            public void setUser_state(String user_state) {
                this.user_state = user_state;
            }

            public String getUser_type() {
                return user_type;
            }

            public void setUser_type(String user_type) {
                this.user_type = user_type;
            }
        }
    }
}

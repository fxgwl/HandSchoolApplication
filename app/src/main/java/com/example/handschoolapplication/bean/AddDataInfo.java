package com.example.handschoolapplication.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhw on 2018/6/25.
 */

public class AddDataInfo {


    /**
     * data : {"change_state":"2","data_integrity":"100","glmoney":"0","head_photo":"head/initial.jpg","id_number":"150424199405123014","mechanism_address":"1529898918860","mechanism_city":"天津河东区全境","mechanism_ctime":"25544555","mechanism_name":"刚刚呵呵哈哈哈","mechanism_type":"文体艺术//书画:书法","mid_photo":"bicths/cde10193e5534c4980d030454988261d/1529898918883.jpg,bicths/cde10193e5534c4980d030454988261d/1529898918886.jpg","midphoto_state":"2","pingjia":"0","qualification_prove":"bicths/cde10193e5534c4980d030454988261d/1529898918878.jpg","qualiprove_state":"2","reject_reason":"信息不全","schoolAddresses":[{"c_time":"2018-06-25 11:55:18","mechanism_address":"1529898918860","school_jing":"117.22276055483528","school_wei":"39.11963583376146","sd_city":"天津市河西区曲阜道85号","sd_content":"改革开放","sd_id":195}],"school_id":"1529898918889","signed_num":"0","totle_sign":"0","user_code":"000000","user_create_time":"2018-06-25 11:54:09","user_dengji":"0","user_gold":"0","user_id":"cde10193e5534c4980d030454988261d","user_integral":"0","user_name":"用户721895","user_password":"qqqqqq1","user_phone":"15122132323","user_renqi":"0","user_type":"1"}
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
         * change_state : 2
         * data_integrity : 100
         * glmoney : 0
         * head_photo : head/initial.jpg
         * id_number : 150424199405123014
         * mechanism_address : 1529898918860
         * mechanism_city : 天津河东区全境
         * mechanism_ctime : 25544555
         * mechanism_name : 刚刚呵呵哈哈哈
         * mechanism_type : 文体艺术//书画:书法
         * mid_photo : bicths/cde10193e5534c4980d030454988261d/1529898918883.jpg,bicths/cde10193e5534c4980d030454988261d/1529898918886.jpg
         * midphoto_state : 2
         * pingjia : 0
         * qualification_prove : bicths/cde10193e5534c4980d030454988261d/1529898918878.jpg
         * qualiprove_state : 2
         * reject_reason : 信息不全
         * schoolAddresses : [{"c_time":"2018-06-25 11:55:18","mechanism_address":"1529898918860","school_jing":"117.22276055483528","school_wei":"39.11963583376146","sd_city":"天津市河西区曲阜道85号","sd_content":"改革开放","sd_id":195}]
         * school_id : 1529898918889
         * signed_num : 0
         * totle_sign : 0
         * user_code : 000000
         * user_create_time : 2018-06-25 11:54:09
         * user_dengji : 0
         * user_gold : 0
         * user_id : cde10193e5534c4980d030454988261d
         * user_integral : 0
         * user_name : 用户721895
         * user_password : qqqqqq1
         * user_phone : 15122132323
         * user_renqi : 0
         * user_type : 1
         */

        private String change_state;
        private String data_integrity;
        private String glmoney;
        private String head_photo;
        private String id_number;
        private String mechanism_address;
        private String mechanism_city;
        private String mechanism_ctime;
        private String mechanism_name;
        private String mechanism_type;
        private String mid_photo;
        private String mid_photos;
        private String midphoto_state;
        private String pingjia;
        private String qualification_prove;
        private String qualiprove_state;
        private String reject_reason;
        private String school_id;
        private String signed_num;
        private String totle_sign;
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
        private String user_type;
        private List<SchoolAddressesBean> schoolAddresses;

        public String getChange_state() {
            return change_state == null ? "" : change_state;
        }

        public void setChange_state(String change_state) {
            this.change_state = change_state;
        }

        public String getData_integrity() {
            return data_integrity == null ? "" : data_integrity;
        }

        public void setData_integrity(String data_integrity) {
            this.data_integrity = data_integrity;
        }

        public String getGlmoney() {
            return glmoney == null ? "" : glmoney;
        }

        public void setGlmoney(String glmoney) {
            this.glmoney = glmoney;
        }

        public String getHead_photo() {
            return head_photo == null ? "" : head_photo;
        }

        public void setHead_photo(String head_photo) {
            this.head_photo = head_photo;
        }

        public String getId_number() {
            return id_number == null ? "" : id_number;
        }

        public void setId_number(String id_number) {
            this.id_number = id_number;
        }

        public String getMechanism_address() {
            return mechanism_address == null ? "" : mechanism_address;
        }

        public void setMechanism_address(String mechanism_address) {
            this.mechanism_address = mechanism_address;
        }

        public String getMechanism_city() {
            return mechanism_city == null ? "" : mechanism_city;
        }

        public void setMechanism_city(String mechanism_city) {
            this.mechanism_city = mechanism_city;
        }

        public String getMechanism_ctime() {
            return mechanism_ctime == null ? "" : mechanism_ctime;
        }

        public void setMechanism_ctime(String mechanism_ctime) {
            this.mechanism_ctime = mechanism_ctime;
        }

        public String getMechanism_name() {
            return mechanism_name == null ? "" : mechanism_name;
        }

        public void setMechanism_name(String mechanism_name) {
            this.mechanism_name = mechanism_name;
        }

        public String getMechanism_type() {
            return mechanism_type == null ? "" : mechanism_type;
        }

        public void setMechanism_type(String mechanism_type) {
            this.mechanism_type = mechanism_type;
        }

        public String getMid_photo() {
            return mid_photo == null ? "" : mid_photo;
        }

        public void setMid_photo(String mid_photo) {
            this.mid_photo = mid_photo;
        }

        public String getMid_photos() {
            return mid_photos == null ? "" : mid_photos;
        }

        public void setMid_photos(String mid_photos) {
            this.mid_photos = mid_photos;
        }

        public String getMidphoto_state() {
            return midphoto_state == null ? "" : midphoto_state;
        }

        public void setMidphoto_state(String midphoto_state) {
            this.midphoto_state = midphoto_state;
        }

        public String getPingjia() {
            return pingjia == null ? "" : pingjia;
        }

        public void setPingjia(String pingjia) {
            this.pingjia = pingjia;
        }

        public String getQualification_prove() {
            return qualification_prove == null ? "" : qualification_prove;
        }

        public void setQualification_prove(String qualification_prove) {
            this.qualification_prove = qualification_prove;
        }

        public String getQualiprove_state() {
            return qualiprove_state == null ? "" : qualiprove_state;
        }

        public void setQualiprove_state(String qualiprove_state) {
            this.qualiprove_state = qualiprove_state;
        }

        public String getReject_reason() {
            return reject_reason == null ? "" : reject_reason;
        }

        public void setReject_reason(String reject_reason) {
            this.reject_reason = reject_reason;
        }

        public String getSchool_id() {
            return school_id == null ? "" : school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getSigned_num() {
            return signed_num == null ? "" : signed_num;
        }

        public void setSigned_num(String signed_num) {
            this.signed_num = signed_num;
        }

        public String getTotle_sign() {
            return totle_sign == null ? "" : totle_sign;
        }

        public void setTotle_sign(String totle_sign) {
            this.totle_sign = totle_sign;
        }

        public String getUser_code() {
            return user_code == null ? "" : user_code;
        }

        public void setUser_code(String user_code) {
            this.user_code = user_code;
        }

        public String getUser_create_time() {
            return user_create_time == null ? "" : user_create_time;
        }

        public void setUser_create_time(String user_create_time) {
            this.user_create_time = user_create_time;
        }

        public String getUser_dengji() {
            return user_dengji == null ? "" : user_dengji;
        }

        public void setUser_dengji(String user_dengji) {
            this.user_dengji = user_dengji;
        }

        public String getUser_gold() {
            return user_gold == null ? "" : user_gold;
        }

        public void setUser_gold(String user_gold) {
            this.user_gold = user_gold;
        }

        public String getUser_id() {
            return user_id == null ? "" : user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_integral() {
            return user_integral == null ? "" : user_integral;
        }

        public void setUser_integral(String user_integral) {
            this.user_integral = user_integral;
        }

        public String getUser_name() {
            return user_name == null ? "" : user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_password() {
            return user_password == null ? "" : user_password;
        }

        public void setUser_password(String user_password) {
            this.user_password = user_password;
        }

        public String getUser_phone() {
            return user_phone == null ? "" : user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUser_renqi() {
            return user_renqi == null ? "" : user_renqi;
        }

        public void setUser_renqi(String user_renqi) {
            this.user_renqi = user_renqi;
        }

        public String getUser_type() {
            return user_type == null ? "" : user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public List<SchoolAddressesBean> getSchoolAddresses() {
            if (schoolAddresses == null) {
                return new ArrayList<>();
            }
            return schoolAddresses;
        }

        public void setSchoolAddresses(List<SchoolAddressesBean> schoolAddresses) {
            this.schoolAddresses = schoolAddresses;
        }

        public static class SchoolAddressesBean {
            /**
             * c_time : 2018-06-25 11:55:18
             * mechanism_address : 1529898918860
             * school_jing : 117.22276055483528
             * school_wei : 39.11963583376146
             * sd_city : 天津市河西区曲阜道85号
             * sd_content : 改革开放
             * sd_id : 195
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

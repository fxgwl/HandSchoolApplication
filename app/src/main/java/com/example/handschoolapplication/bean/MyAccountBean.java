package com.example.handschoolapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */

public class MyAccountBean implements Serializable{


    /**
     * data : [{"courseInfo":{"age_range":"50-48","collect_no":0,"course_address":"北京市 北京市 西城区东长安街天安门","course_capacity":"500","course_id":"1541052241829","course_info":"<p>马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘<\/p>","course_money":"1200元/9节","course_name":"马飘飘","course_photo":"2018/11/01/1541052241806.png,2018/11/01/1541052241809.png","course_state":"0","course_teacher":"马飘飘","course_time":"2018-11-01 14:04:01","course_type":"早教","dengji":"5","enrol_num":"2","hot_state":"0","hot_time":"","is_coups":"1","is_golds":"1","original_price":"500","picture_one":"2018/11/01/1541052241806.png","picture_two":"2018/11/01/1541052241809.png","popularity_num":"15","preferential_price":"500","school_id":"1540955367782","school_jing":"116.403976","school_name":"1111","school_wei":"39.914699","study_num":"1541052241829","user_id":"b7b06f0800634aefbf5e06066962a879"},"course_name":"马飘飘","money_info":"+1200.0","orderInfo":{"alipay_num":"123","class_money":"1200元/9节","class_name":"马飘飘","class_people":"123","class_photo":"2018/11/01/1541052241806.png,2018/11/01/1541052241809.png","class_size":"2018-11-01 15:07:40","class_teacher":"1541052241829","class_time":"2018-11-01 14:55:25","course_id":"1541052241829","course_num":"1","hongdian":"0","is_gold":"0","order_course_time":"2018-11-01,2018-11-2113:00-14:30\n2018-11-01,2018-11-1214:30-15:00","order_id":"1541055307951","order_money":"1200.000000","order_state":"2","ordre_time":"2018-11-01 14:55:07","pay_num":"15122947301","pay_type":"0","read_state":"0","refund_amount":1188,"school_id":"1540955367782","school_logo":"bicths/b7b06f0800634aefbf5e06066962a879/1541052738445.jpg","school_name":"1111","student_name":"123","student_sex":"女","user_id":"80fb31e22bb5480497686983380106ee","user_phone":"15122947301"},"order_id":"1541055307951","record_date":"2018-11-07","record_id":5,"record_time":"11:35:54","record_type":"0","userInfo":{"change_state":"1","data_integrity":"100","glmoney":"0","head_photo":"bicths/b7b06f0800634aefbf5e06066962a879/1541052738445.jpg","id_number":"130524199205133025","mechanism_address":"1540955367752","mechanism_city":"北京市 北京市 西城区","mechanism_ctime":"111","mechanism_name":"1111","mechanism_type":"早教;托教","member_name":"用户_1540955367","mid_photo":"bicths/b7b06f0800634aefbf5e06066962a879/1540955367774.jpg,bicths/b7b06f0800634aefbf5e06066962a879/1540955367779.jpg","midphoto_state":"1","pingjia":"0","qualification_prove":"bicths/b7b06f0800634aefbf5e06066962a879/1540955367769.jpg","qualiprove_state":"1","school_id":"1540955367782","signed_num":"0","totle_sign":"0","user_code":"000000","user_create_time":"2018-10-31 11:08:02","user_dengji":"0","user_gold":"0","user_id":"b7b06f0800634aefbf5e06066962a879","user_integral":"0","user_name":"tel_1540955367","user_password":"qqqqqq1","user_phone":"15122947309","user_renqi":"2","user_type":"1","whether":"yes"},"user_id":"b7b06f0800634aefbf5e06066962a879","user_name":"1111","user_photo":"bicths/b7b06f0800634aefbf5e06066962a879/1541052738445.jpg"}]
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

    public static class DataBean implements Serializable {
        /**
         * courseInfo : {"age_range":"50-48","collect_no":0,"course_address":"北京市 北京市 西城区东长安街天安门","course_capacity":"500","course_id":"1541052241829","course_info":"<p>马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘<\/p>","course_money":"1200元/9节","course_name":"马飘飘","course_photo":"2018/11/01/1541052241806.png,2018/11/01/1541052241809.png","course_state":"0","course_teacher":"马飘飘","course_time":"2018-11-01 14:04:01","course_type":"早教","dengji":"5","enrol_num":"2","hot_state":"0","hot_time":"","is_coups":"1","is_golds":"1","original_price":"500","picture_one":"2018/11/01/1541052241806.png","picture_two":"2018/11/01/1541052241809.png","popularity_num":"15","preferential_price":"500","school_id":"1540955367782","school_jing":"116.403976","school_name":"1111","school_wei":"39.914699","study_num":"1541052241829","user_id":"b7b06f0800634aefbf5e06066962a879"}
         * course_name : 马飘飘
         * money_info : +1200.0
         * orderInfo : {"alipay_num":"123","class_money":"1200元/9节","class_name":"马飘飘","class_people":"123","class_photo":"2018/11/01/1541052241806.png,2018/11/01/1541052241809.png","class_size":"2018-11-01 15:07:40","class_teacher":"1541052241829","class_time":"2018-11-01 14:55:25","course_id":"1541052241829","course_num":"1","hongdian":"0","is_gold":"0","order_course_time":"2018-11-01,2018-11-2113:00-14:30\n2018-11-01,2018-11-1214:30-15:00","order_id":"1541055307951","order_money":"1200.000000","order_state":"2","ordre_time":"2018-11-01 14:55:07","pay_num":"15122947301","pay_type":"0","read_state":"0","refund_amount":1188,"school_id":"1540955367782","school_logo":"bicths/b7b06f0800634aefbf5e06066962a879/1541052738445.jpg","school_name":"1111","student_name":"123","student_sex":"女","user_id":"80fb31e22bb5480497686983380106ee","user_phone":"15122947301"}
         * order_id : 1541055307951
         * record_date : 2018-11-07
         * record_id : 5
         * record_time : 11:35:54
         * record_type : 0
         * userInfo : {"change_state":"1","data_integrity":"100","glmoney":"0","head_photo":"bicths/b7b06f0800634aefbf5e06066962a879/1541052738445.jpg","id_number":"130524199205133025","mechanism_address":"1540955367752","mechanism_city":"北京市 北京市 西城区","mechanism_ctime":"111","mechanism_name":"1111","mechanism_type":"早教;托教","member_name":"用户_1540955367","mid_photo":"bicths/b7b06f0800634aefbf5e06066962a879/1540955367774.jpg,bicths/b7b06f0800634aefbf5e06066962a879/1540955367779.jpg","midphoto_state":"1","pingjia":"0","qualification_prove":"bicths/b7b06f0800634aefbf5e06066962a879/1540955367769.jpg","qualiprove_state":"1","school_id":"1540955367782","signed_num":"0","totle_sign":"0","user_code":"000000","user_create_time":"2018-10-31 11:08:02","user_dengji":"0","user_gold":"0","user_id":"b7b06f0800634aefbf5e06066962a879","user_integral":"0","user_name":"tel_1540955367","user_password":"qqqqqq1","user_phone":"15122947309","user_renqi":"2","user_type":"1","whether":"yes"}
         * user_id : b7b06f0800634aefbf5e06066962a879
         * user_name : 1111
         * user_photo : bicths/b7b06f0800634aefbf5e06066962a879/1541052738445.jpg
         */

        private CourseInfoBean courseInfo;
        private String course_name;
        private String money_info;
        private OrderInfoBean orderInfo;
        private String order_id;
        private String record_date;
        private int record_id;
        private String record_time;
        private String record_type;
        private UserInfoBean userInfo;
        private String user_id;
        private String user_name;
        private String user_photo;

        public CourseInfoBean getCourseInfo() {
            return courseInfo;
        }

        public void setCourseInfo(CourseInfoBean courseInfo) {
            this.courseInfo = courseInfo;
        }

        public String getCourse_name() {
            return course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }

        public String getMoney_info() {
            return money_info;
        }

        public void setMoney_info(String money_info) {
            this.money_info = money_info;
        }

        public OrderInfoBean getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfoBean orderInfo) {
            this.orderInfo = orderInfo;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getRecord_date() {
            return record_date;
        }

        public void setRecord_date(String record_date) {
            this.record_date = record_date;
        }

        public int getRecord_id() {
            return record_id;
        }

        public void setRecord_id(int record_id) {
            this.record_id = record_id;
        }

        public String getRecord_time() {
            return record_time;
        }

        public void setRecord_time(String record_time) {
            this.record_time = record_time;
        }

        public String getRecord_type() {
            return record_type;
        }

        public void setRecord_type(String record_type) {
            this.record_type = record_type;
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

        public String getUser_photo() {
            return user_photo;
        }

        public void setUser_photo(String user_photo) {
            this.user_photo = user_photo;
        }

        public static class CourseInfoBean implements Serializable{
            /**
             * age_range : 50-48
             * collect_no : 0
             * course_address : 北京市 北京市 西城区东长安街天安门
             * course_capacity : 500
             * course_id : 1541052241829
             * course_info : <p>马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘马飘飘</p>
             * course_money : 1200元/9节
             * course_name : 马飘飘
             * course_photo : 2018/11/01/1541052241806.png,2018/11/01/1541052241809.png
             * course_state : 0
             * course_teacher : 马飘飘
             * course_time : 2018-11-01 14:04:01
             * course_type : 早教
             * dengji : 5
             * enrol_num : 2
             * hot_state : 0
             * hot_time :
             * is_coups : 1
             * is_golds : 1
             * original_price : 500
             * picture_one : 2018/11/01/1541052241806.png
             * picture_two : 2018/11/01/1541052241809.png
             * popularity_num : 15
             * preferential_price : 500
             * school_id : 1540955367782
             * school_jing : 116.403976
             * school_name : 1111
             * school_wei : 39.914699
             * study_num : 1541052241829
             * user_id : b7b06f0800634aefbf5e06066962a879
             */

            private String age_range;
            private int collect_no;
            private String course_address;
            private String course_capacity;
            private String course_id;
            private String course_info;
            private String course_money;
            private String course_name;
            private String course_photo;
            private String course_state;
            private String course_teacher;
            private String course_time;
            private String course_type;
            private String dengji;
            private String enrol_num;
            private String hot_state;
            private String hot_time;
            private String is_coups;
            private String is_golds;
            private String original_price;
            private String picture_one;
            private String picture_two;
            private String popularity_num;
            private String preferential_price;
            private String school_id;
            private String school_jing;
            private String school_name;
            private String school_wei;
            private String study_num;
            private String study_code;
            private String user_id;

            public String getStudy_code() {
                return study_code == null ? "" : study_code;
            }

            public void setStudy_code(String study_code) {
                this.study_code = study_code;
            }

            public String getAge_range() {
                return age_range;
            }

            public void setAge_range(String age_range) {
                this.age_range = age_range;
            }

            public int getCollect_no() {
                return collect_no;
            }

            public void setCollect_no(int collect_no) {
                this.collect_no = collect_no;
            }

            public String getCourse_address() {
                return course_address;
            }

            public void setCourse_address(String course_address) {
                this.course_address = course_address;
            }

            public String getCourse_capacity() {
                return course_capacity;
            }

            public void setCourse_capacity(String course_capacity) {
                this.course_capacity = course_capacity;
            }

            public String getCourse_id() {
                return course_id;
            }

            public void setCourse_id(String course_id) {
                this.course_id = course_id;
            }

            public String getCourse_info() {
                return course_info;
            }

            public void setCourse_info(String course_info) {
                this.course_info = course_info;
            }

            public String getCourse_money() {
                return course_money;
            }

            public void setCourse_money(String course_money) {
                this.course_money = course_money;
            }

            public String getCourse_name() {
                return course_name;
            }

            public void setCourse_name(String course_name) {
                this.course_name = course_name;
            }

            public String getCourse_photo() {
                return course_photo;
            }

            public void setCourse_photo(String course_photo) {
                this.course_photo = course_photo;
            }

            public String getCourse_state() {
                return course_state;
            }

            public void setCourse_state(String course_state) {
                this.course_state = course_state;
            }

            public String getCourse_teacher() {
                return course_teacher;
            }

            public void setCourse_teacher(String course_teacher) {
                this.course_teacher = course_teacher;
            }

            public String getCourse_time() {
                return course_time;
            }

            public void setCourse_time(String course_time) {
                this.course_time = course_time;
            }

            public String getCourse_type() {
                return course_type;
            }

            public void setCourse_type(String course_type) {
                this.course_type = course_type;
            }

            public String getDengji() {
                return dengji;
            }

            public void setDengji(String dengji) {
                this.dengji = dengji;
            }

            public String getEnrol_num() {
                return enrol_num;
            }

            public void setEnrol_num(String enrol_num) {
                this.enrol_num = enrol_num;
            }

            public String getHot_state() {
                return hot_state;
            }

            public void setHot_state(String hot_state) {
                this.hot_state = hot_state;
            }

            public String getHot_time() {
                return hot_time;
            }

            public void setHot_time(String hot_time) {
                this.hot_time = hot_time;
            }

            public String getIs_coups() {
                return is_coups;
            }

            public void setIs_coups(String is_coups) {
                this.is_coups = is_coups;
            }

            public String getIs_golds() {
                return is_golds;
            }

            public void setIs_golds(String is_golds) {
                this.is_golds = is_golds;
            }

            public String getOriginal_price() {
                return original_price;
            }

            public void setOriginal_price(String original_price) {
                this.original_price = original_price;
            }

            public String getPicture_one() {
                return picture_one;
            }

            public void setPicture_one(String picture_one) {
                this.picture_one = picture_one;
            }

            public String getPicture_two() {
                return picture_two;
            }

            public void setPicture_two(String picture_two) {
                this.picture_two = picture_two;
            }

            public String getPopularity_num() {
                return popularity_num;
            }

            public void setPopularity_num(String popularity_num) {
                this.popularity_num = popularity_num;
            }

            public String getPreferential_price() {
                return preferential_price;
            }

            public void setPreferential_price(String preferential_price) {
                this.preferential_price = preferential_price;
            }

            public String getSchool_id() {
                return school_id;
            }

            public void setSchool_id(String school_id) {
                this.school_id = school_id;
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

            public String getSchool_wei() {
                return school_wei;
            }

            public void setSchool_wei(String school_wei) {
                this.school_wei = school_wei;
            }

            public String getStudy_num() {
                return study_num;
            }

            public void setStudy_num(String study_num) {
                this.study_num = study_num;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }
        }

        public static class OrderInfoBean implements Serializable{
            /**
             * alipay_num : 123
             * class_money : 1200元/9节
             * class_name : 马飘飘
             * class_people : 123
             * class_photo : 2018/11/01/1541052241806.png,2018/11/01/1541052241809.png
             * class_size : 2018-11-01 15:07:40
             * class_teacher : 1541052241829
             * class_time : 2018-11-01 14:55:25
             * course_id : 1541052241829
             * course_num : 1
             * hongdian : 0
             * is_gold : 0
             * order_course_time : 2018-11-01,2018-11-2113:00-14:30
             2018-11-01,2018-11-1214:30-15:00
             * order_id : 1541055307951
             * order_money : 1200.000000
             * order_state : 2
             * ordre_time : 2018-11-01 14:55:07
             * pay_num : 15122947301
             * pay_type : 0
             * read_state : 0
             * refund_amount : 1188
             * school_id : 1540955367782
             * school_logo : bicths/b7b06f0800634aefbf5e06066962a879/1541052738445.jpg
             * school_name : 1111
             * student_name : 123
             * student_sex : 女
             * user_id : 80fb31e22bb5480497686983380106ee
             * user_phone : 15122947301
             */

            private String alipay_num;
            private String class_money;
            private String class_name;
            private String class_people;
            private String class_photo;
            private String class_size;
            private String class_teacher;
            private String class_time;
            private String course_id;
            private String course_num;
            private String hongdian;
            private String is_gold;
            private String order_course_time;
            private String order_id;
            private String order_money;
            private String order_state;
            private String ordre_time;
            private String pay_num;
            private String pay_type;
            private String read_state;
            private int refund_amount;
            private String school_id;
            private String school_logo;
            private String school_name;
            private String student_name;
            private String student_sex;
            private String user_id;
            private String user_phone;

            public String getAlipay_num() {
                return alipay_num;
            }

            public void setAlipay_num(String alipay_num) {
                this.alipay_num = alipay_num;
            }

            public String getClass_money() {
                return class_money;
            }

            public void setClass_money(String class_money) {
                this.class_money = class_money;
            }

            public String getClass_name() {
                return class_name;
            }

            public void setClass_name(String class_name) {
                this.class_name = class_name;
            }

            public String getClass_people() {
                return class_people;
            }

            public void setClass_people(String class_people) {
                this.class_people = class_people;
            }

            public String getClass_photo() {
                return class_photo;
            }

            public void setClass_photo(String class_photo) {
                this.class_photo = class_photo;
            }

            public String getClass_size() {
                return class_size;
            }

            public void setClass_size(String class_size) {
                this.class_size = class_size;
            }

            public String getClass_teacher() {
                return class_teacher;
            }

            public void setClass_teacher(String class_teacher) {
                this.class_teacher = class_teacher;
            }

            public String getClass_time() {
                return class_time;
            }

            public void setClass_time(String class_time) {
                this.class_time = class_time;
            }

            public String getCourse_id() {
                return course_id;
            }

            public void setCourse_id(String course_id) {
                this.course_id = course_id;
            }

            public String getCourse_num() {
                return course_num;
            }

            public void setCourse_num(String course_num) {
                this.course_num = course_num;
            }

            public String getHongdian() {
                return hongdian;
            }

            public void setHongdian(String hongdian) {
                this.hongdian = hongdian;
            }

            public String getIs_gold() {
                return is_gold;
            }

            public void setIs_gold(String is_gold) {
                this.is_gold = is_gold;
            }

            public String getOrder_course_time() {
                return order_course_time;
            }

            public void setOrder_course_time(String order_course_time) {
                this.order_course_time = order_course_time;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getOrder_money() {
                return order_money;
            }

            public void setOrder_money(String order_money) {
                this.order_money = order_money;
            }

            public String getOrder_state() {
                return order_state;
            }

            public void setOrder_state(String order_state) {
                this.order_state = order_state;
            }

            public String getOrdre_time() {
                return ordre_time;
            }

            public void setOrdre_time(String ordre_time) {
                this.ordre_time = ordre_time;
            }

            public String getPay_num() {
                return pay_num;
            }

            public void setPay_num(String pay_num) {
                this.pay_num = pay_num;
            }

            public String getPay_type() {
                return pay_type;
            }

            public void setPay_type(String pay_type) {
                this.pay_type = pay_type;
            }

            public String getRead_state() {
                return read_state;
            }

            public void setRead_state(String read_state) {
                this.read_state = read_state;
            }

            public int getRefund_amount() {
                return refund_amount;
            }

            public void setRefund_amount(int refund_amount) {
                this.refund_amount = refund_amount;
            }

            public String getSchool_id() {
                return school_id;
            }

            public void setSchool_id(String school_id) {
                this.school_id = school_id;
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

            public String getStudent_name() {
                return student_name;
            }

            public void setStudent_name(String student_name) {
                this.student_name = student_name;
            }

            public String getStudent_sex() {
                return student_sex;
            }

            public void setStudent_sex(String student_sex) {
                this.student_sex = student_sex;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getUser_phone() {
                return user_phone;
            }

            public void setUser_phone(String user_phone) {
                this.user_phone = user_phone;
            }
        }

        public static class UserInfoBean implements Serializable{
            /**
             * change_state : 1
             * data_integrity : 100
             * glmoney : 0
             * head_photo : bicths/b7b06f0800634aefbf5e06066962a879/1541052738445.jpg
             * id_number : 130524199205133025
             * mechanism_address : 1540955367752
             * mechanism_city : 北京市 北京市 西城区
             * mechanism_ctime : 111
             * mechanism_name : 1111
             * mechanism_type : 早教;托教
             * member_name : 用户_1540955367
             * mid_photo : bicths/b7b06f0800634aefbf5e06066962a879/1540955367774.jpg,bicths/b7b06f0800634aefbf5e06066962a879/1540955367779.jpg
             * midphoto_state : 1
             * pingjia : 0
             * qualification_prove : bicths/b7b06f0800634aefbf5e06066962a879/1540955367769.jpg
             * qualiprove_state : 1
             * school_id : 1540955367782
             * signed_num : 0
             * totle_sign : 0
             * user_code : 000000
             * user_create_time : 2018-10-31 11:08:02
             * user_dengji : 0
             * user_gold : 0
             * user_id : b7b06f0800634aefbf5e06066962a879
             * user_integral : 0
             * user_name : tel_1540955367
             * user_password : qqqqqq1
             * user_phone : 15122947309
             * user_renqi : 2
             * user_type : 1
             * whether : yes
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
            private String member_name;
            private String mid_photo;
            private String midphoto_state;
            private String pingjia;
            private String qualification_prove;
            private String qualiprove_state;
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
            private String whether;

            public String getChange_state() {
                return change_state;
            }

            public void setChange_state(String change_state) {
                this.change_state = change_state;
            }

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

            public String getId_number() {
                return id_number;
            }

            public void setId_number(String id_number) {
                this.id_number = id_number;
            }

            public String getMechanism_address() {
                return mechanism_address;
            }

            public void setMechanism_address(String mechanism_address) {
                this.mechanism_address = mechanism_address;
            }

            public String getMechanism_city() {
                return mechanism_city;
            }

            public void setMechanism_city(String mechanism_city) {
                this.mechanism_city = mechanism_city;
            }

            public String getMechanism_ctime() {
                return mechanism_ctime;
            }

            public void setMechanism_ctime(String mechanism_ctime) {
                this.mechanism_ctime = mechanism_ctime;
            }

            public String getMechanism_name() {
                return mechanism_name;
            }

            public void setMechanism_name(String mechanism_name) {
                this.mechanism_name = mechanism_name;
            }

            public String getMechanism_type() {
                return mechanism_type;
            }

            public void setMechanism_type(String mechanism_type) {
                this.mechanism_type = mechanism_type;
            }

            public String getMember_name() {
                return member_name;
            }

            public void setMember_name(String member_name) {
                this.member_name = member_name;
            }

            public String getMid_photo() {
                return mid_photo;
            }

            public void setMid_photo(String mid_photo) {
                this.mid_photo = mid_photo;
            }

            public String getMidphoto_state() {
                return midphoto_state;
            }

            public void setMidphoto_state(String midphoto_state) {
                this.midphoto_state = midphoto_state;
            }

            public String getPingjia() {
                return pingjia;
            }

            public void setPingjia(String pingjia) {
                this.pingjia = pingjia;
            }

            public String getQualification_prove() {
                return qualification_prove;
            }

            public void setQualification_prove(String qualification_prove) {
                this.qualification_prove = qualification_prove;
            }

            public String getQualiprove_state() {
                return qualiprove_state;
            }

            public void setQualiprove_state(String qualiprove_state) {
                this.qualiprove_state = qualiprove_state;
            }

            public String getSchool_id() {
                return school_id;
            }

            public void setSchool_id(String school_id) {
                this.school_id = school_id;
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

            public String getUser_type() {
                return user_type;
            }

            public void setUser_type(String user_type) {
                this.user_type = user_type;
            }

            public String getWhether() {
                return whether;
            }

            public void setWhether(String whether) {
                this.whether = whether;
            }
        }
    }
}

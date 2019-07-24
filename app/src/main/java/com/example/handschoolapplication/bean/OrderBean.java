package com.example.handschoolapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */

public class OrderBean implements Serializable{
    /**
     * data : [{"class_money":"800元/8节","class_name":"音乐","class_photo":"2017/11/10/1510302644926.jpg,2017/11/10/1510302644931.jpg,2017/11/10/1510302644935.jpg,2017/11/10/1510302644950.jpg,2017/11/10/1510302644953.jpg","class_teacher":"1506153404911","courseInfo":{"age_range":"2-6岁","course_address":"天津市北京市","course_capacity":"28","course_id":"1506153404911","course_info":"<p>bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg<\/p>","course_money":"800元/4节","course_name":"音乐","course_photo":"2017/11/10/1510302644926.jpg,2017/11/10/1510302644931.jpg,2017/11/10/1510302644935.jpg,2017/11/10/1510302644950.jpg,2017/11/10/1510302644953.jpg","course_state":"1","course_teacher":"李小明","course_time":"2017-11-10 16:47:55","course_type":"文体艺术/书画,学习辅导/中小学教育","dengji":"5","enrol_num":"60","hot_time":"2017-09-20 14:15:50","original_price":"660","popularity_num":"719","preferential_price":"330","school_id":"1505805630236","school_jing":"119.40403592998626","school_name":"我的小学堂a","school_wei":"39.91386028193478","study_num":"1506153404911","user_id":"8841f54f7b574f06a470ee9002043f8d"},"courseTimeInfo":[{"course_id":"1506153404911","create_time":"2017-10-20 17:30:05","ctime_id":7,"ctime_times":"8:30-10:00,10:30-12:00,13:30-15:00,15:30-17:00","ctime_week":"周一"},{"course_id":"1506153404911","create_time":"2017-10-20 17:30:05","ctime_id":8,"ctime_times":"8:30-10:00,10:30-12:00,13:30-15:00,15:30-17:00","ctime_week":"周二"}],"course_id":"1506153404911","course_num":"1","order_course_time":"周一8:30-10:00,10:30-12:00,13:30-15:00,15:30-17:00\n周二8:30-10:00,10:30-12:00,13:30-15:00,15:30-17:00","order_id":"1510972612227","order_money":"800","order_state":"0","ordre_time":"2017-11-18 10:36:52","school_id":"1505805630236","school_logo":"bicths/8841f54f7b574f06a470ee9002043f8d/1510304373559.jpg","school_name":"我的小学堂a","user_id":"e0abbd527e6845fbb8ea80c841e0ca96","user_phone":"15773263911"}]
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
         * class_money : 800元/8节
         * class_name : 音乐
         * class_photo : 2017/11/10/1510302644926.jpg,2017/11/10/1510302644931.jpg,2017/11/10/1510302644935.jpg,2017/11/10/1510302644950.jpg,2017/11/10/1510302644953.jpg
         * class_teacher : 1506153404911
         * courseInfo : {"age_range":"2-6岁","course_address":"天津市北京市","course_capacity":"28","course_id":"1506153404911","course_info":"<p>bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg<\/p>","course_money":"800元/4节","course_name":"音乐","course_photo":"2017/11/10/1510302644926.jpg,2017/11/10/1510302644931.jpg,2017/11/10/1510302644935.jpg,2017/11/10/1510302644950.jpg,2017/11/10/1510302644953.jpg","course_state":"1","course_teacher":"李小明","course_time":"2017-11-10 16:47:55","course_type":"文体艺术/书画,学习辅导/中小学教育","dengji":"5","enrol_num":"60","hot_time":"2017-09-20 14:15:50","original_price":"660","popularity_num":"719","preferential_price":"330","school_id":"1505805630236","school_jing":"119.40403592998626","school_name":"我的小学堂a","school_wei":"39.91386028193478","study_num":"1506153404911","user_id":"8841f54f7b574f06a470ee9002043f8d"}
         * courseTimeInfo : [{"course_id":"1506153404911","create_time":"2017-10-20 17:30:05","ctime_id":7,"ctime_times":"8:30-10:00,10:30-12:00,13:30-15:00,15:30-17:00","ctime_week":"周一"},{"course_id":"1506153404911","create_time":"2017-10-20 17:30:05","ctime_id":8,"ctime_times":"8:30-10:00,10:30-12:00,13:30-15:00,15:30-17:00","ctime_week":"周二"}]
         * course_id : 1506153404911
         * course_num : 1
         * order_course_time : 周一8:30-10:00,10:30-12:00,13:30-15:00,15:30-17:00
         周二8:30-10:00,10:30-12:00,13:30-15:00,15:30-17:00
         * order_id : 1510972612227
         * order_money : 800
         * order_state : 0
         * ordre_time : 2017-11-18 10:36:52
         * school_id : 1505805630236
         * school_logo : bicths/8841f54f7b574f06a470ee9002043f8d/1510304373559.jpg
         * school_name : 我的小学堂a
         * user_id : e0abbd527e6845fbb8ea80c841e0ca96
         * student_name:33333
         * user_phone : 15773263911
         */

        private String class_money;
        private String class_name;
        private String class_photo;
        private String class_teacher;
        private CourseInfoBean courseInfo;
        private String course_id;
        private String course_num;
        private String order_course_time;
        private String order_id;
        private String order_money;
        private String order_state;
        private String ordre_time;
        private String pay_type;
        private String school_id;
        private String school_logo;
        private String school_name;
        private String user_id;
        private String user_phone;
        private String student_name;
        private String student_sex;
        private String is_golds;
        private List<CourseTimeInfoBean> courseTimeInfo;

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

        public String getClass_photo() {
            return class_photo;
        }

        public void setClass_photo(String class_photo) {
            this.class_photo = class_photo;
        }

        public String getClass_teacher() {
            return class_teacher;
        }

        public void setClass_teacher(String class_teacher) {
            this.class_teacher = class_teacher;
        }

        public CourseInfoBean getCourseInfo() {
            return courseInfo;
        }

        public void setCourseInfo(CourseInfoBean courseInfo) {
            this.courseInfo = courseInfo;
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

        public String getPay_type() {
            return pay_type == null ? "" : pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
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

        public List<CourseTimeInfoBean> getCourseTimeInfo() {
            return courseTimeInfo;
        }

        public void setCourseTimeInfo(List<CourseTimeInfoBean> courseTimeInfo) {
            this.courseTimeInfo = courseTimeInfo;
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

        public String getIs_golds() {
            return is_golds;
        }

        public void setIs_golds(String is_golds) {
            this.is_golds = is_golds;
        }

        public static class CourseInfoBean {
            /**
             * age_range : 2-6岁
             * course_address : 天津市北京市
             * course_capacity : 28
             * course_id : 1506153404911
             * course_info : <p>bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg</p>
             * course_money : 800元/4节
             * course_name : 音乐
             * course_photo : 2017/11/10/1510302644926.jpg,2017/11/10/1510302644931.jpg,2017/11/10/1510302644935.jpg,2017/11/10/1510302644950.jpg,2017/11/10/1510302644953.jpg
             * course_state : 1
             * course_teacher : 李小明
             * course_time : 2017-11-10 16:47:55
             * course_type : 文体艺术/书画,学习辅导/中小学教育
             * dengji : 5
             * enrol_num : 60
             * hot_time : 2017-09-20 14:15:50
             * original_price : 660
             * popularity_num : 719
             * preferential_price : 330
             * school_id : 1505805630236
             * school_jing : 119.40403592998626
             * school_name : 我的小学堂a
             * school_wei : 39.91386028193478
             * study_num : 1506153404911
             * user_id : 8841f54f7b574f06a470ee9002043f8d
             */

            private String age_range;
            private String course_address;
            private int course_capacity;
            private String course_id;
            private String course_info;
            private String course_money;
            private String course_name;
            private String course_photo;
            private String picture_one;
            private String picture_two;
            private String picture_three;
            private String picture_four;
            private String picture_five;
            private String course_state;
            private String course_teacher;
            private String course_time;
            private String course_type;
            private String dengji;
            private int enrol_num;
            private String hot_time;
            private String original_price;
            private String popularity_num;
            private String preferential_price;
            private String school_id;
            private String school_jing;
            private String school_name;
            private String school_wei;
            private String study_num;
            private String study_code;
            private String user_id;
            private String is_golds;
            private String is_coups;

            public String getStudy_code() {
                return study_code == null ? "" : study_code;
            }

            public void setStudy_code(String study_code) {
                this.study_code = study_code;
            }

            public String getIs_golds() {
                return is_golds;
            }

            public void setIs_golds(String is_golds) {
                this.is_golds = is_golds;
            }

            public String getIs_coups() {
                return is_coups == null ? "" : is_coups;
            }

            public void setIs_coups(String is_coups) {
                this.is_coups = is_coups;
            }

            public String getAge_range() {
                return age_range;
            }

            public void setAge_range(String age_range) {
                this.age_range = age_range;
            }

            public String getCourse_address() {
                return course_address;
            }

            public void setCourse_address(String course_address) {
                this.course_address = course_address;
            }

            public int getCourse_capacity() {
                return course_capacity;
            }

            public void setCourse_capacity(int course_capacity) {
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

            public String getPicture_one() {
                return picture_one == null ? "" : picture_one;
            }

            public void setPicture_one(String picture_one) {
                this.picture_one = picture_one;
            }

            public String getPicture_two() {
                return picture_two == null ? "" : picture_two;
            }

            public void setPicture_two(String picture_two) {
                this.picture_two = picture_two;
            }

            public String getPicture_three() {
                return picture_three == null ? "" : picture_three;
            }

            public void setPicture_three(String picture_three) {
                this.picture_three = picture_three;
            }

            public String getPicture_four() {
                return picture_four == null ? "" : picture_four;
            }

            public void setPicture_four(String picture_four) {
                this.picture_four = picture_four;
            }

            public String getPicture_five() {
                return picture_five == null ? "" : picture_five;
            }

            public void setPicture_five(String picture_five) {
                this.picture_five = picture_five;
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

            public int getEnrol_num() {
                return enrol_num;
            }

            public void setEnrol_num(int enrol_num) {
                this.enrol_num = enrol_num;
            }

            public String getHot_time() {
                return hot_time;
            }

            public void setHot_time(String hot_time) {
                this.hot_time = hot_time;
            }

            public String getOriginal_price() {
                return original_price;
            }

            public void setOriginal_price(String original_price) {
                this.original_price = original_price;
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

        public static class CourseTimeInfoBean {
            /**
             * course_id : 1506153404911
             * create_time : 2017-10-20 17:30:05
             * ctime_id : 7
             * ctime_times : 8:30-10:00,10:30-12:00,13:30-15:00,15:30-17:00
             * ctime_week : 周一
             */

            private String course_id;
            private String create_time;
            private int ctime_id;
            private String ctime_times;
            private String ctime_week;

            public String getCourse_id() {
                return course_id;
            }

            public void setCourse_id(String course_id) {
                this.course_id = course_id;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getCtime_id() {
                return ctime_id;
            }

            public void setCtime_id(int ctime_id) {
                this.ctime_id = ctime_id;
            }

            public String getCtime_times() {
                return ctime_times;
            }

            public void setCtime_times(String ctime_times) {
                this.ctime_times = ctime_times;
            }

            public String getCtime_week() {
                return ctime_week;
            }

            public void setCtime_week(String ctime_week) {
                this.ctime_week = ctime_week;
            }
        }
    }

//    private List<DataBean> data;
//
//    public List<DataBean> getData() {
//        return data;
//    }
//
//    public void setData(List<DataBean> data) {
//        this.data = data;
//    }
//
//    public static class DataBean implements Serializable{
//        /**
//         * class_money :
//         * class_name : 音乐
//         * class_people :
//         * class_photo : bicths/1584b04562a84667bd9068d6d2dd09c0/1505809601513.jpg
//         * class_size :
//         * class_teacher : 1506153404911
//         * class_time :
//         * course_id : 1506153404911
//         * course_num : 2
//         * is_coupons : 0
//         * is_gold : 0
//         * order_id : 1507541019423
//         * order_money : 330
//         * order_state : 0
//         * order_type :
//         * ordre_time : 2017-10-09 17:23:39
//         * pay_num :
//         * pay_type :
//         * school_id : 1505805630236
//         * school_logo : bicths/4c975ad7eeb646f7b0e06cabc8290ddd/1506565555092.jpg
//         * school_name : 我的小学堂a
//         * user_id : 772d42a5a0014faa8706f7cd3f6e8828
//         * user_name : chuan
//         * user_phone : 13930989708
//         */
//
//        private String class_money;
//        private String class_name;
//        private String class_people;
//        private String class_photo;
//        private String class_size;
//        private String class_teacher;
//        private String class_time;
//        private String course_id;
//        private String course_num;
//        private int is_coupons;
//        private String is_gold;
//        private String order_id;
//        private String order_money;
//        private String order_state;
//        private String order_type;
//        private String ordre_time;
//        private String pay_num;
//        private String pay_type;
//        private String school_id;
//        private String school_logo;
//        private String school_name;
//        private String user_id;
//        private String user_name;
//        private String user_phone;
//
//        public String getClass_money() {
//            return class_money;
//        }
//
//        public void setClass_money(String class_money) {
//            this.class_money = class_money;
//        }
//
//        public String getClass_name() {
//            return class_name;
//        }
//
//        public void setClass_name(String class_name) {
//            this.class_name = class_name;
//        }
//
//        public String getClass_people() {
//            return class_people;
//        }
//
//        public void setClass_people(String class_people) {
//            this.class_people = class_people;
//        }
//
//        public String getClass_photo() {
//            return class_photo;
//        }
//
//        public void setClass_photo(String class_photo) {
//            this.class_photo = class_photo;
//        }
//
//        public String getClass_size() {
//            return class_size;
//        }
//
//        public void setClass_size(String class_size) {
//            this.class_size = class_size;
//        }
//
//        public String getClass_teacher() {
//            return class_teacher;
//        }
//
//        public void setClass_teacher(String class_teacher) {
//            this.class_teacher = class_teacher;
//        }
//
//        public String getClass_time() {
//            return class_time;
//        }
//
//        public void setClass_time(String class_time) {
//            this.class_time = class_time;
//        }
//
//        public String getCourse_id() {
//            return course_id;
//        }
//
//        public void setCourse_id(String course_id) {
//            this.course_id = course_id;
//        }
//
//        public String getCourse_num() {
//            return course_num;
//        }
//
//        public void setCourse_num(String course_num) {
//            this.course_num = course_num;
//        }
//
//        public int getIs_coupons() {
//            return is_coupons;
//        }
//
//        public void setIs_coupons(int is_coupons) {
//            this.is_coupons = is_coupons;
//        }
//
//        public String getIs_gold() {
//            return is_gold;
//        }
//
//        public void setIs_gold(String is_gold) {
//            this.is_gold = is_gold;
//        }
//
//        public String getOrder_id() {
//            return order_id;
//        }
//
//        public void setOrder_id(String order_id) {
//            this.order_id = order_id;
//        }
//
//        public String getOrder_money() {
//            return order_money;
//        }
//
//        public void setOrder_money(String order_money) {
//            this.order_money = order_money;
//        }
//
//        public String getOrder_state() {
//            return order_state;
//        }
//
//        public void setOrder_state(String order_state) {
//            this.order_state = order_state;
//        }
//
//        public String getOrder_type() {
//            return order_type;
//        }
//
//        public void setOrder_type(String order_type) {
//            this.order_type = order_type;
//        }
//
//        public String getOrdre_time() {
//            return ordre_time;
//        }
//
//        public void setOrdre_time(String ordre_time) {
//            this.ordre_time = ordre_time;
//        }
//
//        public String getPay_num() {
//            return pay_num;
//        }
//
//        public void setPay_num(String pay_num) {
//            this.pay_num = pay_num;
//        }
//
//        public String getPay_type() {
//            return pay_type;
//        }
//
//        public void setPay_type(String pay_type) {
//            this.pay_type = pay_type;
//        }
//
//        public String getSchool_id() {
//            return school_id;
//        }
//
//        public void setSchool_id(String school_id) {
//            this.school_id = school_id;
//        }
//
//        public String getSchool_logo() {
//            return school_logo;
//        }
//
//        public void setSchool_logo(String school_logo) {
//            this.school_logo = school_logo;
//        }
//
//        public String getSchool_name() {
//            return school_name;
//        }
//
//        public void setSchool_name(String school_name) {
//            this.school_name = school_name;
//        }
//
//        public String getUser_id() {
//            return user_id;
//        }
//
//        public void setUser_id(String user_id) {
//            this.user_id = user_id;
//        }
//
//        public String getUser_name() {
//            return user_name;
//        }
//
//        public void setUser_name(String user_name) {
//            this.user_name = user_name;
//        }
//
//        public String getUser_phone() {
//            return user_phone;
//        }
//
//        public void setUser_phone(String user_phone) {
//            this.user_phone = user_phone;
//        }
//    }


}

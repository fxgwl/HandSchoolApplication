package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */

public class LearningCourseBean {


    /**
     * classSign : {"all_class":"7","course_id":"1529564813858","created":"2018-06-22","csign_id":2,"order_id":"1529650802208","study_class":"1","user_head":"head/initial.jpg","user_id":"d56c00390a77475a84f67c68a675c243","user_name":"117.22191725292298"}
     * class_money : 1200元/7节
     * class_name : 马飘飘
     * class_photo : 2018/06/21/1529564813826.png
     * class_teacher : 1529564813858
     * class_time : 2018-06-22 15:00:10
     * courseInfo : {"age_range":"120","course_address":"天津市河西区浦口道70号-增1好好的好的","course_capacity":"10","course_id":"1529564813858","course_info":"","course_money":"1200元/7节","course_name":"马飘飘","course_photo":"2018/06/21/1529564813826.png","course_state":"1","course_teacher":"马飘飘","course_time":"2018-06-22 14:30:41","course_type":"文体艺术//书画:书法,球类:足球/篮球/排球/台球","enrol_num":"12","is_coups":"1","is_golds":"1","original_price":"100","popularity_num":"28","preferential_price":"100","school_id":"1529562005537","school_jing":"117.22191725292298","school_name":"学堂zhw","school_wei":"39.11746826637447","study_num":"1529564813858","user_id":"e2d00b6a83994f83ad6322bd5cd2bdff"}
     * courseTimeInfo : [{"course_id":"1529564813858","create_time":"2018-06-22 14:30:41","ctime_id":97,"ctime_times":"14:00-16:00","ctime_type":"0","ctime_week":"2018-06-21,2018-06-29"}]
     * course_id : 1529564813858
     * course_num : 1
     * hongdian : 0
     * is_gold : 0
     * order_course_time : 2018-06-21,2018-06-2914:00-16:00
     * order_id : 1529650802208
     * order_money : 0.01
     * order_state : 2
     * ordre_time : 2018-06-22 15:00:02
     * pay_num : 1577
     * pay_type : 0
     * read_state : 0
     * school_id : 1529562005537
     * school_logo : head/initial.jpg
     * school_name : 学堂zhw
     * student_name : dfd
     * student_name : dfd
     * user_id : d56c00390a77475a84f67c68a675c243
     * user_phone : 11
     */

    private ClassSignBean classSign;
    private String class_money;
    private String class_name;
    private String class_photo;
    private String class_teacher;
    private String class_time;
    private CourseInfoBean courseInfo;
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
    private String school_id;
    private String school_logo;
    private String school_name;
    private String student_name;
    private String student_sex;
    private String user_id;
    private String user_phone;
    private List<CourseTimeInfoBean> courseTimeInfo;
    private boolean isLastSign;

    public ClassSignBean getClassSign() {
        return classSign;
    }

    public void setClassSign(ClassSignBean classSign) {
        this.classSign = classSign;
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

    public String getClass_time() {
        return class_time;
    }

    public void setClass_time(String class_time) {
        this.class_time = class_time;
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

    public List<CourseTimeInfoBean> getCourseTimeInfo() {
        return courseTimeInfo;
    }

    public void setCourseTimeInfo(List<CourseTimeInfoBean> courseTimeInfo) {
        this.courseTimeInfo = courseTimeInfo;
    }

    public boolean isLastSign() {
        return isLastSign;
    }

    public void setLastSign(boolean lastSign) {
        isLastSign = lastSign;
    }

    @Override
    public String toString() {
        return "LearningCourseBean{" +
                "classSign=" + classSign +
                ", class_money='" + class_money + '\'' +
                ", class_name='" + class_name + '\'' +
                ", class_photo='" + class_photo + '\'' +
                ", class_teacher='" + class_teacher + '\'' +
                ", class_time='" + class_time + '\'' +
                ", courseInfo=" + courseInfo +
                ", course_id='" + course_id + '\'' +
                ", course_num='" + course_num + '\'' +
                ", hongdian='" + hongdian + '\'' +
                ", is_gold='" + is_gold + '\'' +
                ", order_course_time='" + order_course_time + '\'' +
                ", order_id='" + order_id + '\'' +
                ", order_money='" + order_money + '\'' +
                ", order_state='" + order_state + '\'' +
                ", ordre_time='" + ordre_time + '\'' +
                ", pay_num='" + pay_num + '\'' +
                ", pay_type='" + pay_type + '\'' +
                ", read_state='" + read_state + '\'' +
                ", school_id='" + school_id + '\'' +
                ", school_logo='" + school_logo + '\'' +
                ", school_name='" + school_name + '\'' +
                ", student_name='" + student_name + '\'' +
                ", student_sex='" + student_sex + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", courseTimeInfo=" + courseTimeInfo +
                '}';
    }

    public static class ClassSignBean {
        /**
         * all_class : 7
         * course_id : 1529564813858
         * created : 2018-06-22
         * csign_id : 2
         * order_id : 1529650802208
         * study_class : 1
         * user_head : head/initial.jpg
         * user_id : d56c00390a77475a84f67c68a675c243
         * user_name : 117.22191725292298
         */

        private String all_class;
        private String course_id;
        private String created;
        private int csign_id;
        private String order_id;
        private int study_class;
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

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public int getStudy_class() {
            return study_class;
        }

        public void setStudy_class(int study_class) {
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

        @Override
        public String toString() {
            return "ClassSignBean{" +
                    "all_class='" + all_class + '\'' +
                    ", course_id='" + course_id + '\'' +
                    ", created='" + created + '\'' +
                    ", csign_id=" + csign_id +
                    ", order_id='" + order_id + '\'' +
                    ", study_class=" + study_class +
                    ", user_head='" + user_head + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", user_name='" + user_name + '\'' +
                    '}';
        }
    }

    public static class CourseInfoBean {
        /**
         * age_range : 120
         * course_address : 天津市河西区浦口道70号-增1好好的好的
         * course_capacity : 10
         * course_id : 1529564813858
         * course_info :
         * course_money : 1200元/7节
         * course_name : 马飘飘
         * course_photo : 2018/06/21/1529564813826.png
         * course_state : 1
         * course_teacher : 马飘飘
         * course_time : 2018-06-22 14:30:41
         * course_type : 文体艺术//书画:书法,球类:足球/篮球/排球/台球
         * enrol_num : 12
         * is_coups : 1
         * is_golds : 1
         * original_price : 100
         * popularity_num : 28
         * preferential_price : 100
         * school_id : 1529562005537
         * school_jing : 117.22191725292298
         * school_name : 学堂zhw
         * school_wei : 39.11746826637447
         * study_num : 1529564813858
         * user_id : e2d00b6a83994f83ad6322bd5cd2bdff
         * study_class
         */

        private String age_range;
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
        private String enrol_num;
        private String is_coups;
        private String is_golds;
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
        private int study_class;

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

        public String getEnrol_num() {
            return enrol_num;
        }

        public void setEnrol_num(String enrol_num) {
            this.enrol_num = enrol_num;
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

        public String getStudy_code() {
            return study_code == null ? "" : study_code;
        }

        public void setStudy_code(String study_code) {
            this.study_code = study_code;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public int getStudy_class() {
            return study_class;
        }

        public void setStudy_class(int study_class) {
            this.study_class = study_class;
        }


        @Override
        public String toString() {
            return "CourseInfoBean{" +
                    "age_range='" + age_range + '\'' +
                    ", course_address='" + course_address + '\'' +
                    ", course_capacity='" + course_capacity + '\'' +
                    ", course_id='" + course_id + '\'' +
                    ", course_info='" + course_info + '\'' +
                    ", course_money='" + course_money + '\'' +
                    ", course_name='" + course_name + '\'' +
                    ", course_photo='" + course_photo + '\'' +
                    ", course_state='" + course_state + '\'' +
                    ", course_teacher='" + course_teacher + '\'' +
                    ", course_time='" + course_time + '\'' +
                    ", course_type='" + course_type + '\'' +
                    ", enrol_num='" + enrol_num + '\'' +
                    ", is_coups='" + is_coups + '\'' +
                    ", is_golds='" + is_golds + '\'' +
                    ", original_price='" + original_price + '\'' +
                    ", popularity_num='" + popularity_num + '\'' +
                    ", preferential_price='" + preferential_price + '\'' +
                    ", school_id='" + school_id + '\'' +
                    ", school_jing='" + school_jing + '\'' +
                    ", school_name='" + school_name + '\'' +
                    ", school_wei='" + school_wei + '\'' +
                    ", study_num='" + study_num + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", study_class=" + study_class +
                    '}';
        }
    }

    public static class CourseTimeInfoBean {
        /**
         * course_id : 1529564813858
         * create_time : 2018-06-22 14:30:41
         * ctime_id : 97
         * ctime_times : 14:00-16:00
         * ctime_type : 0
         * ctime_week : 2018-06-21,2018-06-29
         */

        private String course_id;
        private String create_time;
        private int ctime_id;
        private String ctime_times;
        private String ctime_type;
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

        public String getCtime_type() {
            return ctime_type;
        }

        public void setCtime_type(String ctime_type) {
            this.ctime_type = ctime_type;
        }

        public String getCtime_week() {
            return ctime_week;
        }

        public void setCtime_week(String ctime_week) {
            this.ctime_week = ctime_week;
        }

        @Override
        public String toString() {
            return "CourseTimeInfoBean{" +
                    "course_id='" + course_id + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", ctime_id=" + ctime_id +
                    ", ctime_times='" + ctime_times + '\'' +
                    ", ctime_type='" + ctime_type + '\'' +
                    ", ctime_week='" + ctime_week + '\'' +
                    '}';
        }
    }
}

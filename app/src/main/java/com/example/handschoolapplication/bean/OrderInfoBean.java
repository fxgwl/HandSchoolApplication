package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */

public class OrderInfoBean {


    /**
     * data : {"class_money":"300元/3节","class_name":"测试课程三","class_photo":"2018/01/27/1517036295749.jpg,2018/01/27/1517036295774.jpg,2018/01/27/1517036295782.jpg,2018/01/27/1517036295788.jpg,2018/01/27/1517036295795.jpg","class_teacher":"1512791116691","class_time":"2018-03-16 17:54:25","courseInfo":{"age_range":"5-10岁","course_address":"天津河西区","course_capacity":"20","course_id":"1512791116691","course_info":"<p>&nbsp; &nbsp; 测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试，测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试，测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试。<\/p><p><img src=\"/PrivateSchool/images/20171209/1512791085278029337.jpg\" title=\"1512791085278029337.jpg\" alt=\"311619.jpg\" width=\"260\" height=\"167\"/><\/p><p>&nbsp; &nbsp; 测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试<\/p>","course_money":"300元/3节","course_name":"测试课程三","course_photo":"2018/01/27/1517036295749.jpg,2018/01/27/1517036295774.jpg,2018/01/27/1517036295782.jpg,2018/01/27/1517036295788.jpg,2018/01/27/1517036295795.jpg","course_state":"0","course_teacher":"周一一","course_time":"2018-01-27 14:58:15","course_type":"文体艺术//音乐:二胡;文体艺术//书画","dengji":"5","enrol_num":"6","is_coups":"1","is_golds":"1,1","original_price":"130","popularity_num":"52","preferential_price":"100","school_id":"1512784945461","school_jing":"117.22 ","school_name":"安鑫一家","school_wei":"39.12","study_num":"1512791116691","user_id":"10c5bb01a3c8480da20ee09367fdb111"},"courseTimeInfo":[{"course_id":"1512791116691","create_time":"2017-12-09 11:45:16","ctime_id":8,"ctime_times":"10:10-12:00.13:00-14:00.15:00-16:00","ctime_week":"星期一"},{"course_id":"1512791116691","create_time":"2017-12-09 11:45:16","ctime_id":9,"ctime_times":"12:00-14:00","ctime_week":"星期二"},{"course_id":"1512791116691","create_time":"2017-12-09 11:45:16","ctime_id":10,"ctime_times":"10:10-12:00.13:00-14:00.15:00-16:00","ctime_week":"星期三"}],"course_id":"1512791116691","course_num":"1","hongdian":"1","is_gold":"30","order_course_time":"星期一10:10-12:00.13:00-14:00.15:00-16:00\n星期二12:00-14:00\n星期三10:10-12:00.13:00-14:00.15:00-16:00","order_id":"1521194056186","order_money":"300.00","order_state":"1","ordre_time":"2018-03-16 17:54:16","pay_num":"18333616097","pay_type":"0","school_id":"1512784945461","school_logo":"bicths/10c5bb01a3c8480da20ee09367fdb111/1512790579136.jpg","school_name":"安鑫一家","student_name":"3333333333","userInfo":{"data_integrity":"100","glmoney":"0","head_photo":"bicths/10c5bb01a3c8480da20ee09367fdb111/1512790579136.jpg","id_number":"130524199205133025","mechanism_address":"1512784945258","mechanism_city":"天津市-河西区","mechanism_ctime":"20170513","mechanism_name":"安鑫一家","mechanism_type":"文体艺术//音乐:二胡;文体艺术//书画;学习辅导//中小学教育:小学/中学/数学/语文/物理;活动拓展//夏令营;早教;托教;家教","member_name":"123456789","mid_photo":"bicths/10c5bb01a3c8480da20ee09367fdb111/1512784945456.jpg","pingjia":"5","qualification_prove":"bicths/10c5bb01a3c8480da20ee09367fdb111/1512784945362.jpg","school_id":"1512784945461","signed_num":"1","totle_sign":"2","user_area":"39.12","user_code":"000000","user_create_time":"2017-12-09 09:49:38","user_dengji":"0","user_gold":"20","user_id":"10c5bb01a3c8480da20ee09367fdb111","user_integral":"20","user_name":"117.22 ","user_password":"1","user_phone":"15122947309","user_renqi":"29","user_state":"0","user_type":"1"},"user_id":"d2da9c8b335a4bcfba87e29052e288ad","user_phone":"18333616097"}
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
         * class_money : 300元/3节
         * class_name : 测试课程三
         * class_photo : 2018/01/27/1517036295749.jpg,2018/01/27/1517036295774.jpg,2018/01/27/1517036295782.jpg,2018/01/27/1517036295788.jpg,2018/01/27/1517036295795.jpg
         * class_teacher : 1512791116691
         * class_time : 2018-03-16 17:54:25
         * courseInfo : {"age_range":"5-10岁","course_address":"天津河西区","course_capacity":"20","course_id":"1512791116691","course_info":"<p>&nbsp; &nbsp; 测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试，测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试，测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试。<\/p><p><img src=\"/PrivateSchool/images/20171209/1512791085278029337.jpg\" title=\"1512791085278029337.jpg\" alt=\"311619.jpg\" width=\"260\" height=\"167\"/><\/p><p>&nbsp; &nbsp; 测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试<\/p>","course_money":"300元/3节","course_name":"测试课程三","course_photo":"2018/01/27/1517036295749.jpg,2018/01/27/1517036295774.jpg,2018/01/27/1517036295782.jpg,2018/01/27/1517036295788.jpg,2018/01/27/1517036295795.jpg","course_state":"0","course_teacher":"周一一","course_time":"2018-01-27 14:58:15","course_type":"文体艺术//音乐:二胡;文体艺术//书画","dengji":"5","enrol_num":"6","is_coups":"1","is_golds":"1,1","original_price":"130","popularity_num":"52","preferential_price":"100","school_id":"1512784945461","school_jing":"117.22 ","school_name":"安鑫一家","school_wei":"39.12","study_num":"1512791116691","user_id":"10c5bb01a3c8480da20ee09367fdb111"}
         * courseTimeInfo : [{"course_id":"1512791116691","create_time":"2017-12-09 11:45:16","ctime_id":8,"ctime_times":"10:10-12:00.13:00-14:00.15:00-16:00","ctime_week":"星期一"},{"course_id":"1512791116691","create_time":"2017-12-09 11:45:16","ctime_id":9,"ctime_times":"12:00-14:00","ctime_week":"星期二"},{"course_id":"1512791116691","create_time":"2017-12-09 11:45:16","ctime_id":10,"ctime_times":"10:10-12:00.13:00-14:00.15:00-16:00","ctime_week":"星期三"}]
         * course_id : 1512791116691
         * course_num : 1
         * hongdian : 1
         * is_gold : 30
         * order_course_time : 星期一10:10-12:00.13:00-14:00.15:00-16:00
         星期二12:00-14:00
         星期三10:10-12:00.13:00-14:00.15:00-16:00
         * order_id : 1521194056186
         * order_money : 300.00
         * order_state : 1
         * ordre_time : 2018-03-16 17:54:16
         * pay_num : 18333616097
         * pay_type : 0
         * school_id : 1512784945461
         * school_logo : bicths/10c5bb01a3c8480da20ee09367fdb111/1512790579136.jpg
         * school_name : 安鑫一家
         * student_name : 3333333333
         * userInfo : {"data_integrity":"100","glmoney":"0","head_photo":"bicths/10c5bb01a3c8480da20ee09367fdb111/1512790579136.jpg","id_number":"130524199205133025","mechanism_address":"1512784945258","mechanism_city":"天津市-河西区","mechanism_ctime":"20170513","mechanism_name":"安鑫一家","mechanism_type":"文体艺术//音乐:二胡;文体艺术//书画;学习辅导//中小学教育:小学/中学/数学/语文/物理;活动拓展//夏令营;早教;托教;家教","member_name":"123456789","mid_photo":"bicths/10c5bb01a3c8480da20ee09367fdb111/1512784945456.jpg","pingjia":"5","qualification_prove":"bicths/10c5bb01a3c8480da20ee09367fdb111/1512784945362.jpg","school_id":"1512784945461","signed_num":"1","totle_sign":"2","user_area":"39.12","user_code":"000000","user_create_time":"2017-12-09 09:49:38","user_dengji":"0","user_gold":"20","user_id":"10c5bb01a3c8480da20ee09367fdb111","user_integral":"20","user_name":"117.22 ","user_password":"1","user_phone":"15122947309","user_renqi":"29","user_state":"0","user_type":"1"}
         * user_id : d2da9c8b335a4bcfba87e29052e288ad
         * user_phone : 18333616097
         * class_people
         * student_sex
         */

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
        private String school_id;
        private String school_logo;
        private String school_name;
        private String student_name;
        private String student_sex;
        private UserInfoBean userInfo;
        private String user_id;
        private String user_phone;
        private String class_people;
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

        public String getClass_people() {
            return class_people;
        }

        public void setClass_people(String class_people) {
            this.class_people = class_people;
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


        @Override
        public String toString() {
            return "DataBean{" +
                    "class_money='" + class_money + '\'' +
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
                    ", school_id='" + school_id + '\'' +
                    ", school_logo='" + school_logo + '\'' +
                    ", school_name='" + school_name + '\'' +
                    ", student_name='" + student_name + '\'' +
                    ", userInfo=" + userInfo +
                    ", user_id='" + user_id + '\'' +
                    ", user_phone='" + user_phone + '\'' +
                    ", class_people='" + class_people + '\'' +
                    ", courseTimeInfo=" + courseTimeInfo +
                    '}';
        }

        public static class CourseInfoBean {
            /**
             * age_range : 5-10岁
             * course_address : 天津河西区
             * course_capacity : 20
             * course_id : 1512791116691
             * course_info : <p>&nbsp; &nbsp; 测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试，测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试，测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试。</p><p><img src="/PrivateSchool/images/20171209/1512791085278029337.jpg" title="1512791085278029337.jpg" alt="311619.jpg" width="260" height="167"/></p><p>&nbsp; &nbsp; 测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试</p>
             * course_money : 300元/3节
             * course_name : 测试课程三
             * course_photo : 2018/01/27/1517036295749.jpg,2018/01/27/1517036295774.jpg,2018/01/27/1517036295782.jpg,2018/01/27/1517036295788.jpg,2018/01/27/1517036295795.jpg
             * course_state : 0
             * course_teacher : 周一一
             * course_time : 2018-01-27 14:58:15
             * course_type : 文体艺术//音乐:二胡;文体艺术//书画
             * dengji : 5
             * enrol_num : 6
             * is_coups : 1
             * is_golds : 1,1
             * original_price : 130
             * popularity_num : 52
             * preferential_price : 100
             * school_id : 1512784945461
             * school_jing : 117.22
             * school_name : 安鑫一家
             * school_wei : 39.12
             * study_num : 1512791116691
             * user_id : 10c5bb01a3c8480da20ee09367fdb111
             */

            private String age_range;
            private String course_address;
            private String course_capacity;
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
            private String user_id;

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

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
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
                        ", dengji='" + dengji + '\'' +
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
                        '}';
            }
        }

        public static class UserInfoBean {
            /**
             * data_integrity : 100
             * glmoney : 0
             * head_photo : bicths/10c5bb01a3c8480da20ee09367fdb111/1512790579136.jpg
             * id_number : 130524199205133025
             * mechanism_address : 1512784945258
             * mechanism_city : 天津市-河西区
             * mechanism_ctime : 20170513
             * mechanism_name : 安鑫一家
             * mechanism_type : 文体艺术//音乐:二胡;文体艺术//书画;学习辅导//中小学教育:小学/中学/数学/语文/物理;活动拓展//夏令营;早教;托教;家教
             * member_name : 123456789
             * mid_photo : bicths/10c5bb01a3c8480da20ee09367fdb111/1512784945456.jpg
             * pingjia : 5
             * qualification_prove : bicths/10c5bb01a3c8480da20ee09367fdb111/1512784945362.jpg
             * school_id : 1512784945461
             * signed_num : 1
             * totle_sign : 2
             * user_area : 39.12
             * user_code : 000000
             * user_create_time : 2017-12-09 09:49:38
             * user_dengji : 0
             * user_gold : 20
             * user_id : 10c5bb01a3c8480da20ee09367fdb111
             * user_integral : 20
             * user_name : 117.22
             * user_password : 1
             * user_phone : 15122947309
             * user_renqi : 29
             * user_state : 0
             * user_type : 1
             */

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
            private String pingjia;
            private String qualification_prove;
            private String school_id;
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


            @Override
            public String toString() {
                return "UserInfoBean{" +
                        "data_integrity='" + data_integrity + '\'' +
                        ", glmoney='" + glmoney + '\'' +
                        ", head_photo='" + head_photo + '\'' +
                        ", id_number='" + id_number + '\'' +
                        ", mechanism_address='" + mechanism_address + '\'' +
                        ", mechanism_city='" + mechanism_city + '\'' +
                        ", mechanism_ctime='" + mechanism_ctime + '\'' +
                        ", mechanism_name='" + mechanism_name + '\'' +
                        ", mechanism_type='" + mechanism_type + '\'' +
                        ", member_name='" + member_name + '\'' +
                        ", mid_photo='" + mid_photo + '\'' +
                        ", pingjia='" + pingjia + '\'' +
                        ", qualification_prove='" + qualification_prove + '\'' +
                        ", school_id='" + school_id + '\'' +
                        ", signed_num='" + signed_num + '\'' +
                        ", totle_sign='" + totle_sign + '\'' +
                        ", user_area='" + user_area + '\'' +
                        ", user_code='" + user_code + '\'' +
                        ", user_create_time='" + user_create_time + '\'' +
                        ", user_dengji='" + user_dengji + '\'' +
                        ", user_gold='" + user_gold + '\'' +
                        ", user_id='" + user_id + '\'' +
                        ", user_integral='" + user_integral + '\'' +
                        ", user_name='" + user_name + '\'' +
                        ", user_password='" + user_password + '\'' +
                        ", user_phone='" + user_phone + '\'' +
                        ", user_renqi='" + user_renqi + '\'' +
                        ", user_state='" + user_state + '\'' +
                        ", user_type='" + user_type + '\'' +
                        '}';
            }
        }

        public static class CourseTimeInfoBean {
            /**
             * course_id : 1512791116691
             * create_time : 2017-12-09 11:45:16
             * ctime_id : 8
             * ctime_times : 10:10-12:00.13:00-14:00.15:00-16:00
             * ctime_week : 星期一
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


            @Override
            public String toString() {
                return "CourseTimeInfoBean{" +
                        "course_id='" + course_id + '\'' +
                        ", create_time='" + create_time + '\'' +
                        ", ctime_id=" + ctime_id +
                        ", ctime_times='" + ctime_times + '\'' +
                        ", ctime_week='" + ctime_week + '\'' +
                        '}';
            }
        }
    }
}

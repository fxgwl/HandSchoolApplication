package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by zhw on 2018/8/25.
 */

public class OrderDetailsBean {


    /**
     * data : {"class_money":"1200元/48节","class_name":"高考冲刺","class_photo":"2018/08/03/1533276606865.png,2018/08/03/1533276607014.png","class_teacher":"1533265537086","courseInfo":{"age_range":"10","courseTimeInfos":[{"course_id":"1533265537086","create_time":"2018-08-03 11:05:37","ctime_id":186,"ctime_times":"10:00-10:00","ctime_type":"1","ctime_week":"星期一"},{"course_id":"1533265537086","create_time":"2018-08-03 11:05:37","ctime_id":187,"ctime_times":"11:00-12:00","ctime_type":"1","ctime_week":"星期二"}],"course_address":"天津天津河西区劝业场街道辽宁路","course_capacity":"10","course_id":"1533265537086","course_info":"<ul style=\"outline: none; margin-top: 95px; padding: 0px; font-family: &quot;Microsoft YaHei&quot;, Helvetica, tahoma, arial, sans-serif; color: rgb(255, 255, 255); font-size: 15px; text-align: center; white-space: normal; text-size-adjust: auto;\" class=\" list-paddingleft-2\"><li><p style=\"outline: none; margin: 52px auto 0px; padding: 0px; line-height: 28px; color: rgb(102, 102, 102); text-indent: 28px; text-align: left; width: 326px; font-size: 14px;\">教学经验是衡量教师教学成果的一个重要的标准。随着网络教学的推进，利用互联网教学的经验尤为重要。教师在完成资质审核后，进入教学经验的认证，教学经验的认证通过在线试讲及考核的方式来实现。教学资质（Q）和教学经验（E）的考核均量化为分值作为考核标准。<\/p><\/li><\/ul><p><br/><\/p>","course_money":"1200元/48节","course_name":"高考冲刺","course_photo":"2018/08/03/1533276606865.png,2018/08/03/1533276607014.png","course_state":"1","course_teacher":"马飘飘","course_time":"2018-08-03 14:10:07","course_type":"活动拓展//夏令营:夏令营","enrol_num":"2","is_coups":"1","is_golds":"1","original_price":"100","popularity_num":"29","preferential_price":"100","school_id":"1533264228251","school_jing":"117.199419","school_name":"马飘飘","school_wei":"39.12673","sd_id":288,"study_num":"1533265537086","user_id":"3008b7e173c24a788ac835f6b4c7e1cc"},"course_id":"1533265537086","course_num":"1","hongdian":"0","order_course_time":"星期一10:00-10:00\n星期二11:00-12:00","order_id":"1533276710172","order_money":"1200","order_state":"2","ordre_time":"2018-08-03 14:11:50","read_state":"1","school_id":"1533264228251","school_logo":"bicths/3008b7e173c24a788ac835f6b4c7e1cc/1533264982944.jpg","school_name":"马飘飘","user_id":"d9759e30456a4922bd4fde3952148903","user_phone":"15122947301"}
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
         * class_money : 1200元/48节
         * class_name : 高考冲刺
         * class_photo : 2018/08/03/1533276606865.png,2018/08/03/1533276607014.png
         * class_teacher : 1533265537086
         * courseInfo : {"age_range":"10","courseTimeInfos":[{"course_id":"1533265537086","create_time":"2018-08-03 11:05:37","ctime_id":186,"ctime_times":"10:00-10:00","ctime_type":"1","ctime_week":"星期一"},{"course_id":"1533265537086","create_time":"2018-08-03 11:05:37","ctime_id":187,"ctime_times":"11:00-12:00","ctime_type":"1","ctime_week":"星期二"}],"course_address":"天津天津河西区劝业场街道辽宁路","course_capacity":"10","course_id":"1533265537086","course_info":"<ul style=\"outline: none; margin-top: 95px; padding: 0px; font-family: &quot;Microsoft YaHei&quot;, Helvetica, tahoma, arial, sans-serif; color: rgb(255, 255, 255); font-size: 15px; text-align: center; white-space: normal; text-size-adjust: auto;\" class=\" list-paddingleft-2\"><li><p style=\"outline: none; margin: 52px auto 0px; padding: 0px; line-height: 28px; color: rgb(102, 102, 102); text-indent: 28px; text-align: left; width: 326px; font-size: 14px;\">教学经验是衡量教师教学成果的一个重要的标准。随着网络教学的推进，利用互联网教学的经验尤为重要。教师在完成资质审核后，进入教学经验的认证，教学经验的认证通过在线试讲及考核的方式来实现。教学资质（Q）和教学经验（E）的考核均量化为分值作为考核标准。<\/p><\/li><\/ul><p><br/><\/p>","course_money":"1200元/48节","course_name":"高考冲刺","course_photo":"2018/08/03/1533276606865.png,2018/08/03/1533276607014.png","course_state":"1","course_teacher":"马飘飘","course_time":"2018-08-03 14:10:07","course_type":"活动拓展//夏令营:夏令营","enrol_num":"2","is_coups":"1","is_golds":"1","original_price":"100","popularity_num":"29","preferential_price":"100","school_id":"1533264228251","school_jing":"117.199419","school_name":"马飘飘","school_wei":"39.12673","sd_id":288,"study_num":"1533265537086","user_id":"3008b7e173c24a788ac835f6b4c7e1cc"}
         * course_id : 1533265537086
         * course_num : 1
         * hongdian : 0
         * order_course_time : 星期一10:00-10:00
         星期二11:00-12:00
         * order_id : 1533276710172
         * order_money : 1200
         * order_state : 2
         * ordre_time : 2018-08-03 14:11:50
         * read_state : 1
         * school_id : 1533264228251
         * school_logo : bicths/3008b7e173c24a788ac835f6b4c7e1cc/1533264982944.jpg
         * school_name : 马飘飘
         * user_id : d9759e30456a4922bd4fde3952148903
         * user_phone : 15122947301
         */

        private String class_money;
        private String class_name;
        private String class_photo;
        private String class_teacher;
        private CourseInfoBean courseInfo;
        private String course_id;
        private String course_num;
        private String hongdian;
        private String order_course_time;
        private String order_id;
        private String order_money;
        private String order_state;
        private String ordre_time;
        private String read_state;
        private String school_id;
        private String school_logo;
        private String school_name;
        private String user_id;
        private String user_phone;
        private String student_sex;
        private String student_name;

        public String getStudent_sex() {
            return student_sex;
        }

        public void setStudent_sex(String student_sex) {
            this.student_sex = student_sex;
        }

        public String getStudent_name() {
            return student_name;
        }

        public void setStudent_name(String student_name) {
            this.student_name = student_name;
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

        public static class CourseInfoBean {
            /**
             * age_range : 10
             * courseTimeInfos : [{"course_id":"1533265537086","create_time":"2018-08-03 11:05:37","ctime_id":186,"ctime_times":"10:00-10:00","ctime_type":"1","ctime_week":"星期一"},{"course_id":"1533265537086","create_time":"2018-08-03 11:05:37","ctime_id":187,"ctime_times":"11:00-12:00","ctime_type":"1","ctime_week":"星期二"}]
             * course_address : 天津天津河西区劝业场街道辽宁路
             * course_capacity : 10
             * course_id : 1533265537086
             * course_info : <ul style="outline: none; margin-top: 95px; padding: 0px; font-family: &quot;Microsoft YaHei&quot;, Helvetica, tahoma, arial, sans-serif; color: rgb(255, 255, 255); font-size: 15px; text-align: center; white-space: normal; text-size-adjust: auto;" class=" list-paddingleft-2"><li><p style="outline: none; margin: 52px auto 0px; padding: 0px; line-height: 28px; color: rgb(102, 102, 102); text-indent: 28px; text-align: left; width: 326px; font-size: 14px;">教学经验是衡量教师教学成果的一个重要的标准。随着网络教学的推进，利用互联网教学的经验尤为重要。教师在完成资质审核后，进入教学经验的认证，教学经验的认证通过在线试讲及考核的方式来实现。教学资质（Q）和教学经验（E）的考核均量化为分值作为考核标准。</p></li></ul><p><br/></p>
             * course_money : 1200元/48节
             * course_name : 高考冲刺
             * course_photo : 2018/08/03/1533276606865.png,2018/08/03/1533276607014.png
             * course_state : 1
             * course_teacher : 马飘飘
             * course_time : 2018-08-03 14:10:07
             * course_type : 活动拓展//夏令营:夏令营
             * enrol_num : 2
             * is_coups : 1
             * is_golds : 1
             * original_price : 100
             * popularity_num : 29
             * preferential_price : 100
             * school_id : 1533264228251
             * school_jing : 117.199419
             * school_name : 马飘飘
             * school_wei : 39.12673
             * sd_id : 288
             * study_num : 1533265537086
             * user_id : 3008b7e173c24a788ac835f6b4c7e1cc
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
            private int sd_id;
            private String study_num;
            private String study_code;
            private String user_id;
            private String student_sex;
            private String student_name;
            private int study_class;

            public String getStudy_code() {
                return study_code == null ? "" : study_code;
            }

            public void setStudy_code(String study_code) {
                this.study_code = study_code;
            }

            private List<CourseTimeInfosBean> courseTimeInfos;

            public int getStudy_class() {
                return study_class;
            }

            public String getStudent_sex() {
                return student_sex;
            }

            public void setStudent_sex(String student_sex) {
                this.student_sex = student_sex;
            }

            public String getStudent_name() {
                return student_name;
            }

            public void setStudent_name(String student_name) {
                this.student_name = student_name;
            }

            public void setStudy_class(int study_class) {
                this.study_class = study_class;
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

            public int getSd_id() {
                return sd_id;
            }

            public void setSd_id(int sd_id) {
                this.sd_id = sd_id;
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

            public List<CourseTimeInfosBean> getCourseTimeInfos() {
                return courseTimeInfos;
            }

            public void setCourseTimeInfos(List<CourseTimeInfosBean> courseTimeInfos) {
                this.courseTimeInfos = courseTimeInfos;
            }

            public static class CourseTimeInfosBean {
                /**
                 * course_id : 1533265537086
                 * create_time : 2018-08-03 11:05:37
                 * ctime_id : 186
                 * ctime_times : 10:00-10:00
                 * ctime_type : 1
                 * ctime_week : 星期一
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
            }
        }
    }
}

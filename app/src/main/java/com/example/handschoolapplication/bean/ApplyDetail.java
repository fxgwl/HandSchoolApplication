package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/2.
 */

public class ApplyDetail {


    /**
     * data : [{"all_class":"20","course_id":"1521529263014","created":"2018-03-20","csign_id":4,"study_class":"7","userInfos":[{"head_photo":"bicths/2725043a517341a280141a606b7e50fa/1521527214356.jpg","user_id":"2725043a517341a280141a606b7e50fa","user_name":"张宁","user_phone":"15845031735"}],"user_head":"bicths/1e2dd5ff8ebc47bb8a01599e5a9d0e85/1521527225229.jpg","user_id":"2725043a517341a280141a606b7e50fa","user_name":"117.23616327304414"},{"all_class":"10","course_id":"1521529263014","created":"2018-03-21","csign_id":5,"study_class":"6","userInfos":[{"user_id":"169cabbc911e465f9a6435bed8607437","user_name":"","user_phone":"7777777"}],"user_head":"bicths/1e2dd5ff8ebc47bb8a01599e5a9d0e85/1521527225229.jpg","user_id":"169cabbc911e465f9a6435bed8607437","user_name":"117.23616327304414"}]
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
         * all_class : 20
         * course_id : 1521529263014
         * created : 2018-03-20
         * csign_id : 4
         * study_class : 7
         * userInfos : [{"head_photo":"bicths/2725043a517341a280141a606b7e50fa/1521527214356.jpg","user_id":"2725043a517341a280141a606b7e50fa","user_name":"张宁","user_phone":"15845031735"}]
         * user_head : bicths/1e2dd5ff8ebc47bb8a01599e5a9d0e85/1521527225229.jpg
         * user_id : 2725043a517341a280141a606b7e50fa
         * user_name : 117.23616327304414
         */

        private String all_class;
        private String course_id;
        private String created;
        private int csign_id;
        private String study_class;
        private String user_head;
        private String user_id;
        private String user_name;
        public boolean isCheck;
        private List<UserInfosBean> userInfos;
        private OrderInfo orderInfo;

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

        public String getStudy_class() {
            return study_class;
        }

        public void setStudy_class(String study_class) {
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

        public List<UserInfosBean> getUserInfos() {
            return userInfos;
        }

        public void setUserInfos(List<UserInfosBean> userInfos) {
            this.userInfos = userInfos;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public OrderInfo getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfo orderInfo) {
            this.orderInfo = orderInfo;
        }

        public static class UserInfosBean {
            /**
             * head_photo : bicths/2725043a517341a280141a606b7e50fa/1521527214356.jpg
             * user_id : 2725043a517341a280141a606b7e50fa
             * user_name : 张宁
             * user_phone : 15845031735
             */

            private String head_photo;
            private String user_id;
            private String user_name;
            private String user_phone;

            public String getHead_photo() {
                return head_photo;
            }

            public void setHead_photo(String head_photo) {
                this.head_photo = head_photo;
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
        }

        public static class OrderInfo{

            /**
             * class_money : 0.01元/1节
             * class_name : 测试编辑课程
             * class_photo : 2019/01/28/1548650798416.jpeg
             * class_teacher : 1548650798426
             * class_time : 2019-01-28 17:47:33
             * course_id : 1548650798426
             * course_num : 1
             * hongdian : 1
             * is_gold : 0
             * order_course_time : 2019-01-29,2019-01-3111:00-12:00
             * order_id : 1548668844502
             * order_money : 0.01
             * order_state : 1
             * ordre_time : 2019-01-28 17:47:24
             * pay_num : 15122947309
             * pay_type : 0
             * price_id : 78
             * read_state : 1
             * refund_amount : 0
             * school_id : 1548645002909
             * school_logo : head/ad75a34d189d46deab7d4d5617c8a075/1548646308397.jpg
             * school_name : 博佳机器人俱乐部
             * student_name : 你
             * student_sex : 女
             * user_id : 5fe52a40672f4ecfa1d48b9b8670735a
             * user_phone : 15122947309
             */

            private String class_money;
            private String class_name;
            private String class_photo;
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
            private int price_id;
            private String read_state;
            private int refund_amount;
            private String school_id;
            private String school_logo;
            private String school_name;
            private String student_name;
            private String student_sex;
            private String user_id;
            private String user_phone;

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

            public int getPrice_id() {
                return price_id;
            }

            public void setPrice_id(int price_id) {
                this.price_id = price_id;
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
    }
}

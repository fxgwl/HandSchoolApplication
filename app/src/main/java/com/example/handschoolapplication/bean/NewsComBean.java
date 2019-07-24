package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Axehome_Mr.z on 2019/1/29 20:01
 * $Describe
 */
public class NewsComBean {


    /**
     * data : [{"age_range":"2-18","collect_no":3,"course_address":"河北省廊坊市广阳区建设路与爱民道交叉口新世界B座1317室廊坊新世界中心B座","course_capacity":"7","course_id":"1548645391934","course_info":"<p>是否热爱动画？是否有一双灵巧的手？是否想随时随地的记录生活？只要你愿意，每节30分钟，带你记录生活中的细节。我们从简笔画开始观察生活，绘制生活中的点点滴滴，留住只属于你自己的一份回忆与感动！每节课程将带着同学们在电脑上绘制一些小东西，并由浅入深教授大家一些绘画的小技法。同学们可以利用课余时间进行练习，并自主DIY一些有趣小东西<\/p>","course_money":"0.01元/5节","course_name":"Photoshop绘画","course_photo":"2019/01/28/1548653867057.jpg","course_state":"2","course_teacher":"LEE老师","course_time":"2019-01-28 13:37:47","course_type":"学习辅导//中小学教育:小学","dengji":"3","enrol_num":"1","hot_state":"0","is_coups":"1","is_golds":"1","original_price":"100","picture_five":"","picture_four":"","picture_one":"2019/01/28/1548653865233.jpg","picture_three":"","picture_two":"","popularity_num":"72","preferential_price":"100","school_id":"1548643960487","school_jing":"116.721376","school_name":"天津测试机构","school_wei":"39.534523","sd_id":220,"study_num":"1548645391934","user_id":"3e3b8250809a430f80f4f71a25de7bf0"},{"age_range":"2-18","collect_no":0,"course_address":"天津市天津市河西区南京路与马场道交汇处天津国际贸易中心","course_capacity":"7","course_id":"1548663097936","course_info":"<p>测试<\/p>","course_money":"0.01元/4节","course_name":"Photoshop绘画2","course_photo":"2019/01/28/1548663097926.jpg,2019/01/28/1548663097927.jpeg","course_state":"0","course_teacher":"LEE老师","course_time":"2019-01-28 16:11:57","course_type":"学习辅导//中小学教育:小学","dengji":"3","enrol_num":"1","hot_state":"0","is_coups":"1","is_golds":"1","original_price":"100","picture_five":"","picture_four":"","picture_one":"2019/01/28/1548663097926.jpg","picture_three":"","picture_two":"2019/01/28/1548663097927.jpeg","popularity_num":"14","preferential_price":"100","school_id":"1548643960487","school_jing":"117.222609","school_name":"天津测试机构","school_wei":"39.119605","sd_id":224,"study_num":"1548663097936","user_id":"3e3b8250809a430f80f4f71a25de7bf0"}]
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
         * age_range : 2-18
         * collect_no : 3
         * course_address : 河北省廊坊市广阳区建设路与爱民道交叉口新世界B座1317室廊坊新世界中心B座
         * course_capacity : 7
         * course_id : 1548645391934
         * course_info : <p>是否热爱动画？是否有一双灵巧的手？是否想随时随地的记录生活？只要你愿意，每节30分钟，带你记录生活中的细节。我们从简笔画开始观察生活，绘制生活中的点点滴滴，留住只属于你自己的一份回忆与感动！每节课程将带着同学们在电脑上绘制一些小东西，并由浅入深教授大家一些绘画的小技法。同学们可以利用课余时间进行练习，并自主DIY一些有趣小东西</p>
         * course_money : 0.01元/5节
         * course_name : Photoshop绘画
         * course_photo : 2019/01/28/1548653867057.jpg
         * course_state : 2
         * course_teacher : LEE老师
         * course_time : 2019-01-28 13:37:47
         * course_type : 学习辅导//中小学教育:小学
         * dengji : 3
         * enrol_num : 1
         * hot_state : 0
         * is_coups : 1
         * is_golds : 1
         * original_price : 100
         * picture_five :
         * picture_four :
         * picture_one : 2019/01/28/1548653865233.jpg
         * picture_three :
         * picture_two :
         * popularity_num : 72
         * preferential_price : 100
         * school_id : 1548643960487
         * school_jing : 116.721376
         * school_name : 天津测试机构
         * school_wei : 39.534523
         * sd_id : 220
         * study_num : 1548645391934
         * user_id : 3e3b8250809a430f80f4f71a25de7bf0
         * message_time : 3e3b8250809a430f80f4f71a25de7bf0
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
        private String is_coups;
        private String is_golds;
        private String original_price;
        private String picture_five;
        private String picture_four;
        private String picture_one;
        private String picture_three;
        private String picture_two;
        private String popularity_num;
        private String preferential_price;
        private String school_id;
        private String school_jing;
        private String school_name;
        private String school_wei;
        private int sd_id;
        private String study_num;
        private String user_id;
        private String message_time;

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

        public String getPicture_five() {
            return picture_five;
        }

        public void setPicture_five(String picture_five) {
            this.picture_five = picture_five;
        }

        public String getPicture_four() {
            return picture_four;
        }

        public void setPicture_four(String picture_four) {
            this.picture_four = picture_four;
        }

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

        public String getMessage_time() {
            return message_time == null ? "" : message_time;
        }

        public void setMessage_time(String message_time) {
            this.message_time = message_time;
        }
    }
}

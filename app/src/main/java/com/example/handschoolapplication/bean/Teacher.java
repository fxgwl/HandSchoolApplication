package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class Teacher {


    /**
     * data : [{"school_id":"1507602671757","teacher_education":"本科","teacher_id":8,"teacher_longevity":"很牛","teacher_motto":"厉害了","teacher_name":"下一","teacher_photo":"2017/10/10/1507603104389.jpg","teacher_through":"费服你改的可"},{"school_id":"1507602671757","teacher_education":"中心","teacher_id":10,"teacher_longevity":"初中","teacher_motto":"行政村","teacher_name":"出租车v","teacher_photo":"2017/10/10/1507603144448.png","teacher_through":"初中"},{"school_id":"1507602671757","teacher_education":"初中","teacher_id":11,"teacher_longevity":"v","teacher_motto":"v收到","teacher_name":"查证出","teacher_photo":"2017/10/10/1507603165916.jpg","teacher_through":"v收到"}]
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
         * school_id : 1507602671757
         * teacher_education : 本科
         * teacher_id : 8
         * teacher_longevity : 很牛
         * teacher_motto : 厉害了
         * teacher_name : 下一
         * teacher_photo : 2017/10/10/1507603104389.jpg
         * teacher_through : 费服你改的可
         */

        private String school_id;
        private String teacher_education;
        private int teacher_id;
        private String teacher_longevity;
        private String teacher_motto;
        private String teacher_name;
        private String teacher_photo;
        private String teacher_through;

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getTeacher_education() {
            return teacher_education;
        }

        public void setTeacher_education(String teacher_education) {
            this.teacher_education = teacher_education;
        }

        public int getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(int teacher_id) {
            this.teacher_id = teacher_id;
        }

        public String getTeacher_longevity() {
            return teacher_longevity;
        }

        public void setTeacher_longevity(String teacher_longevity) {
            this.teacher_longevity = teacher_longevity;
        }

        public String getTeacher_motto() {
            return teacher_motto;
        }

        public void setTeacher_motto(String teacher_motto) {
            this.teacher_motto = teacher_motto;
        }

        public String getTeacher_name() {
            return teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public String getTeacher_photo() {
            return teacher_photo;
        }

        public void setTeacher_photo(String teacher_photo) {
            this.teacher_photo = teacher_photo;
        }

        public String getTeacher_through() {
            return teacher_through;
        }

        public void setTeacher_through(String teacher_through) {
            this.teacher_through = teacher_through;
        }
    }
}

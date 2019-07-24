package com.example.handschoolapplication.bean;

import java.util.List;

/**
 * Created by Axehome_Mr.z on 2019/1/29 21:16
 * $Describe
 */
public class CourseNewsBean {


    /**
     * code : 0
     * count : 2
     * data : [{"course_id":"1548663097936","head_photo":"head/initial.jpg","message_content":"JzjxhvxzzzzjnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnrashjkhvjkvhJKDfhjkzhdfjkhjkzfxhb nxbcjvklhdufyusfhjshjksh788174238907r87017309819284hjshdcfjkzbxcjbzxjchjshfjhdsjfhjsdbvxzbnvbjksdhgfgdfhagsdjfgvbnccxbz","message_state":"0","message_time":"2019-01-29 19:27:43","message_title":"天津测试机构","message_type":"head/3e3b8250809a430f80f4f71a25de7bf0/1548660588227.jpg","school_id":"1548643960487","school_name":"天津测试机构","smessage_id":115,"user_id":"f34bad4fe0fc46b985a1dfee556a56a3","user_name":"tel_1548729013"},{"course_id":"1548663097936","head_photo":"head/initial.jpg","message_content":"1","message_state":"0","message_time":"2019-01-29 18:50:37","message_title":"天津测试机构","message_type":"head/3e3b8250809a430f80f4f71a25de7bf0/1548660588227.jpg","school_id":"1548643960487","school_name":"天津测试机构","smessage_id":114,"user_id":"f34bad4fe0fc46b985a1dfee556a56a3","user_name":"tel_1548729013"}]
     */

    private int code;
    private int count;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * course_id : 1548663097936
         * head_photo : head/initial.jpg
         * message_content : JzjxhvxzzzzjnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnrashjkhvjkvhJKDfhjkzhdfjkhjkzfxhb nxbcjvklhdufyusfhjshjksh788174238907r87017309819284hjshdcfjkzbxcjbzxjchjshfjhdsjfhjsdbvxzbnvbjksdhgfgdfhagsdjfgvbnccxbz
         * message_state : 0
         * message_time : 2019-01-29 19:27:43
         * message_title : 天津测试机构
         * message_type : head/3e3b8250809a430f80f4f71a25de7bf0/1548660588227.jpg
         * school_id : 1548643960487
         * school_name : 天津测试机构
         * smessage_id : 115
         * user_id : f34bad4fe0fc46b985a1dfee556a56a3
         * user_name : tel_1548729013
         */

        private String course_id;
        private String head_photo;
        private String message_content;
        private String message_state;
        private String message_time;
        private String message_title;
        private String message_type;
        private String school_id;
        private String school_name;
        private int smessage_id;
        private String user_id;
        private String user_name;

        public String getCourse_id() {
            return course_id == null ? "" : course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public String getHead_photo() {
            return head_photo == null ? "" : head_photo;
        }

        public void setHead_photo(String head_photo) {
            this.head_photo = head_photo;
        }

        public String getMessage_content() {
            return message_content == null ? "" : message_content;
        }

        public void setMessage_content(String message_content) {
            this.message_content = message_content;
        }

        public String getMessage_state() {
            return message_state == null ? "" : message_state;
        }

        public void setMessage_state(String message_state) {
            this.message_state = message_state;
        }

        public String getMessage_time() {
            return message_time == null ? "" : message_time;
        }

        public void setMessage_time(String message_time) {
            this.message_time = message_time;
        }

        public String getMessage_title() {
            return message_title == null ? "" : message_title;
        }

        public void setMessage_title(String message_title) {
            this.message_title = message_title;
        }

        public String getMessage_type() {
            return message_type == null ? "" : message_type;
        }

        public void setMessage_type(String message_type) {
            this.message_type = message_type;
        }

        public String getSchool_id() {
            return school_id == null ? "" : school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getSchool_name() {
            return school_name == null ? "" : school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public int getSmessage_id() {
            return smessage_id;
        }

        public void setSmessage_id(int smessage_id) {
            this.smessage_id = smessage_id;
        }

        public String getUser_id() {
            return user_id == null ? "" : user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name == null ? "" : user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }
}

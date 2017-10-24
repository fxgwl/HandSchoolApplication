package com.example.handschoolapplication.bean;

/**
 * Created by Administrator on 2017/8/17.
 */

public class LearnNewsBean {


    /**
     * message_content : 亲，您报名的学堂开课了，注意不要迟到哦！
     * message_time : 2017-10-21 17:12:38
     * message_title : 嘿嘿
     * message_type : images/head.jpg
     * school_id : 1505805630235
     * school_name : 我的小学堂b
     * smessage_id : 18
     * user_id : 772d42a5a0014faa8706f7cd3f6e8828
     */

    private String message_content;
    private String message_time;
    private String message_title;
    private String message_type;
    private String school_id;
    private String school_name;
    private int smessage_id;
    private String user_id;

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public String getMessage_time() {
        return message_time;
    }

    public void setMessage_time(String message_time) {
        this.message_time = message_time;
    }

    public String getMessage_title() {
        return message_title;
    }

    public void setMessage_title(String message_title) {
        this.message_title = message_title;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getSchool_name() {
        return school_name;
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
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}

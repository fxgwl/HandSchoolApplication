package com.example.handschoolapplication.bean;

/**
 * Created by Administrator on 2017/10/16.
 */

public class NewsListBean {

    /**
     * message_content : 亲，您收到一张优惠券
     * message_date : 2017-10-16 12:00:30
     * message_id : 8
     * message_state : 0
     * message_type : 1
     * user_id : 205c2f34bc064712bf2e0157c142b6dd
     */

    private String message_content;
    private String message_date;
    private int message_id;
    private String message_state;
    private String message_type;
    private String user_id;

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public String getMessage_date() {
        return message_date;
    }

    public void setMessage_date(String message_date) {
        this.message_date = message_date;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public String getMessage_state() {
        return message_state;
    }

    public void setMessage_state(String message_state) {
        this.message_state = message_state;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}

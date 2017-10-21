package com.example.handschoolapplication.bean;

/**
 * Created by Administrator on 2017/9/2.
 */

public class NotificationBean {

    /**
     * coupons_id : 10
     * inform_content : 点击此处领取
     * inform_id : 2
     * inform_name : 亲，点此领取优惠券
     * inform_type : 1
     * message_time : 2017-09-23 11:23:44
     * user_id : 772d42a5a0014faa8706f7cd3f6e8828
     * inform_state : 0
     */

    private int coupons_id;
    private String inform_content;
    private int inform_id;
    private String inform_name;
    private String inform_type;
    private String message_time;
    private String user_id;
    private String inform_state;
    private boolean isGet;

    public int getCoupons_id() {
        return coupons_id;
    }

    public void setCoupons_id(int coupons_id) {
        this.coupons_id = coupons_id;
    }

    public String getInform_content() {
        return inform_content;
    }

    public void setInform_content(String inform_content) {
        this.inform_content = inform_content;
    }

    public int getInform_id() {
        return inform_id;
    }

    public void setInform_id(int inform_id) {
        this.inform_id = inform_id;
    }

    public String getInform_name() {
        return inform_name;
    }

    public void setInform_name(String inform_name) {
        this.inform_name = inform_name;
    }

    public String getInform_type() {
        return inform_type;
    }

    public void setInform_type(String inform_type) {
        this.inform_type = inform_type;
    }

    public String getMessage_time() {
        return message_time;
    }

    public void setMessage_time(String message_time) {
        this.message_time = message_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getInform_state() {
        return inform_state;
    }

    public void setInform_state(String inform_state) {
        this.inform_state = inform_state;
    }

    public boolean isGet() {
        return isGet;
    }

    public void setGet(boolean get) {
        isGet = get;
    }
}

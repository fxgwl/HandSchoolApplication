package com.example.handschoolapplication.bean;

/**
 * Created by Axehome_Mr.z on 2019/5/17 20:19
 * $Describe
 */
public class WeixinBean {

    private String access_token;
    private String openid;
    private int sex;
    private String Nickname;

    public String getAccess_token() {
        return access_token == null ? "" : access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getOpenid() {
        return openid == null ? "" : openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return Nickname == null ? "" : Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }


}

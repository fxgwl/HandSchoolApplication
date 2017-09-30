package com.example.handschoolapplication.utils;

/**
 * Created by Administrator on 2017/9/15.
 */

public class Internet {

    public static final String BASE_URL = "http://axehome.viphk.ngrok.org/PrivateSchool/";

    //注册登录等相关

    //获取注册验证码
    public static final String RE_GETCODE = BASE_URL + "UserInfo/getRegcode";
    //注册
    public static final String REGISTER = BASE_URL + "UserInfo/register";
    //登录
    public static final String LOGIN = BASE_URL + "UserInfo/login";
    //企业资料填写
    public static final String COMMITINFO = BASE_URL + "UserInfo/company";
    //忘记密码获取验证码
    public static final String PWD_GETCODE = BASE_URL+"UserInfo/getPwdcode";
    //忘记密码
    public static final String FORGOTPWD = BASE_URL+"UserInfo/forgetpwd";

}

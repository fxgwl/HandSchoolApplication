package com.example.handschoolapplication.utils;

/**
 * Created by Administrator on 2017/10/16.
 */

public class InternetS {

    public static final String BASE_URL = "http://axehome.viphk.ngrok.org/PrivateSchool/";

//    public static final String BASE_URL = "http://157.10.1.105/PrivateSchool/";

    //掌上私塾接口
    //77阅读
    public static final String READING = BASE_URL+"MessageInfo/updateTeacherInfo";
    //76消息列表
    public static final String NEWSLIST = BASE_URL+"MessageInfo/listall";

    //    74某一课程下的总评价数：
    public static final String TOTALEVANUM = BASE_URL+"InteractMessage/count";
    //73某一课程下的全部评价
    public static final String ALLEVALUATE = BASE_URL+"InteractMessage/listAlls";

    //78学习消息
    public static final String LEARNNEWS = BASE_URL+"StudyMessage/listbyUser";
    //79报名信息
    public static final String APPLYINFOR = BASE_URL+"CourseInfo/listCourseState";
    //80开课
    public static final String BEGINCOURSE = BASE_URL+"CourseInfo/classBegin";
    //81结束课程
    public static final String ENDCOURSE = BASE_URL+"CourseInfo/classOver";
    //82账户信息
    public static final String ACCOUNTINFO = BASE_URL+"AccountInfo/getUserAccountInfo";
    //83提现

    //84收入/提现记录
    public static final String ACCOUNTLIST = BASE_URL+"AccountRecord/getUserRecord";

}

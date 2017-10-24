package com.example.handschoolapplication.utils;

/**
 * Created by Administrator on 2017/10/16.
 */

public class InternetS {

//    public static final String BASE_URL = "http://axehome.viphk.ngrok.org/PrivateSchool/";


    public static final String BASE_URL = "http://120.92.44.55/PrivateSchool/";

    //掌上私塾接口
    //77阅读
    public static final String READING = BASE_URL + "MessageInfo/updateTeacherInfo";
    //76消息列表
    public static final String NEWSLIST = BASE_URL + "MessageInfo/listall";

    //    74某一课程下的总评价数：
    public static final String TOTALEVANUM = BASE_URL + "InteractMessage/count";
    //73某一课程下的全部评价
    public static final String ALLEVALUATE = BASE_URL + "InteractMessage/listAlls";

    //78学习消息
    public static final String LEARNNEWS = BASE_URL + "StudyMessage/listbyUser";
    //79报名信息
    public static final String APPLYINFOR = BASE_URL + "CourseInfo/listCourseState";
    //80开课
    public static final String BEGINCOURSE = BASE_URL + "CourseInfo/classBegin";
    //81结束课程
    public static final String ENDCOURSE = BASE_URL + "CourseInfo/classOver";
    //82账户信息
    public static final String ACCOUNTINFO = BASE_URL + "AccountInfo/getUserAccountInfo";
    //83提现

    //84收入/提现记录
    public static final String ACCOUNTLIST = BASE_URL + "AccountRecord/getUserRecord";
    //85课程排序
    public static final String COURSE_RANK = BASE_URL + "CourseInfo/listCourseInfo";
    //86机构排序
    public static final String ORGANIZATION_RANK = BASE_URL + "UserInfo/findjigou";
    //87等级推荐  星级排行
    public static final String GRADE_RANK = BASE_URL + "UserInfo/findSchooldengji";
    //88人气排行
    public static final String POPULIRATION_RANK = BASE_URL + "UserInfo/findSchoolrenqi";
    //89价格排序：由高到低
    public static final String PRICE_UP_RANK = BASE_URL + "CourseInfo/listBymoney";
    //90领取优惠券
    public static final String GET_DISCOUNT_PAPER = BASE_URL + "CouponsInfo/receiveCoupons";
    //91优惠券列表
    public static final String DISCOUNT_PAPER_LIST = BASE_URL + "CouponsInfo/listByuser";
    //92热门推荐
    public static final String HOT_RECOMMEND = BASE_URL + "CourseInfo/listHotClass";
    //93判断签到
    public static final String IS_SIGN = BASE_URL + "SignInfo/IsSign";
    //94学堂评价管理
    public static final String CLASS_EVALUATE_MANAGER = BASE_URL + "InteractMessage/getBySchoolInfo";
    //95评价详情信息
    public static final String EVALUATION_INFORMINAGER = BASE_URL + "InteractMessage/getBySchoolInfo";
    //96通知消息详情信息
    public static final String NOTIFATION_NEWS_INFORMINAGER = BASE_URL + "InformMessage/getByUserInformMessage";
    //97学堂订单详情信息
    public static final String CLASS_ORDER_INFOR = BASE_URL + "OrderInfo/getSchoolOrder";
    //98热门地址信息
    public static final String HOT_ADDRESS_INFOR = BASE_URL + "AddressInfo/listAddressInfo";
    //99用户评价管理
    public static final String USER_EVALUATE_MANAGER = BASE_URL + "InteractMessage/getByUser";


}

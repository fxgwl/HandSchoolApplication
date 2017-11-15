package com.example.handschoolapplication.utils;

/**
 * Created by Administrator on 2017/9/15.
 */

public class Internet {

//    public static final String BASE_URL = "http://axehome.viphk.ngrok.org/PrivateSchool/";
    public static final String BASE_URL = "http://120.92.44.55/PrivateSchool/";

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
    public static final String PWD_GETCODE = BASE_URL + "UserInfo/getPwdcode";
    //忘记密码
    public static final String FORGOTPWD = BASE_URL + "UserInfo/forgetpwd";

    //----------------三方登录----------------------
    public static final String WECHAT_LOGIN = BASE_URL + "UserInfo/weixin";
    public static final String SINA_WEIBO_LOGIN = BASE_URL + "UserInfo/weibo";
    public static final String QQ_LOGIN = BASE_URL + "UserInfo/qq";
    //三房登录获取验证码
    public static final String GET_CODE_FOR_THREE = BASE_URL + "UserInfo/getThreecode";


    //========
    //更换头像
    public static final String CHANGEHEAD = BASE_URL + "UserInfo/headphoto";
    //更换真实姓名
    public static final String REALNAME = BASE_URL + "UserInfo/realname";

    //更换身份证号
    public static final String USERIDCARD = BASE_URL + "UserInfo/usercard";
    //个人信息
    public static final String USERINFO = BASE_URL + "UserInfo/getoneUser";
    //添加地址
    public static final String ADDADDRESS = BASE_URL + "SchoolAddress/insertSchoolAddress";
    //获取地址列表
    public static final String ADDRESSLIST = BASE_URL + "SchoolAddress/listmyAddress";
    //删除地址列表
    public static final String DELETEADDRESS = BASE_URL + "SchoolAddress/deleteSchoolAddress";
    //修改手机号获取验证码
    public static final String CHANGEPHONECODE = BASE_URL + "UserInfo/getcode";
    //修改手机号
    public static final String CHANGEPHONE = BASE_URL + "UserInfo/phonenum";
    //提交意见反馈：
    public static final String BACKINFO = BASE_URL + "FeedbackInfo/insertFeedbackInfo";
    //搜索热点问题：
    public static final String INFOSEARCH = BASE_URL + "HelpInfo/search";
    //热点问题列表
    public static final String ALLINFO = BASE_URL + "HelpInfo/listHelpInfo";
    //热点问题详情
    public static final String INFODETAIL = BASE_URL + "HelpInfo/getHelpInfo";
    //联系客服：
    public static final String CONTACTSERVICE = BASE_URL + "ArtificialMessage/insertArtificialMessage";
    //客服对话列表
    public static final String SERVICELIST = BASE_URL + "ArtificialMessage/listByuser";
    //教育资讯列表：
    public static final String TEACHNEWS = BASE_URL + "NewsInfo/listNewsInfo";
    //首页广告位：
    public static final String HOMEAD = BASE_URL + "AdvertisingInfo/listAdvertisingInfo";
    //学堂简介 环境：
    public static final String SCHOOLINTO = BASE_URL + "UserInfo/selectSchool";
    //师资
    public static final String SHIZI = BASE_URL + "TeacherInfo/listbySchool";
    //课程
    public static final String COURSEINFO = BASE_URL + "CourseInfo/listbySchool";
    //课程类型选择
    public static final String COURSESTATE = BASE_URL + "CourseInfo/listbyState";
    //收藏/取消收藏 学堂：
    public static final String SAVECLASS = BASE_URL + "CollectSchool/insertCollectSchool";
    //课程类型：
    public static final String CLASSTYPE = BASE_URL + "TypeOneInfo/listOneTwo";
    //机构列表：
    public static final String ORGANLIST = BASE_URL + "UserInfo/selectUser";
    //课程列表
    public static final String COURSELIST = BASE_URL + "CourseInfo/listbyType";
    //课程主页
    public static final String COURSEHOMEPAGE = BASE_URL + "CourseInfo/getCourseInfo";
    //课程时间
    public static final String COURSETIME = BASE_URL + "CourseTimeInfo/getByCourseId";
    //课程费用
    public static final String COURSEMONEY = BASE_URL + "CoursePriceInfo/getByCourseId";
    //收藏/取消收藏 课程
    public static final String SAVECOURSE = BASE_URL + "CollectInfo/insertCollectInfo";
    //课程收藏列表
    public static final String SAVECOURSELIST = BASE_URL + "CollectInfo/getmyCollect";
    //课程收藏删除
    public static final String DELETECOLLECT = BASE_URL + "CollectInfo/deleteCollectInfo";
    //学堂收藏列表
    public static final String SCHOOLCOLLECT = BASE_URL + "CollectSchool/getmySchool";
    //学堂收藏删除
    public static final String DELETESCHOOLCOLLECT = BASE_URL + "CollectSchool/deleteCollectSchool";
    //课程(模糊)搜索
    public static final String COURSESEARCH = BASE_URL + "CourseInfo/listbyname";
    //学堂(模糊)搜索
    public static final String SCHOOLSEARCH = BASE_URL + "UserInfo/selectschool";
    //签到
    public static final String SIGN = BASE_URL + "SignInfo/insertSignInfo";
    //足迹列表
    public static final String FOOTLIST = BASE_URL + "FootprintInfo/listFootprint";
    //积分记录
    public static final String INTEGRALRECORD = BASE_URL + "IntegralInfo/listIntegral";
    //首页联系客服
    public static final String HOMECONTACT = BASE_URL + "ConsultMessage/insertConsultMessage";
    //对话列表
    public static final String CONTACTLIST = BASE_URL + "ConsultMessage/getConsult";
    //立即报名/加入购物车
    public static final String SINGUP = BASE_URL + "OrderInfo/insertOrderInfo";
    //购物车--根据学堂分类
    public static final String SHOPCAR = BASE_URL + "OrderInfo/listBySchool";
    //购物车--改变数量
    public static final String CHANGENUM = BASE_URL + "OrderInfo/updateOrderInfo";
    //订单详情
    public static final String ORDER_INFO = BASE_URL + "OrderInfo/getOrderInfo";
    //订单中心==全部
    public static final String ALLORDER = BASE_URL + "OrderInfo/getMyall";

    //不同状态订单
    public static final String ORDERSTATE = BASE_URL + "OrderInfo/getUserOrder";
    //取消订单
    public static final String CLOSEORDER = BASE_URL + "OrderInfo/closeOrder";
    //退款
    public static final String RETURNMONEY = BASE_URL + "OrderInfo/refundment";
    //优惠券
    public static final String DISCOUNT = BASE_URL + "OrderInfo/getOrderCoupons";
    //金币信息
    public static final String GOLDDISCOUNT = BASE_URL + "OrderInfo/ins";
    //发表评价
    public static final String COMMENT = BASE_URL + "InteractMessage/insertInteractMessage";
    //评价回复
    public static final String REPLAYCOMMENT = BASE_URL + "ReplyInfo/insertReplyInfo";
    //评价详情
    public static final String COMMENTDETAIL = BASE_URL + "InteractMessage/getInteractInfo";
    //优惠券列表
    public static final String DISCOUNTLIST = BASE_URL + "UserCoupons/getUCoupons";
    //评价管理:已评价
    public static final String HASCOMMENT = BASE_URL + "InteractMessage/getByUser";
    //个人对话列表
    public static final String PERSONDIALOG = BASE_URL + "ConsultMessage/getforMe";


    //--------------------昝宏伟---------------------->
    //根据一级类型名称查二级类型
    public static final String GET_SECOND = BASE_URL + "TypeTwoInfo/listTypeOne";
    //根据二级类型名称查三级类型
    public static final String GET_THIRD = BASE_URL + "TypeThreeInfo/listTypeOne";


    //---------------------------------------cc
    //报名信息详情
    public static final String BMINFO = BASE_URL + "ClassSign/listsign";
    //发消息单发
    public static final String SINGLENEWS = BASE_URL + "ClassSign/single";
    //发消息群发
    public static final String ALLNEWS = BASE_URL + "ClassSign/mass";

    //支付
    //支付宝支付
    public static final String ALIPAY = BASE_URL + "alipay/trade/create";
    //微信支付
    public static final String WECHATPAY = BASE_URL + "wechat/weiBuy";
}

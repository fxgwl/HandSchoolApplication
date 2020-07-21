package com.example.handschoolapplication.utils;

/**
 * Created by Administrator on 2017/9/15.
 */

public class Internet {

        public static final String BASE_URL = "http://m.xczsss.com/PrivateSchool/";//正式域名
//    public static final String BASE_URL = "http://47.93.31.94/PrivateSchool/";//测试域名

    //public static final String BASE_URL = "http://157.10.1.117/PrivateSchool/";//本地服务器

    //注册登录等相关

    //获取注册验证码
    public static final String RE_GETCODE = BASE_URL + "UserInfo/getRegcode";
    //注册
    public static final String REGISTER = BASE_URL + "UserInfo/register";
    //登录
    public static final String LOGIN = BASE_URL + "UserInfo/login";
    //企业资料填写
    public static final String COMMITINFO = BASE_URL + "UserInfo/company";
    //驳回企业资料填写
    public static final String COMMITINFOS = BASE_URL + "UserInfo/companys";
    //忘记密码获取验证码
    public static final String PWD_GETCODE = BASE_URL + "UserInfo/getPwdcode";
    //忘记密码
    public static final String FORGOTPWD = BASE_URL + "UserInfo/forgetpwd";

    //----------------三方登录----------------------
    public static final String WECHAT_LOGIN = BASE_URL + "UserInfo/weixin";
    public static final String SINA_WEIBO_LOGIN = BASE_URL + "UserInfo/weibo";
    public static final String QQ_LOGIN = BASE_URL + "UserInfo/qq";
    //三方登录获取验证码
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
    //修改机构名称
    public static final String modifyCompanyName = BASE_URL + "UserInfo/modifyCompanyName";
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
    public static final String HOMEADs = BASE_URL + "AdvertisingInfo/listType";
    //机构简介 环境：
    public static final String SCHOOLINTO = BASE_URL + "UserInfo/selectSchool";
    //师资
    public static final String SHIZI = BASE_URL + "TeacherInfo/listbySchool";
    //课程
    public static final String COURSEINFO = BASE_URL + "CourseInfo/listbySchool";
    //课程类型选择
    public static final String COURSESTATE = BASE_URL + "CourseInfo/listState";
    //收藏/取消收藏 机构：
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
    //机构收藏列表
    public static final String SCHOOLCOLLECT = BASE_URL + "CollectSchool/getmySchool";
    //机构收藏删除
    public static final String DELETESCHOOLCOLLECT = BASE_URL + "CollectSchool/deleteCollectSchool";
    //课程(模糊)搜索
    public static final String COURSESEARCH = BASE_URL + "CourseInfo/listbyname";
    //机构(模糊)搜索
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
    //购物车--根据机构分类
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
    public static final String COMMENT = BASE_URL + "InteractMessage/save";
    //评价回复
    public static final String REPLAYCOMMENT = BASE_URL + "ReplyInfo/save";
    //评价详情
    public static final String COMMENTDETAIL = BASE_URL + "InteractMessage/getInteractInfo";
    //优惠券列表
    public static final String DISCOUNTLIST = BASE_URL + "UserCoupons/getUCoupons";
    //评价管理:已评价
    public static final String HASCOMMENT = BASE_URL + "InteractMessage/getByUser";
    public static final String HASCOMMENT_s = BASE_URL + "InteractMessage/listInteract";

    public static final String INTERACT_MESSAGE_SCHOOL = BASE_URL + "InteractMessage/listSchoolInteract";
    //个人对话列表
    public static final String PERSONDIALOG = BASE_URL + "ConsultMessage/getforMe";


    //--------------------昝宏伟---------------------->
    //根据一级类型名称查二级类型
    public static final String GET_SECOND = BASE_URL + "TypeTwoInfo/listTypeOne";
    //根据二级类型名称查三级类型
    public static final String GET_THIRD = BASE_URL + "TypeThreeInfo/listTypeOne";


    //---------------------------------------cc-----------------------
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
    //线下支付
    public static final String PAY_CASH = BASE_URL + "OrderInfo/offline";

    //提现
    //支付宝提现
    public static final String ALIPAY_CASH = BASE_URL + "AccountCash/getMoneyInfo";


    //机构类别
    //添加机构类别
    public static final String ADD_CLASS_TYPE = BASE_URL + "UserInfo/addSchoolType";
    //修改机构类别
    public static final String CHANGE_CLASS_TYPE = BASE_URL + "UserInfo/changeSchoolType";
    //删除机构类别
    public static final String DELECT_CLASS_TYPE = BASE_URL + "UserInfo/delSchoolType";

    //修改身份证信息
    public static final String CHANGE_IDENTITY = BASE_URL + "UserInfo/changephoto";
    //修改资质信息
    public static final String CHANGE_QUALIFICATION = BASE_URL + "UserInfo/changephoto";

    //删除学习计划
    public static final String DELECT_DELECT_PLAN = BASE_URL + "OrderInfo/deleteOrderInfo";

    //消息未读个数
    public static final String UNREAD_NEWS_NUM = BASE_URL + "MessageInfo/information";

    //学习消息阅读接口
    public static final String READ_NEWS = BASE_URL + "StudyMessage/byid";

    public static final String READ_EDU = BASE_URL + "NewsInfo/getNewsInfo";

    //学堂粉丝接口
    public static final String GET_SCHOOL_FAN = BASE_URL + "CollectSchool/getCollector";
    //修改学堂地址接口
    public static final String CHANGE_SCHOOL_ADDRESS = BASE_URL + "SchoolAddress/updateSchoolAddress";
    //根据学堂Id查询第一个课程的Id
    public static final String GET_COURSE_ID = BASE_URL + "CourseInfo/getCurriculum";

    //刷新通知消息的未读状态
    public static final String UPDATE_NOTIFICATION = BASE_URL + "InformMessage/alterInformMessage";

    //学习码页面确认按钮
    public static final String AFFIRM_ORDER = BASE_URL + "OrderInfo/getOrder";
    //课程主页店铺优惠券列表
    public static final String DISCOUNT_PAGE_LIST = BASE_URL + "CouponsInfo/school";

    //资料审核失败的重新填写的接口
    public static final String EDIT_AND_ADD = BASE_URL + "UserInfo/byUser";
    //学堂扫描二维码获取课程信息
    public static final String SCAN_RESULT = BASE_URL + "OrderInfo/getOrderDetails";
    //添加学生姓名
    public static final String STUDENT_NAME = BASE_URL + "OrderInfo/saveStudent";
    //根据省市区获取小学列表
    public static final String GET_SCHOOL_BY_ADDRESS = BASE_URL + "gakuen/vague";
    //获取该机构添加的小学列表
    public static final String GET_SCHOOL_BY_CLASS = BASE_URL + "bind/list";
    //添加该机构附近的小学
    public static final String ADD_SCHOOL_NEAR_CLASS = BASE_URL + "bind/save";
    //删除该机构附近的小学
    public static final String DELECT_CLASS_SCHOOL = BASE_URL + "bind/delReplace";
    //根据小学查询所在地区的机构
    public static final String GET_CLASS_LSIT_BY_SCHOOL = BASE_URL + "bind/gather";
    //获取六大类广告
    public static final String GET_BANNER_ADVERTISING = BASE_URL + "AdvertisingInfo/listType";
    //新加的评论接口
    public static final String COMMENT_NEW = BASE_URL + "criticOthers/save";
    //新加的评论接口列表
    public static final String COMMENT_NEW_LIST = BASE_URL + "criticOthers/listByOthers";
    //新加的机构的课程
    public static final String NEWS_COM_COURSE = BASE_URL + "CourseInfo/getCurriculum";
    //新加的课程咨询消息
    public static final String COURSE_NEWS = BASE_URL + "StudyMessage/queryList";
    //新加的提现手续费接口
    public static final String CASH_TIP = BASE_URL + "charge/detail";

}

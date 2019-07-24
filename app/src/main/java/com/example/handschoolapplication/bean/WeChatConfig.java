package com.example.handschoolapplication.bean;

/**
 * Created by Axehome_Mr.z on 2019/5/17 18:43
 * $Describe
 */
public class WeChatConfig {
    /**
     * 商户id
     */
    public static String MCH_ID = "11111"; // 商户号
    /**
     * 应用id，appId
     */
    public static String APP_ID = "wx433e119bb99f2075"; // 微信开发平台应用id
    /**
     * APP_SECRET
     */
    public static String APP_SECRET = "a107e832346dc96757f73c850d1b1be1"; // 应用对应的凭证
    /**
     * 秘钥
     */
    public static String APP_KEY = "111111"; // 商户号对应的密钥
    /**
     * 扩展字段
     */
    public static String PACKAGE = "Sign=WXPay"; // 扩展字段 暂填写固定值Sign=WXPay
    /**
     * 设备号
     */
    public static String DEVICE_INFO = "WEB";// 设备号 终端设备号(门店号或收银设备ID)，默认请传"WEB"
    /**
     * 通知回调接口路径
     */
    public static String NOTIFY_URL = "https://WeChatPlaceOrder";
    /**
     * 钞票类型 人民币
     */
    public static String FEE_TYPE = "CNY";
    /**
     * 交易类型
     */
    public static String TRADE_TYPE = "APP";//交易类型
    /**
     * 固定的，统一下单后调用的生成预付单接口
     */
    public static String PAY_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 企业付款接口
     */
    public static String TRANSFERS_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

}

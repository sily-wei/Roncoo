package com.roncoo.common.config;

/**
 *@Author feri
 *@Date Created in 2018/8/9 22:58
 */
public class WeChatPayConfig {
    public static String APP_ID = "wx632c8f211f8122c6";
    public static String MCH_ID = "1497984412";
    public static String API_KEY = "sbNCm1JnevqI36LrEaxFwcaT0hkGxFnC";
    public static String UFDOOER_URL ="https://api.mch.weixin.qq.com/pay/unifiedorder";//下单接口
    public static String QUERY_URL ="https://api.mch.weixin.qq.com/pay/orderquery";//查询接口
    public static String CANCEL_URL ="https://api.mch.weixin.qq.com/pay/closeorder";//取消接口

    public static String NOTIFY_URL = "http://localhost:8080/payment/result";
    public static String CREATE_IP = "114.242.26.51";

}

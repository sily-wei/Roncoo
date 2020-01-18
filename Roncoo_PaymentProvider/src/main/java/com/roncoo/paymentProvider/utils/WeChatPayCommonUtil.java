package com.roncoo.paymentProvider.utils;


import com.roncoo.common.config.WeChatPayConfig;

import java.net.Inet4Address;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *@Author feri
 *@Date Created in 2018/8/9 23:12
 */
public class WeChatPayCommonUtil {
    /**
     * @return boolean
     */
    public static boolean isTenpaySign(String characterEncoding, SortedMap<Object,
                    Object> packageParams, String API_KEY) {
        StringBuffer sb=new StringBuffer();
        Set es= packageParams.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if(!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + API_KEY);
        String mysign = MD5Util.MD5Encode(sb.toString(),
                characterEncoding).toLowerCase();
        String tenpaySign = ((String)packageParams.get("sign")).toLowerCase();
        return tenpaySign.equals(mysign);
    }
    /**
     * @author
     * @Description sign
     * @param characterEncoding
     * @return
     */
    public static String createSign(String characterEncoding, SortedMap<Object,
            Object> packageParams, String API_KEY) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) &&
                    !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + API_KEY);
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }
    /**
     * @Description 封装请求参数为xml格式的字符串
     * @return
     */
    public static String getRequestXml(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("detail".equalsIgnoreCase(k) ) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            } }
        sb.append("</xml>");
        return sb.toString();
    }
    /*获取指定大小的正整数*/
    public static int buildRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    public static String getCurrTime() {
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = outFormat.format(now);
        return s;
    }
    //生成支付数据
    /**
     * 创建预支付链接 返回待支付链接
     * @param order_price 价格 单位 分
     * @param  body 商品描述
     * @param  out_trade_no 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一*/
    public static String weixin_pay(String order_price,String body,String
            out_trade_no) throws Exception {
        String ip= Inet4Address.getLocalHost().getHostAddress();
        String appid = WeChatPayConfig.APP_ID; // appid
        String key = WeChatPayConfig.API_KEY; // key
        String currTime = WeChatPayCommonUtil.getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = WeChatPayCommonUtil.buildRandom(4) + "";
        String nonce_str = strTime + strRandom;
        String trade_type = "NATIVE";//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
        //MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", WeChatPayConfig.MCH_ID);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", order_price);
        packageParams.put("spbill_create_ip", ip);
        packageParams.put("notify_url", "http://"+ip+":8080/paycallback");
        packageParams.put("trade_type", trade_type);
        String sign = WeChatPayCommonUtil.createSign("UTF-8", packageParams, key);
        packageParams.put("sign", sign);
        String requestXML = WeChatPayCommonUtil.getRequestXml(packageParams);
        String resXml = HttpUtil.postData(WeChatPayConfig.UFDOOER_URL, requestXML);
        Map map = XmlUtil.doXMLParse(resXml);
        if(map.containsKey("code_url")){
            return (String) map.get("code_url");
        }else {
            return null;
        }

    }

    /**
     * 进行微信支付订单的查询
     * @param oid  订单编号
     */
    public static String weixin_query(String oid){
        String currTime = getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = WeChatPayCommonUtil.buildRandom(4) + "";
        String nonce_str = strTime + strRandom;
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        packageParams.put("appid", WeChatPayConfig.APP_ID);
        packageParams.put("mch_id", WeChatPayConfig.MCH_ID);
        packageParams.put("out_trade_no", oid);
        packageParams.put("nonce_str", nonce_str);
        String sign = WeChatPayCommonUtil.createSign("UTF-8", packageParams, WeChatPayConfig.API_KEY);
        packageParams.put("sign", sign);
        String requestXML = WeChatPayCommonUtil.getRequestXml(packageParams);
        String resXml = HttpUtil.postData(WeChatPayConfig.QUERY_URL, requestXML);
        return resXml;
    }



    /**
     * 取消订单，取消未支付的订单*/
    public static String weixin_cancel(String oid){
        String currTime = getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = WeChatPayCommonUtil.buildRandom(4) + "";
        String nonce_str = strTime + strRandom;
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        packageParams.put("appid", WeChatPayConfig.APP_ID);
        packageParams.put("mch_id", WeChatPayConfig.MCH_ID);
        packageParams.put("out_trade_no", oid);
        packageParams.put("nonce_str", nonce_str);
        String sign = WeChatPayCommonUtil.createSign("UTF-8", packageParams, WeChatPayConfig.API_KEY);
        packageParams.put("sign", sign);
        String requestXML = WeChatPayCommonUtil.getRequestXml(packageParams);
        String resXml = HttpUtil.postData(WeChatPayConfig.CANCEL_URL, requestXML);
        return resXml;
    }
}

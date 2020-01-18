package com.roncoo.paymentProvider.utils;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.roncoo.paymentProvider.bean.AlipayBean;
import com.roncoo.paymentProvider.bean.AlipayRefoundBean;

/**
 * @author weining
 * @date 2020/1/15 19:37
 */
public class AlipayUtil {
    /**
     * URL 支付宝网关（固定）  https://openapi.alipay.com/gateway.do
     * APPID  APPID 即创建应用后生成  获取见上方创建应用
     * APP_PRIVATE_KEY  开发者私钥，由开发者自己生成 获取见配置密钥
     * FORMAT 参数返回格式，只支持 json  json（固定）
     * CHARSET 编码集，支持 GBK/UTF-8 开发者根据实际工程编码配置
     * ALIPAY_PUBLIC_KEY  支付宝公钥，由支付宝生成 获取详见配置密钥
     * SIGN_TYPE 商户生成签名字符串所使用的签名算法类型，目前支持 RSA2 和 RSA，推荐使用 RSA2
     */
    public static AlipayClient alipayClient;
    public static String URL = "https://openapi.alipay.com/gateway.do";
    public static String APP_ID = "2017091608770636";
    public static String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCh1qI8uo1qhrcePsa5JUAoYUX8HfPuBt7kc90aCP1 M/v61/uzaU/lyGQeChKV3jdDTn2Lcq6kT5JBl3TLiaYHmO6cId1nQAIUxiT9zhB9crc4wAx8CVabMbcqUefs7Xsp+Yh hUgU5X6GOS3emkUeL7RegBnL8vayfEBeUDgBxsk/K/VygBA8sapsEhnoOrB6bhMY4GaJrxb0kg9Ej8x4kpExLcxkT+U gcOiJvh6vpBZo5CJsiPQkFvSsNsWY2uSDudSL/KqpMxz+yPfVvZDt4fOfyi+CfYR43Jlo4tsT7joqH2JT06BH+KdJyc 1D3Lqw7w/WdmZtmoLghH0kRZawrLAgMBAAECggEAYYtpm+rhQ7zQ8HTr+DogknYW5Z/0H5qai93d/Uw/yEHFqlJt1iZ ZKlE1upBS311l6beesdzxeuD/u7X4bokjV27K/YpaYsl9fl74FJslAApuRXgMH68aawsd2CIxsBYxPL3JZl3Np6SVJ7 eDlJwakFMRRK+CeIVAoaDf6R01hKctkYnnE0wT+ffQNKWsISoEyiKVT3g5fur7iPOuDlDXsfi6Mm+e75wCXTmRRHmb8 lPBAMLV+Kj5DFxg8dwNz81Fs4ZM2Aq0lBaTfy1H1zSlM1m42wcsMYDcgdEH9aq+OgqK+cny6umgs7/Alg7IgV/9b7Ah KdvAqLy2ERUJtooj2QKBgQDeIoDW3HuTq7sBaBnu63f7icT2RM3fApfOiGM4UDtxPvc5dS5S//o3E8p+rbp21FfBeyL OJFd9dg/eu+ETA+63QMPw4Kq4AH/EA5AFohaOQ0IKFDjYyxfyD8ajA4USDwdiaW2/vmMeAtGSv+W5zWb9/t49LOTwzE W904+yOGcmhQKBgQC6guDZ0Ob4o9nx5XwZXEe2di4MupARHceGzmolyDvs3Qi/w+8QntrDvfqIJoqoxOG5NVi3jtjkq tJtMaPyxqNWTabWOOTLbrsqlvPUmeCl0j3FVFKAGcV7/b9XkLvh1DtnIe6rhhZCVB4e4bL/katpOTgulhmSMaWIaztG U0F1DwKBgQCTeobdn/6vuSlsMqhdFppPN1W8R0wDjt4o8iYlwibk9e//hswdsPN307zyQ/dzY2FsBIvEHx6zHkpFD6n MDSVVJzuv1gmiJjqtccwR4V5mT0MuG+TuElCwlkbD/ddAeRfm/6Ys0oNN7oMjkiI8LKH/alI0fXT2Zji7YhWaNpZNXQ KBgEU6q0duWS1VdGJrcgLf0+aQO0uSPEN+MD+Dgrb/ee7TpJm5mpUqwb0CWWoMFE/MtJRQjtujdDJ8jZrmYBqPTLWOI S1G9PXl5idK3Lq/Wzlxrmf+gpj19+2sJEfWe0a5xkrjt3mHTd/U5VFFKXHfmiZ2jLoOEPPI5c6bLudNo/BVAoGBAMvw RxLO4xb11Ip4rnEHkw3Qn8lrddoC3/m7haHYZ5DyGe8wdCdEi6wyk5MvlNQdqdVg5bqV0AiotIBcd5Pemabun2WaB11 h/6SSb6wKY4Fnz+H155zaEww4no9BTG9llqQV7H8AS77dN1bxhcpE/MGFoB9JFU0D+BwXAnth4z1u ";
    public static String FORMAT = "json";
    public static String CHARSET = "UTF-8";
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx7jJT+PSEM6ZiimTW0SGUfg4cJU04H/mQqkL2mk7KaHXFQq Mh4US6xYkDlaEXzOOfxevuBqWOaB4/8TleO1CHZHXWHu9Xc+iYtJPNJGrxoGLM+6Cg9IafJTygRoaqdH0SoVMpxFdOp UftNdXHO+G0ZpS/7c1zpn8G64zN5J17IFrLcUlsEnSgOrJxsS2Q50b44er0KQlj76pehB2sTveHS2vdhqXzrv+oq99X tUKEY1a3nwDjXneI7YYKLHD9KU53pti/ibLDkOEjO4+DRowd+wfSwkmWGVL3X320mvCfrg/aMN71B/cyyhW0mQ4cxqh 2UcnpxLm0v/+uC7dSCyAJwIDAQAB\n";
    public static String SIGN_TYPE = "RSA2";

    static {
        alipayClient = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
    }

    //创建预支付链接
    public static String createAliPay(AlipayBean alipayBean) {
        //1.创建请求对象
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        //2.设置传输的内容
        request.setBizContent(JSON.toJSONString(alipayBean));
        //3.执行请求，并获取详情对象
        try {
            AlipayTradePrecreateResponse response = alipayClient.execute(request);
            //4.校验是否正确
            if (response.isSuccess()) {
                System.out.println("调用成功");
                return response.getQrCode();
            } else {
                System.out.println("调用失败");
            }
        } catch (AlipayApiException e) {
            System.out.println(e.getErrMsg());
        }
        return null;
    }

    //创建统一收单 交易查询
    public static String queryAlipay(String oid) {
        //1.创建请求对象
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        //2.设置传输的内容
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + oid + "\"}");
        //3.执行请求
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
            //4.校验是否正确
            if (response.isSuccess()) {
                System.out.println("调用成功");
                return response.getTradeStatus();
            } else {
                System.out.println("调用失败");
            }
        } catch (AlipayApiException e) {
            System.out.println(e.getErrMsg());
        }
        return null;
    }

    //取消订单
    public static String cancelPay(String oid){
        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
        request.setBizContent("{\"out_trade_no\":\""+oid+"\"}");
        try {
            AlipayTradeCancelResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                return response.getAction();
            } else {
            }
        }catch (AlipayApiException ex){
            System.err.println(ex.getErrMsg());
        }
        return null;
    }
    //退款订单
    public static String refoundPay(AlipayRefoundBean bean){
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest ();
        request.setBizContent(JSON.toJSONString(bean));
        try {
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                return response.getCode();
            } else {
            }
        }catch (AlipayApiException ex){
            System.err.println(ex.getErrMsg());
        }
        return null;
    }

    //查询退款进度
    public static String queryRefoundPay(String oid, String out_request_no) {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + oid + "\"," +
                "\"out_request_no\":\"" + out_request_no + "\"" +
                "  }");
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                return response.getRefundFee();
            } else {
                System.out.println("调用失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }


}

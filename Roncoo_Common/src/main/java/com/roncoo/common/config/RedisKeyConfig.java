package com.roncoo.common.config;

/**
 * @author weining
 * @date 2019/12/30 20:29
 */
public class RedisKeyConfig {
    //记录注册码
    public static final String RCODE_KEY = "sms:rcode";
    public static final String FCODE_KEY = "sms:fcode";

    //记录登录相关内容
    public static final String TOKEN_KEY="login:token:";//后面追加令牌 存储的值：用户的id
    //记录用户对应的令牌
    public static final String USER_TOKEN="login:user:";//后面追加uid  List类型 值：令牌
    //令牌有效期 默认值
    public static final int TOKEN_TIME=1800;//30分钟
    //挤掉的令牌
    public static final String TOKEN_TKCK="login:kick";//Hash类型 字段 令牌 值：被挤掉的设备信息
    //冻结 冻结是账户 ：无论手机号还是邮箱 失败超过3次 密码不正确 默认是1小时
    public static final String TOKEN_FROST="login:frost:";//后面追加uid
    //失败记录
    public static final String LOGIN_ERROR="login:error:";//后面追加uid  +:+时间戳
    //账号冻结 默认1小时
    public static final int TOKENFORST_TIME=3600;//60分钟

    //记录点赞信息的key Hash
    public static final String LIKE_HASH="like:babystudy";//永久有效


}

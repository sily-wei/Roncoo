package com.roncoo.common.config;

/**
 * @author weining
 * @date 2019/12/30 20:28
 */
public class SystemConfig {
    //秘钥 AES
    public static final String PASS_KEY = "alTdOAhHXtwagZlhmttz1A==";

    //验证码的有效期 默认10分钟
    public static final int CODE_TIME=600;

    public static final String TOKEN_HEAD="utoken";

    //每种等级的积分
    public static final int LEVEL_1=1000;
    public static final int LEVEL_2=10000;
    public static final int LEVEL_3=100000;
}

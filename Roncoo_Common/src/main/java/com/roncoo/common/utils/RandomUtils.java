package com.roncoo.common.utils;

import java.util.Random;

/**
 * @author weining
 * @date 2020/1/16 20:26
 */
public class RandomUtils {
    /**
     * 随机生成指定格式的数字
     * @param len 随机数字的个数*/
    public static int createNum(int len){
        Random random=new Random();
        //4  1000-9999   0-8999 +1000
        //5 10000-99999
        //[0,num)
        int max=(int)(Math.pow(10,len)-Math.pow(10,len-1));
        return random.nextInt(max)+(int)Math.pow(10,len-1);
    }
}

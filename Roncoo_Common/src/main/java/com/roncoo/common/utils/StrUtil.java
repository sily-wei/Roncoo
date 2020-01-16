package com.roncoo.common.utils;

/**
 * @author weining
 * @date 2019/12/30 21:43
 */

/**
 * 检验字符串是否为空
 */
public class StrUtil {
    public static boolean isEmpty(String... strs) {
        for (String s : strs) {
            if (s == null || s.length() == 0) {
                return true;
            }
        }
        return false;
    }
}

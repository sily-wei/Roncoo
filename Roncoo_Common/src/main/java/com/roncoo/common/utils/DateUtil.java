package com.roncoo.common.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author weining
 * @date 2019/12/12 19:55
 */
public class DateUtil {
    /**
     * 添加过期时间
     * @param y 要保存多少年
     * @return 返回保存后的时间
     */
    public static Date addYear(int y){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,y);
        return calendar.getTime();
    }

    //获取指定年 的今天
    public static Date addDays(int days){
        //日历 类 完成实例化
        Calendar calendar=Calendar.getInstance();
        //计算日期
        calendar.add(Calendar.DAY_OF_MONTH,days);
        return calendar.getTime();
    }
    //获取今日的日期 date
    public static Date getDate(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
    public static Date getDate(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }
}

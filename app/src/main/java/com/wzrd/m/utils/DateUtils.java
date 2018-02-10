package com.wzrd.m.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2017/1/4.
 */
public class DateUtils {
    /**获取当前时间毫秒值
     * @return
     */
    public static Long getCurrentTime(){
        return System.currentTimeMillis();
    }
    /**
     * 获取当前时间
     *
     * @param
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return df.format(new Date());
    }


    /**
     * 获取当前时间
     *
     * @param
     * @return
     */
    public static String getCurrentDatequn() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return df.format(new Date());
    }
    public static String convertFormat(String strDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String objStr = "";
        try {
            Date date = simpleDateFormat.parse(strDate);
            objStr = formatDateChineseS(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return objStr;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
    /**
     * 获取当前时间第二天的00:00:00
     *
     * @return
     */
    public static Date formatDateTomorrow(Date dNow) {//获取当前时间第二天的00:00:00
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(dNow);// 把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, +1); // 设置为明天
        dBefore = calendar.getTime(); // 得到前一天的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA); // 设置时间格式
        String date = sdf.format(dBefore) + " 00:00:00";
        Date tomorrow = strToDateLong(date);
        return tomorrow;
    }
    /**
     * 格式化时间(转换为"yyyy-MM-dd HH:mm:ss")
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return df.format(date);
    }





    /**
     * 格式化时间(转换为"yyyy年MM月dd日 HH:mm")
     *
     * @param date
     * @return
     */
    public static String formatDateChinese(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA);
        return df.format(date);
    }

    public static String formatDateChineseSS(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.CHINA);
        return df.format(date);
    }
    /**
     * 格式化时间(转换为"yyyy年MM月dd日")
     *
     * @param date
     * @return
     */
    public static String formatDateChineseS(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        return df.format(date);
    }

    /**
     * @param date
     * @return
     */
    public static String formatDateHHMM(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.CHINA);
        return df.format(date);
    }
    public static String formatDateTomorrowChinese(Date dNow) {
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(dNow);// 把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, +0); // 设置为前一天
        dBefore = calendar.getTime(); // 得到前一天的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss", Locale.CHINA); // 设置时间格式
        return sdf.format(dBefore);
    }
    /**
     * 获取15天前的时间 Data类型
     *
     * @param date
     * @return
     */
    public static Date formatDateDaysAgo(Date date) {
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(date);// 把时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -3); // 设置为3天前(测试)
//        calendar.add(Calendar.DAY_OF_MONTH, -15); // 设置为15天前
        Date dBefore = calendar.getTime();
        return dBefore;
    }

    /**
     * 返回时间是前3天的时间   String
     * @param date
     * @return
     */
    public static String formatDateDaysAgoString(Date date) {
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(date);// 把时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -3); // 设置为3天前(测试)
//        calendar.add(Calendar.DAY_OF_MONTH, -15); // 设置为15天前
        Date dBefore = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA); // 设置时间格式
        return format.format(dBefore);
    }

    /**
     * 返回时间是后3天的时间   String
     * @param date
     * @return
     */
    public static String formatDateDaysAfterString(Date date) {
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(date);// 把时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, +3); // 设置为3天前(测试)
//        calendar.add(Calendar.DAY_OF_MONTH, +15); // 设置为15天前
        Date dBefore = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA); // 设置时间格式
        return format.format(dBefore);
    }

    /**
     * @param timeStamp
     * @return
     */
    public static String castTimeStampToString(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        if (timeStamp == null || timeStamp.isEmpty()) {
            return "";
        } else {
            return format.format(Long.parseLong(timeStamp));
        }

    }

}

package com.wzrd.m.utils;

import android.content.Context;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lk on 2017/12/31.
 */

public class Utils {
    //使用正则表达式判断电话号码
    public static boolean isMobileNO(String tel) {
        Pattern p = Pattern.compile("^(13[0-9]|15([0-3]|[5-9])|14[5,7,9]|17[1,3,5,6,7,8]|18[0-9])\\d{8}$");
        Matcher m = p.matcher(tel);
        return m.matches();
    }

    /**
     * toast long
     * @param context
     * @param message
     */

    public static void ToastLong(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    /**
     * toast short
     * @param context
     * @param message
     */

    public static void ToastShort(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}

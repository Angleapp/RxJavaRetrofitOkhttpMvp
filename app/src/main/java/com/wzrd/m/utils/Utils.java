package com.wzrd.m.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wzrd.p.impl.AbsToolBarMenuPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lk on 2017/12/31.
 */

public class Utils {
    /**
     * 设置页面返回
     *
     * @param activity
     * @param backImageView
     * @param title
     * @param titleContent
     * @param menuImageView
     * @param resId
     */
    public static void backToolbar(final Activity activity, ImageView backImageView, TextView title, String titleContent, ImageView menuImageView, int resId, final AbsToolBarMenuPresenter callBack,TextView toolbarMenuText,String menuContent) {
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
        title.setText(titleContent);
        if (resId != 0) {
            menuImageView.setImageResource(resId);
        }
        if (callBack != null) {
            menuImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.setToolBarMenu();
                }
            });
        }
        toolbarMenuText.setText(menuContent);
    }

    //使用正则表达式判断电话号码
    public static boolean isMobileNO(String tel) {
        Pattern p = Pattern.compile("^(13[0-9]|15([0-3]|[5-9])|14[5,7,9]|17[1,3,5,6,7,8]|18[0-9])\\d{8}$");
        Matcher m = p.matcher(tel);
        return m.matches();
    }

    /**
     * toast long
     *
     * @param context
     * @param message
     */

    public static void ToastLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * toast short
     *
     * @param context
     * @param message
     */

    public static void ToastShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public static String getuuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * 比较两个时间的先后顺序
     *
     * @param locatime
     * @param servertime
     * @return
     */
    public static boolean compareTime(String locatime, String servertime) {
        try {
            // 如果下载的时间戳为空
            if (servertime == null || servertime.equals("")) {
                return true;
            }
            // 如果本地时间为空 则说明该说明在本地没有任何操作
            if ((locatime == null || locatime.equals("")) && servertime != null && !servertime.equals("")) {
                return false;
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            Date date1 = format.parse(locatime);
            Date date2 = format.parse(servertime);
            boolean flag = date1.after(date2);
            return flag;
        } catch (Exception e) {
            return true;
        }

    }

    /**
     * 以原点为圆点，以radius维半径画圆，通过弧度o,获得坐标
     *
     * @param radius 半径
     * @param o      弧度
     * @return
     */
    public static float[] getXYPoint(float[] centrePoint, int radius, float o) {
        float[] xyPoint = {(float) (radius * Math.sin(o) + centrePoint[0]), (float) ((-1) * radius * Math.cos(o) + centrePoint[1])};
        return xyPoint;
    }
}

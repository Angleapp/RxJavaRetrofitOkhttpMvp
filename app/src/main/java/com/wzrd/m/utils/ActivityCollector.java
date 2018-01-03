package com.wzrd.m.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/12.
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

//    public static void clearOther() {
//        for (Activity activity : activities) {
//            if (!activity.isFinishing()) {
//                if (activity instanceof LoginActivity) {
//                    return;
//                } else {
//                    activity.finish();
//                }
//            }
//        }
//    }
    public static Activity getActivity(){
        if (activities!=null){
            return activities.get(0);
        }else {
            return null;
        }
    }
}

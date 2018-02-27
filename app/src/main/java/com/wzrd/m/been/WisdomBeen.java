package com.wzrd.m.been;

import android.databinding.BaseObservable;
import android.os.Build;
import android.view.View;

import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.v.activity.homepage.WisdomActivity;

import java.io.Serializable;

/**
 * Created by lk on 2018/2/22.
 */

public class WisdomBeen extends BaseObservable implements Serializable {
    private String id;
    private String userid;
    private String message;
    private String type;
    private boolean ischeckd;
    private String modifiytime;
    private int position;


    public WisdomBeen() {
    }


    public WisdomBeen(String id, String userid, String message, String type, boolean ischeckd, String modifiytime, int position) {
        this.id = id;
        this.userid = userid;
        this.message = message;
        this.type = type;
        this.ischeckd = ischeckd;
        this.modifiytime = modifiytime;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isIscheckd() {
        return ischeckd;
    }

    public void setIscheckd(boolean ischeckd) {
        this.ischeckd = ischeckd;
    }

    public String getModifiytime() {
        return modifiytime;
    }

    public void setModifiytime(String modifiytime) {
        this.modifiytime = modifiytime;
    }

    /**
     * 退出
     *
     * @param view
     */
    public void backonclick(View view) {
        WisdomActivity activity = exit(view);
        activity.finish();
    }

    /**
     * 完成监听
     *
     * @param view
     */
    public void completeonclick(View view) {

        WisdomActivity activity = exit(view);
        SharedPreferencesUtil.saveInt(activity,"clickposition",getPosition());
        SharedPreferencesUtil.saveString(activity,"wisdom","wisdom");
        activity.finish();
    }

    public WisdomActivity exit(View view) {
        WisdomActivity activity;
        if (Build.VERSION.SDK_INT > 21) {
            activity = (WisdomActivity) view.getContext();
        } else {
            activity = (WisdomActivity) view.getRootView().getContext();
        }
        return activity;
    }

    /**
     * 判断‘更多推荐’是否显示
     *
     * @param type
     * @return
     */
    public boolean gettype(String type) {
        if ("1".equals(type)) {
            return true;
        }
        return false;


    }

}

package com.wzrd.m.been;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.wzrd.m.utils.Utils;
import com.wzrd.v.activity.welcome.IconActivity;

/**
 * Created by lk on 2018/1/3.
 */

public class MineAndLover extends BaseObservable {
    private String iconpath;
    private UserMessage userMessage;

    public MineAndLover() {
    }

    public String getIconpath() {
        return iconpath;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath;
    }

    public UserMessage getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(UserMessage userMessage) {
        this.userMessage = userMessage;
    }

    /**
     * 退出监听
     *
     * @param view
     */
    public void onExitClickView(View view) {
        Utils.ToastShort(view.getContext(), "退出监听");

    }

    /**
     * 解除与爱人之间的绑定
     *
     * @param view
     */
    public void onUnLoveClickView(View view) {
        Utils.ToastShort(view.getContext(), "解除与爱人之间的绑定");
    }


    /**
     * xiugai头像昵称
     *
     * @param view
     */
    public void onModifyClickView(View view) {
        Intent intent = new Intent(view.getContext(), IconActivity.class);
        intent.putExtra("pathfrom", "确定");
        view.getContext().startActivity(intent);
    }

}

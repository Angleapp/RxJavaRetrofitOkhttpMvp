package com.wzrd.m.been;

import android.content.Intent;
import android.os.Build;
import android.view.View;

import com.wzrd.v.activity.welcome.IconActivity;

/**
 * Created by lk on 2017/12/31.
 */

public class UserMessage {

    private String nickname;//昵称
    private String iconpath;//头像地址
    private final String IMAGE_TYPE = "image/*";
    public static final int IMAGE_REQUEST_CODE = 0x102;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIconpath() {
        return iconpath;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath;
    }

    /**
     * 选择头像的监听
     * @param view
     */
    public void onClickView(View view){
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(IMAGE_TYPE);
        if (Build.VERSION.SDK_INT <19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }
//        Log.e("view","view-->"+view.getContext());
        IconActivity activity= (IconActivity) view.getRootView().getContext();
        activity.startActivityForResult(intent, IMAGE_REQUEST_CODE);

    }
}

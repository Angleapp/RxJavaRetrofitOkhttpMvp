package com.wzrd.m.been;

import android.Manifest;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.os.Build;
import android.text.Editable;
import android.view.View;

import com.wzrd.m.utils.Constants;
import com.wzrd.m.utils.RxPermission;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.v.activity.welcome.BindingLoversActivity;
import com.wzrd.v.activity.welcome.IconActivity;


/**
 * Created by lk on 2017/12/31.
 */

public class UserMessage extends BaseObservable {

    private String nickname;//昵称
    private String iconpath;//头像地址
    private final String IMAGE_TYPE = "image/*";
    public static final int IMAGE_REQUEST_CODE = 0x102;

    public UserMessage(String nickname, String iconpath) {
        this.nickname = nickname;
        this.iconpath = iconpath;
//
    }

    public UserMessage() {
    }

    public String getNickname() {

        return  this.nickname;
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
     *
     * @param view
     */
    public void onClickView(View view) {
        IconActivity activity = (IconActivity) view.getRootView().getContext();
        boolean rxrequest = RxPermission.rxrequest(activity, Constants.RDWISDPREMISS,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (rxrequest) {
            Intent intent = new Intent();
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType(IMAGE_TYPE);
            if (Build.VERSION.SDK_INT < 19) {
                intent.setAction(Intent.ACTION_GET_CONTENT);
            } else {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            }
            activity.startActivityForResult(intent, IMAGE_REQUEST_CODE);
        }

    }

    /**
     * editview 内容改变的监听
     * @param
     */
    public void onChangClickView(Editable view){

        this.nickname=view.toString();
        setNickname(this.nickname);
    }

    /**
     * 点击下一步的监听
     * @param view
     */

    public void onNextClickView(View view){
        IconActivity activity = (IconActivity) view.getRootView().getContext();
        SharedPreferencesUtil.saveString(activity,"nickname",this.nickname);
        Intent intent = new Intent(activity, BindingLoversActivity.class);
        activity.startActivity(intent);

    }
}

package com.wzrd.m.been;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.wzrd.m.contacts.ContactsActivity;
import com.wzrd.m.utils.Constants;
import com.wzrd.m.utils.RxPermission;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.m.utils.Utils;
import com.wzrd.v.activity.MainActivity;
import com.wzrd.v.activity.welcome.BindingLoversActivity;

/**
 * Created by lk on 2018/1/3.
 */

public class LoveUser extends BaseObservable {
    private String lovename;
    private String lovephone;
    public static final int LOVERESULT = 0x23;

    public LoveUser(String lovename, String lovephone) {
        this.lovename = lovename;
        this.lovephone = lovephone;
    }

    public LoveUser() {
    }

    public String getLovename() {
        return lovename;
    }

    public void setLovename(String lovename) {
        this.lovename = lovename;
    }

    public String getLovephone() {
        return lovephone;
    }

    public void setLovephone(String lovephone) {
        this.lovephone = lovephone;
    }

    /**
     * 打开通话普
     *
     * @param view
     */

    public void onClickContacts(View view) {

        BindingLoversActivity activity = (BindingLoversActivity) view.getRootView().getContext();
        boolean rxrequest = RxPermission.rxrequest(activity, Constants.RDWISDCONTANTS, Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SYNC_SETTINGS);
        if (rxrequest) {
            activity.startActivityForResult(new Intent(activity, ContactsActivity.class), LOVERESULT);
        }
    }

    /**
     * 绑定情侣
     * @param view
     */

    public void onClickBinding(View view) {
        Context context = view.getRootView().getContext();
//        BindingLoversActivity activity = (BindingLoversActivity) context;
        SharedPreferencesUtil.saveString(context,"lovephone",lovephone);
        Utils.ToastShort(context,"绑定成功");
    }

    /**
     * 暂不绑定情侣
     * @param view
     */
    public void onClickNoBinding(View view) {
        Context context = view.getRootView().getContext();
        BindingLoversActivity activity = (BindingLoversActivity) context;
//        SharedPreferencesUtil.saveString(context,"lovephone",lovephone);
//        Utils.ToastShort(context,"绑定成功");

        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }


}

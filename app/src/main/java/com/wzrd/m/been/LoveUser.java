package com.wzrd.m.been;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.os.Build;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wzrd.m.contacts.ContactsActivity;
import com.wzrd.m.utils.Constants;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.m.utils.Utils;
import com.wzrd.v.activity.MainActivity;
import com.wzrd.v.activity.welcome.BindingLoversActivity;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

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

        final BindingLoversActivity activity;
        if (Build.VERSION.SDK_INT > 21) {
            activity = (BindingLoversActivity) view.getContext();
        } else {
            activity = (BindingLoversActivity) view.getRootView().getContext();
        }

        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions
                .request(Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SYNC_SETTINGS)
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Observer<Boolean>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        /**
                         * aBoolean为true  同意权限
                         * aBoolean为false 没同意权限
                         */

                        if (!aBoolean) {
                            Utils.ToastShort(activity, Constants.RDWISDCONTANTS);
                        } else {
                            activity.startActivityForResult(new Intent(activity, ContactsActivity.class), LOVERESULT);

                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    /**
     * 绑定情侣
     *
     * @param view
     */

    public void onClickBinding(View view) {
        Context context = view.getRootView().getContext();
//        BindingLoversActivity activity = (BindingLoversActivity) context;
        SharedPreferencesUtil.saveString(context, "lovephone", lovephone);
        Utils.ToastShort(context, "绑定成功");
    }

    /**
     * 暂不绑定情侣
     *
     * @param view
     */
    public void onClickNoBinding(View view) {
        Context context = view.getContext();
        BindingLoversActivity activity = (BindingLoversActivity) context;
//        SharedPreferencesUtil.saveString(context,"lovephone",lovephone);
//        Utils.ToastShort(context,"绑定成功");

        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }


}

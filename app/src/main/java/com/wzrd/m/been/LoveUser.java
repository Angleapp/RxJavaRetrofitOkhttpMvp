package com.wzrd.m.been;

import android.Manifest;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.os.Build;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wzrd.m.contacts.ContactsActivity;
import com.wzrd.m.db.manger.UserManager;
import com.wzrd.m.utils.Constants;
import com.wzrd.m.utils.DateUtils;
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
                            Intent intent = new Intent(activity, ContactsActivity.class);
                            intent.putExtra("pathfrom", "love");
                            activity.startActivityForResult(intent, LOVERESULT);

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

        BindingLoversActivity activity;
        if (Build.VERSION.SDK_INT > 21) {
            activity = (BindingLoversActivity) view.getContext();
        } else {
            activity = (BindingLoversActivity) view.getRootView().getContext();
        }


        SharedPreferencesUtil.saveString(activity, "lovephone", lovephone);
        SharedPreferencesUtil.saveString(activity, "lovename", lovename);
        Utils.ToastShort(activity, "绑定成功");
        String userphone = SharedPreferencesUtil.getString(activity, "userphonenum", null);
        if (userphone != null) {
            UserManager manager = new UserManager(activity);
            TSYSUSER modile = new TSYSUSER();
            modile.setT_sys_id(userphone);
            modile.setT_sys_userid(userphone);
            modile.setT_sys_modify_id(userphone);
            modile.setT_sys_userphone(userphone);
            modile.setT_sys_username(userphone);
            modile.setT_sys_lover_name(this.lovename);
            modile.setT_sys_loverphone(lovephone);
            modile.setT_sys_modify_time(DateUtils.getCurrentDate());
            modile.setT_sys_username(SharedPreferencesUtil.getString(activity, "nickname", ""));
            modile.setT_sys_usericonpath(SharedPreferencesUtil.getString(activity, "icon", ""));
            manager.insertUser(modile);
        }

        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();


    }

    /**
     * 暂不绑定情侣
     *
     * @param view
     */
    public void onClickNoBinding(View view) {
        BindingLoversActivity activity;
        if (Build.VERSION.SDK_INT > 21) {
            activity = (BindingLoversActivity) view.getContext();
        } else {
            activity = (BindingLoversActivity) view.getRootView().getContext();
        }
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }


}

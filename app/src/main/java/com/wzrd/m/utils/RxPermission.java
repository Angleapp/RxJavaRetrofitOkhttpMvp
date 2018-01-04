package com.wzrd.m.utils;

import android.app.Activity;
import android.util.Log;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 权限的申请
 * Created by lk on 2018/1/2.
 */

public class RxPermission {

    private boolean permiss = false;

    public boolean rxrequest(final Activity context, final String message, String... permissions) {
        RxPermissions rxPermissions = new RxPermissions(context);
        final String TAG = "RxPermission";

        rxPermissions
                .request(permissions)
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
                            Utils.ToastShort(context, message);
                        }
                        permiss = aBoolean;
                        Log.e(TAG, "aBoolean--->" + permiss);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "e--->" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete--->" + permiss);
                    }
                });

        return permiss;
    }
}



package com.wzrd.m.utils;

import android.app.Activity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 权限的申请
 * Created by lk on 2018/1/2.
 */

public class RxPermission {

   private static boolean  permiss=false;
    public static boolean rxrequest(final Activity context, final String message, String... permissions ){
        RxPermissions rxPermissions = new RxPermissions(context);

        rxPermissions.request(permissions).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                /**
                 * aBoolean为true  同意权限
                 * aBoolean为false 没同意权限
                 */

                if(!aBoolean){
                    Utils.ToastShort(context,message);
                }
                permiss=aBoolean;

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        return permiss;
    }
}



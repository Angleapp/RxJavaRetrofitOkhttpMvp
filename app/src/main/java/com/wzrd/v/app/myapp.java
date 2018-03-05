package com.wzrd.v.app;

import android.app.Application;
import android.content.Context;

//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.cookie.CookieJarImpl;
//import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

/**
 * Created by lk on 2017/10/30.
 */

public class myapp extends Application {

    private Context mAppContext;

    public Context getAppContext() {
        return mAppContext;
    }

    public void setAppContext(Context appContext) {
        mAppContext = appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getAppContext();
//        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .cookieJar(cookieJar)
//                //其他配置
//                .build();
//
//        OkHttpUtils.initClient(okHttpClient);

    }
}

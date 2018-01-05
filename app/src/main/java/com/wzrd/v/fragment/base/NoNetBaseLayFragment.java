package com.wzrd.v.fragment.base;


import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by lk on 2018/1/5.
 */

public abstract class NoNetBaseLayFragment extends Fragment {
    protected boolean isVisible;
    // 标志位，标志已经初始化完成。
    protected boolean isPrepared = false;
    public Context context;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    public abstract void lazyLoad();

    protected void onInvisible() {
    }

}

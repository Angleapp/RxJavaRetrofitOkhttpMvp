package com.wzrd.p.impl;

import com.wzrd.v.view.interf.TimerView;

/**
 * Created by lk on 2017/12/31.
 */

public abstract class AbsRxTimerPresenter<V extends TimerView> {
    private  V view;
    public V getView() {
        return view;
    }
}

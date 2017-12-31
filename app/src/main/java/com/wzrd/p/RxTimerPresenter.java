package com.wzrd.p;

import android.content.Context;

import com.wzrd.m.timer.RxTimerMoulde;
import com.wzrd.p.impl.AbsRxTimerPresenter;
import com.wzrd.v.view.interf.TimerView;

/**
 * Created by lk on 2017/12/31.
 */

public class RxTimerPresenter extends AbsRxTimerPresenter {
    private RxTimerMoulde moulde;
    private TimerView view;

    public RxTimerPresenter(Context context) {
        this.moulde = new RxTimerMoulde();
        this.view= (TimerView) context;
    }

    public void timer(int time){
        this.moulde.Rxtimer(time,view);
    }
}

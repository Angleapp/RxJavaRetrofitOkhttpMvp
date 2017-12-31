package com.wzrd.v.view.interf;

/**
 * Created by lk on 2017/12/31.
 */

public interface TimerView<T> {
    void onCompile();
    void onRefresh(T message);
    void onError(T message);
    void onBegin(T message);
}

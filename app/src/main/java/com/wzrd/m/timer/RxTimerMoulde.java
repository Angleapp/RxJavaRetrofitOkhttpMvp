package com.wzrd.m.timer;

import com.wzrd.v.view.interf.TimerView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by lk on 2017/12/31.
 */

public class RxTimerMoulde {

    public void Rxtimer(final int time, final TimerView view) {
        if (view != null) {


            countdown(time)
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
                            view.onBegin(time);
                        }
                    }).subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Integer integer) {
                    view.onRefresh(integer);
                }

                @Override
                public void onError(Throwable e) {
                    view.onError(e.getMessage().toString());
                }

                @Override
                public void onComplete() {
                    view.onCompile();
                }
            });
        }


    }

    public static Observable<Integer> countdown(int time) {
        if (time < 0) time = 0;

        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Integer>() {

                    @Override
                    public Integer apply(Long aLong) throws Exception {
                        return countTime - aLong.intValue();
                    }
                })
                .take(countTime + 1);
    }


}

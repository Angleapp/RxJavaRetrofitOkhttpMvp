package com.wzrd.m.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.wzrd.R;
import com.wzrd.m.been.ContactMessage;
import com.wzrd.m.been.SelectBean;
import com.wzrd.m.been.WisdomBeen;
import com.wzrd.v.activity.homepage.WisdomActivity;
import com.wzrd.v.adapter.InboxAdapter;
import com.wzrd.v.adapter.OutboxAdapter;
import com.wzrd.v.adapter.RecycleViewDivider;
import com.wzrd.v.adapter.SelectAdapter;
import com.wzrd.v.adapter.WisdomAdapter;
import com.wzrd.v.view.SwipeMenuLayout;

import java.util.List;

/**
 * Created by lk on 2018/2/9.
 */

public class DataRecycleUtils {
    private static final int a = R.color.white_9;

    @BindingAdapter("outboxadapter")
    public static void setoutboxadapter(RecyclerView recyclerView, List<ContactMessage> data) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecycleViewDivider(recyclerView.getContext(), LinearLayoutManager.VERTICAL, 1, a));
        recyclerView.setAdapter(new OutboxAdapter(recyclerView.getContext(), data));
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    SwipeMenuLayout viewCache = SwipeMenuLayout.getViewCache();
                    if (null != viewCache) {
                        viewCache.smoothClose();
                    }
                }
                return false;
            }
        });
    }


    @BindingAdapter("inboxadapter")
    public static void setinboxadapter(RecyclerView recyclerView, List<ContactMessage> data) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new RecycleViewDivider(recyclerView.getContext(), LinearLayoutManager.VERTICAL, 1, a));
        recyclerView.setAdapter(new InboxAdapter(recyclerView.getContext(), data));

    }


    @BindingAdapter("selectdback")
    public static void setselectdback(RecyclerView recyclerView, List<SelectBean> data) {
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
        recyclerView.setAdapter(new SelectAdapter(recyclerView.getContext(), data));

    }

    @BindingAdapter("wisdomadapter")
    public static void setwisdom(RecyclerView recyclerView, List<WisdomBeen> data) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new WisdomAdapter(recyclerView.getContext(), data, (WisdomActivity) recyclerView.getContext()));
    }
}

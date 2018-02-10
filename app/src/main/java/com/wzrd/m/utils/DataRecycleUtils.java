package com.wzrd.m.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wzrd.m.been.ContactMessage;
import com.wzrd.v.adapter.OutboxAdapter;

import java.util.List;

/**
 * Created by lk on 2018/2/9.
 */

public class DataRecycleUtils {
    @BindingAdapter("adapter")
    public static void setadapter(RecyclerView recyclerView, List<ContactMessage> data){
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new OutboxAdapter(recyclerView.getContext(), data));
    }
}

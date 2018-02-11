package com.wzrd.v.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wzrd.BR;
import com.wzrd.R;
import com.wzrd.databinding.OutboxAdapterItemBinding;
import com.wzrd.m.been.ContactMessage;
import com.wzrd.m.holder.BindingHolder;

import java.util.List;

/**
 * Created by lk on 2018/2/9.
 */

public class OutboxAdapter extends RecyclerView.Adapter<BindingHolder> {
    private List<ContactMessage> data;

    private Context context;

    public OutboxAdapter(Context context, List<ContactMessage> list) {
        this.context = context;
        this.data = list;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        OutboxAdapterItemBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.outbox_adapter_item, parent, false);


        return new BindingHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {

        holder.getBinding().setVariable(BR.message, data.get(position));
        // 立刻刷新界面
        holder.getBinding().executePendingBindings();
    }
    

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}

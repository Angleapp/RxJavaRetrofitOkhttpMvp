package com.wzrd.v.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzrd.BR;
import com.wzrd.R;
import com.wzrd.databinding.WisdomAdapterItemBinding;
import com.wzrd.m.been.WisdomBeen;
import com.wzrd.m.holder.BindingHolder;
import com.wzrd.p.inteface.AdapterClickPosition;

import java.util.List;

/**
 * Created by lk on 2018/2/22.
 */

public class WisdomAdapter extends RecyclerView.Adapter<BindingHolder> {
    private List<WisdomBeen> data;

    private Context context;
    private final int selectadaptre = 0x123;
    private AdapterClickPosition.position adapterClickPosition;

    public WisdomAdapter(Context context, List<WisdomBeen> list, AdapterClickPosition.position position) {
        this.context = context;
        this.data = list;
        this.adapterClickPosition = position;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WisdomAdapterItemBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.wisdom_adapter_item, parent, false);
        return new BindingHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, final int position) {
        holder.getBinding().setVariable(BR.data, data.get(position));
        // 立刻刷新界面
        holder.getBinding().executePendingBindings();
        holder.getBinding().getRoot().findViewById(R.id.rl_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setIscheckd(false);
                }
                data.get(position).setIscheckd(true);
                adapterClickPosition.adapterposition(position);
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}


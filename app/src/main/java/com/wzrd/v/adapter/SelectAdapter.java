package com.wzrd.v.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzrd.BR;
import com.wzrd.R;
import com.wzrd.databinding.SelectdAdapterItemBinding;
import com.wzrd.m.been.SelectBean;
import com.wzrd.m.holder.BindingHolder;
import com.wzrd.v.activity.message.SelectBackActivity;

import java.util.List;

/**
 * Created by lk on 2018/2/19.
 */

public class SelectAdapter extends RecyclerView.Adapter<BindingHolder> {
    private List<SelectBean> data;

    private Context context;
    private final int selectadaptre = 0x123;

    public SelectAdapter(Context context, List<SelectBean> list) {
        this.context = context;
        this.data = list;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SelectdAdapterItemBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.selectd_adapter_item, parent, false);
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
                    data.get(i).setCheckd(false);
                }
                data.get(position).setCheckd(true);
                notifyDataSetChanged();
                SelectBackActivity activity = (SelectBackActivity) context;
                Intent intent = new Intent();
                SelectBean bean = data.get(position);
                intent.putExtra("bean", bean);
                activity.setResult(selectadaptre,intent );
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}

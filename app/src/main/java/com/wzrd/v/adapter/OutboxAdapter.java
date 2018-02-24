package com.wzrd.v.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzrd.BR;
import com.wzrd.R;
import com.wzrd.databinding.OutboxAdapterItemBinding;
import com.wzrd.m.been.ContactMessage;
import com.wzrd.m.holder.BindingHolder;
import com.wzrd.v.view.SwipeMenuLayoutInoux;

import java.util.List;

/**
 * Created by lk on 2018/2/9.
 */

public class OutboxAdapter extends RecyclerView.Adapter<BindingHolder> {
    private List<ContactMessage> data;
    private Context context;
    private boolean isDeleteAble = true;

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
    public void onBindViewHolder(final BindingHolder holder, final int position) {

        holder.getBinding().setVariable(BR.message, data.get(position));
        // 立刻刷新界面
        holder.getBinding().executePendingBindings();
        holder.getBinding().getRoot().findViewById(R.id.btn_deloutbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwipeMenuLayoutInoux viewCache = SwipeMenuLayoutInoux.getViewCache();
                if (null != viewCache) {
                    viewCache.smoothClose();
                }
                if (isDeleteAble) {

                    isDeleteAble = false;//初始值为true,当点击删除按钮以后，休息0.3秒钟再让他为
                    //true,起到让数据源刷新完成的作用
                    data.remove(position);//删除数据源
                    notifyItemRemoved(position);//刷新被删除的地方
                    notifyItemRangeChanged(position, getItemCount()); //刷新被删除数据，以及其后面的数据
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(300);
                                isDeleteAble = true;//可点击按钮
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();

                }


            }
        });
    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}

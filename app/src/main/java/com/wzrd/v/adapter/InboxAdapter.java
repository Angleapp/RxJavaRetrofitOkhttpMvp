package com.wzrd.v.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wzrd.BR;
import com.wzrd.R;
import com.wzrd.m.been.ContactMessage;
import com.wzrd.m.holder.BindingHolder;
import com.wzrd.v.view.NumImageView;

import java.util.List;

/**
 * Created by lk on 2018/2/9.
 */

public class InboxAdapter extends RecyclerView.Adapter<BindingHolder> {
    private List<ContactMessage> data;

    private Context context;

    public InboxAdapter(Context context, List<ContactMessage> list) {
        this.context = context;
        this.data = list;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.inbox_adapter_item, parent, false);
        return new BindingHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.getBinding().setVariable(BR.message, data.get(position));
        // 立刻刷新界面
        holder.getBinding().executePendingBindings();

        final LinearLayout ll_inbox= (LinearLayout) holder.getBinding().getRoot().findViewById(R.id.ll_inbox);
        final NumImageView num_iv= (NumImageView) holder.getBinding().getRoot().findViewById(R.id.num_iv);
        if(position>3){
            num_iv.setNum(2);
        }else{
            num_iv.setNum(0);
        }

        ll_inbox.setBackgroundColor(0);
        ll_inbox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        ll_inbox.setBackgroundColor(context.getResources().getColor(R.color.white_b9));
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_MOVE:
                        ll_inbox.setBackgroundColor(0);
                        break;
                }


                return true;
            }

        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}

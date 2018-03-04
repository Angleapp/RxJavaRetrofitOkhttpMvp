package com.wzrd.v.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wzrd.R;
import com.wzrd.m.utils.TextUtil;
import com.wzrd.p.inteface.VideoRecyclerViewItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hyj on 2018/3/2.
 */

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.ViewHolder> {
    private List<String> mList;
    private Context mContext;
    private VideoRecyclerViewItem mVideoRecyclerViewItem;
    private LayoutInflater mInflater;
    public VideoRecyclerViewAdapter(List<String> list, Context context, VideoRecyclerViewItem videoRecyclerViewItem) {
        mList = list;
        mContext = context;
        mVideoRecyclerViewItem = videoRecyclerViewItem;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.video_detail_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        View view = holder.itemView;
        view.setBackgroundResource(R.drawable.video_list_item_selector);
        if (position==0){
            view.setSelected(true);
        }else{
            view.setSelected(false);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVideoRecyclerViewItem.onItemClickLister(position,view);
            }
        });
        String path = mList.get(position);
        if (TextUtil.isEmpty(path)) {
            holder.mIcon.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(path).into(holder.mIcon);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        ImageView mIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

package com.wzrd.v.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wzrd.R;
import com.wzrd.m.been.Video;
import com.wzrd.v.view.GlideRoundTransform;

import java.util.List;

/**
 * Created by hyj on 2018/2/26.
 */

public class VideoRecyclerAdapter extends BaseAdapter {
    private List<Video> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public VideoRecyclerAdapter(List<Video> list, Context context) {
        mList = list;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup group) {
        MyViewHolder viewHolder;
        if (view == null) {
            view = mInflater.inflate(R.layout.video_list_item, group, false);
            viewHolder = new MyViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) view.getTag();
        }
        Video video = mList.get(i);
        int widthPixels = mContext.getResources().getDisplayMetrics().widthPixels - 50;
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(widthPixels / 2, (int) (widthPixels / 2 * 0.62));
        view.setLayoutParams(layoutParams);
        if (TextUtils.isEmpty(video.getVideoPic())) {
            viewHolder.mVideoPic.setImageResource(R.mipmap.pic_text_background);
        } else {
            Glide.with(mContext)
                    .load(video.getVideoPic())
                    .transform(new GlideRoundTransform(mContext,20))
                    .into(viewHolder.mVideoPic);
        }
        viewHolder.mVideoTitle.setText(video.getTitle());
        return view;
    }

    class MyViewHolder {
        ImageView mVideoPic;
        TextView mVideoTitle;

        public MyViewHolder(View itemView) {
            mVideoTitle = (TextView) itemView.findViewById(R.id.video_title);
            mVideoPic = (ImageView) itemView.findViewById(R.id.video_pic);
        }
    }
}

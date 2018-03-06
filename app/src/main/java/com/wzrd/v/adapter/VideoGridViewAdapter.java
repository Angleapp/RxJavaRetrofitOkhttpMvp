package com.wzrd.v.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.wzrd.R;
import com.wzrd.m.been.Video;
import com.wzrd.m.utils.TextUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by hyj on 2018/2/26.
 */

public class VideoGridViewAdapter extends BaseAdapter {
    private List<Video> mList;
    private Context mContext;
    private Map<String, Bitmap> mMap;
    private LayoutInflater mInflater;

    public VideoGridViewAdapter(List<Video> list, Context context, Map<String, Bitmap> map) {
        mList = list;
        mContext = context;
        mMap = map;
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
        int widthPixels = mContext.getResources().getDisplayMetrics().widthPixels;
        int w = (int) (widthPixels * 0.436);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(w, (int) (w * 0.62));
        view.setLayoutParams(layoutParams);
        String path = video.getVideo_path();
        if (!TextUtil.isEmpty(path)) {
            Bitmap bitmap = mMap.get(path);
            viewHolder.mVideoPic.setImageBitmap(bitmap);
        }
        viewHolder.mVideoTitle.setText(video.getTitle());
        return view;
    }

    class MyViewHolder {
        RoundedImageView mVideoPic;
        TextView mVideoTitle;

        public MyViewHolder(View itemView) {
            mVideoTitle = (TextView) itemView.findViewById(R.id.video_title);
            mVideoPic = (RoundedImageView) itemView.findViewById(R.id.video_pic);
        }
    }
}

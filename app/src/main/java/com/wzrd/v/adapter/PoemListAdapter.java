package com.wzrd.v.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.been.Poem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hyj on 2018/2/14.
 */

public class PoemListAdapter extends BaseAdapter {
    private List<Poem> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public PoemListAdapter(List<Poem> list, Context context) {
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
        ViewHolder viewHolder ;
        if (view==null){
            view = mInflater.inflate(R.layout.poem_list_item,group,false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Poem poem = mList.get(i);
        viewHolder.mTitle.setText(poem.getTitle());
        viewHolder.mAuthor.setText(poem.getAuthor());
        viewHolder.mContent.setText(poem.getContent());
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.author)
        TextView mAuthor;
        @BindView(R.id.content)
        TextView mContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

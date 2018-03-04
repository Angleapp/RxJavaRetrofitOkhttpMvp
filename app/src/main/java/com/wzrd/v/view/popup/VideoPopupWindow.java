package com.wzrd.v.view.popup;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wzrd.R;

/**
 * Created by lk on 2018/3/4.
 */

public class VideoPopupWindow extends PopupWindow {
    public VideoPopupWindow(final Activity context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.dailog_video_pop, null, false);

        TextView tv_more = (TextView) binding.getRoot().findViewById(R.id.tv_more);
        TextView tv_del = (TextView) binding.getRoot().findViewById(R.id.tv_del);
        TextView tv_send = (TextView) binding.getRoot().findViewById(R.id.tv_send);
        TextView tv_rename = (TextView) binding.getRoot().findViewById(R.id.tv_rename);
        LinearLayout ll_cancle = (LinearLayout) binding.getRoot().findViewById(R.id.ll_cancle);

        this.setContentView(binding.getRoot());
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        this.setWidth(9 * widthPixels / 10);
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);
        Drawable drawable = context.getResources().getDrawable(R.drawable.bg_shape);
        setBackgroundDrawable(drawable);
//更多
        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//发送

            }
        });
        tv_rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//重命名

            }
        });
        //删除
        tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        //取消
        ll_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

            }
        });


    }
}

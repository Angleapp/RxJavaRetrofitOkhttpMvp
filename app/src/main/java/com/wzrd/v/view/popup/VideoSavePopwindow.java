package com.wzrd.v.view.popup;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.v.view.CircleBarView;

/**
 * Created by lk on 2018/3/5.
 */

public class VideoSavePopwindow extends PopupWindow {
    public VideoSavePopwindow(final Activity context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.dailog_video_save_pop, null, false);
        final TextView tv_sure = (TextView) binding.getRoot().findViewById(R.id.tv_sure);
        final CircleBarView cb_pro = (CircleBarView) binding.getRoot().findViewById(R.id.cb_pro);
        final LinearLayout ll_sending = (LinearLayout) binding.getRoot().findViewById(R.id.ll_sending);
        final RelativeLayout rl_send_complece = (RelativeLayout) binding.getRoot().findViewById(R.id.rl_send_complece);
        this.setContentView(binding.getRoot());
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        this.setWidth(3 * widthPixels / 4);
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(false);
        this.setAnimationStyle(R.style.AnimBottom);
        Drawable drawable = context.getResources().getDrawable(R.drawable.bg_shape);
        setBackgroundDrawable(drawable);
        cb_pro.setMaxNum(10);
        cb_pro.setProgressNum(10, 3000);


        cb_pro.setOnAnimationListener(new CircleBarView.OnAnimationListener() {
            @Override
            public String howToChangeText(float interpolatedTime, float updateNum, float maxNum) {
                return null;
            }

            @Override
            public void howTiChangeProgressColor(Paint paint, float interpolatedTime, float updateNum, float maxNum) {
                if (interpolatedTime == 1.0) {
                    ll_sending.setVisibility(View.GONE);
                    rl_send_complece.setVisibility(View.VISIBLE);
                }

            }
        });
        /**
         * 取消
         */
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

            }
        });




    }

}



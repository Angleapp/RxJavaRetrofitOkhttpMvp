package com.wzrd.v.view.popup;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.utils.ActivityCollector;

/**
 * Created by lk on 2018/2/14.
 */

public class TimerPopupWindow extends PopupWindow {


    private View mMenuView;

    public TimerPopupWindow(final Activity context,String message,String lasttime) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = LayoutInflater.from(context).inflate(R.layout.dailog_timer_pop, null);
        TextView tv_tile = (TextView) view.findViewById(R.id.tv_tile);
        TextView tv_sure = (TextView) (TextView) view.findViewById(R.id.tv_sure);
        TextView tv_tile_time = (TextView) (TextView) view.findViewById(R.id.tv_tile_time);

        this.setContentView(view);
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        this.setWidth(3 * widthPixels / 4);
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);
        Drawable drawable = context.getResources().getDrawable(R.drawable.bg_shape);
        setBackgroundDrawable(drawable);
        tv_tile.setText(message);
        tv_tile_time.setText(lasttime);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

}


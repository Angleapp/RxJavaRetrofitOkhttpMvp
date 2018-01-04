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


/**
 * Created by lk on 2017/5/9.
 */

public class RelifePopupWindow extends PopupWindow {


    private View mMenuView;
    public RelifePopupWindow(final Activity context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mMenuView = inflater.inflate(R.layout.dailog_relife_pop, null);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.dailog_relife_pop, null, false);
        TextView tv_tile= (TextView) binding.getRoot().findViewById(R.id.tv_tile);
        TextView tv_qx= (TextView) (TextView) binding.getRoot().findViewById(R.id.tv_qx);
        TextView tv_sure= (TextView) (TextView) binding.getRoot().findViewById(R.id.tv_sure);

        //设置SelectPicPopupWindow的View
        this.setContentView(binding.getRoot());
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        this.setWidth(3*widthPixels / 4);
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);
        Drawable drawable = context.getResources().getDrawable(R.drawable.bg_shape);
        setBackgroundDrawable(drawable);

        tv_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

}


















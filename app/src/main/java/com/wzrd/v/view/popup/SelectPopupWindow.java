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
 * Created by lk on 2018/2/14.
 */

public class SelectPopupWindow extends PopupWindow {
    private View mMenuView;

    public SelectPopupWindow(final Activity context, String title, String tip, String unSureContent,int unSureColor, String sureContent,int sureColor, View.OnClickListener clickListener) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.dailog_sure_dismiss, null, false);
        TextView tv_title = (TextView) binding.getRoot().findViewById(R.id.tv_title);
        TextView tv_tip = (TextView) binding.getRoot().findViewById(R.id.tv_tip);
        TextView unSure = (TextView) binding.getRoot().findViewById(R.id.tv_unSure);
        TextView sure = (TextView) binding.getRoot().findViewById(R.id.tv_sure);
        tv_title.setText(title);
        tv_tip.setText(tip);
        unSure.setTextColor(unSureColor);
        unSure.setText(unSureContent);
        sure.setTextColor(sureColor);
        sure.setText(sureContent);
        this.setContentView(binding.getRoot());
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        this.setWidth(3 * widthPixels / 4);
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);
        Drawable drawable = context.getResources().getDrawable(R.drawable.bg_shape);
        setBackgroundDrawable(drawable);
        sure.setOnClickListener(clickListener);
        unSure.setOnClickListener(clickListener);
    }

}


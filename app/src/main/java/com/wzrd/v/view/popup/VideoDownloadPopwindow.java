package com.wzrd.v.view.popup;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.wzrd.R;

/**
 * Created by lk on 2018/3/5.
 */

public class VideoDownloadPopwindow extends PopupWindow {
    public VideoDownloadPopwindow(final Activity context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.dailog_download_pop, null, false);
        this.setContentView(binding.getRoot());
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        this.setWidth(3 * widthPixels / 4);
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(false);
        this.setAnimationStyle(R.style.AnimBottom);
        Drawable drawable = context.getResources().getDrawable(R.drawable.bg_shape);
        setBackgroundDrawable(drawable);

    }

}



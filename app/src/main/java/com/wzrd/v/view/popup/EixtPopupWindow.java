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
import com.wzrd.m.db.manger.UserManager;
import com.wzrd.m.utils.ActivityCollector;
import com.wzrd.m.utils.SharedPreferencesUtil;

/**
 * Created by lk on 2018/2/14.
 */

public class EixtPopupWindow extends PopupWindow {


    private View mMenuView;

    public EixtPopupWindow(final Activity context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.dailog_exit_pop, null, false);
        TextView tv_tile = (TextView) binding.getRoot().findViewById(R.id.tv_tile);
        TextView tv_qx = (TextView) (TextView) binding.getRoot().findViewById(R.id.tv_qx);
        TextView tv_sure = (TextView) (TextView) binding.getRoot().findViewById(R.id.tv_sure);
        this.setContentView(binding.getRoot());
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        this.setWidth(3 * widthPixels / 4);
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
//                context.finish();
                ActivityCollector.finishAll();
                dismiss();
            }
        });

    }

}


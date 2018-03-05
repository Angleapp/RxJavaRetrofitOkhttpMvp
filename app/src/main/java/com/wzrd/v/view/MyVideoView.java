package com.wzrd.v.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.VideoView;

/**
 * Created by hyj on 2018/3/1.
 */

public class MyVideoView extends VideoView {
    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //得到手机屏幕的宽和高
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels; // 屏幕宽（像素，如：3200px）
        int screenHeight = dm.heightPixels; // 屏幕高（像素，如：1280px）
        // 最大限度的展示宽和高
        int width = getDefaultSize(screenWidth, widthMeasureSpec);
        int height = getDefaultSize(screenHeight, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }
}

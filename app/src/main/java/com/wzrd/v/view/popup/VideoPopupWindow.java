package com.wzrd.v.view.popup;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.been.Video;
import com.wzrd.m.db.manger.VideoManager;
import com.wzrd.v.activity.home.video.VideoActivity;

/**
 * Created by lk on 2018/3/4.
 */

public class VideoPopupWindow extends PopupWindow {
    private Context context ;
    private Video video;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.tv_sure:
                    VideoManager videoManager = VideoManager.getInstance(context);
                    videoManager.deleteVideo(video);
                    mSelectPopupWindow.dismiss();
                    break;
                case R.id.tv_unSure:
                    mSelectPopupWindow.dismiss();
                    break;
            }
        }
    };
    private SelectPopupWindow mSelectPopupWindow;

    public VideoPopupWindow(final Activity context, final Video video, final VideoActivity videoActivity) {
        super(context);
        this.context = context;
        this.video = video;
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
                dismiss();
                RenamePopupWindow popupWindow = new RenamePopupWindow(context, video,videoActivity);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });

        //删除
        tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                mSelectPopupWindow = new SelectPopupWindow(context, "删除此视频", "删除视频后将无法还原，您可以重新选择视频进行编辑", "取消", Color.parseColor("#FF007AFF"), "确定删除", Color.parseColor("#FFFD4137"), onClickListener);
                mSelectPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
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

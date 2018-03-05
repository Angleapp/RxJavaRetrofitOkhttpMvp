package com.wzrd.v.view.popup;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.been.Video;
import com.wzrd.m.db.manger.VideoManager;
import com.wzrd.m.utils.Utils;

/**
 * Created by lk on 2018/3/4.
 */

public class RenamePopupWindow extends PopupWindow {
    public RenamePopupWindow(final Activity context, final Video video) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.dailog_rename_pop, null, false);
        final TextView tv_hint = (TextView) binding.getRoot().findViewById(R.id.tv_hint);
        final EditText et_message = (EditText) binding.getRoot().findViewById(R.id.et_message);
        TextView tv_qx = (TextView) binding.getRoot().findViewById(R.id.tv_qx);
        TextView tv_sure = (TextView)binding.getRoot().findViewById(R.id.tv_sure);
        this.setContentView(binding.getRoot());
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        this.setWidth(3 * widthPixels / 4);
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);
        Drawable drawable = context.getResources().getDrawable(R.drawable.bg_shape);
        setBackgroundDrawable(drawable);

        et_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if(s.equals(video.getTitle())){
                    tv_hint.setVisibility(View.VISIBLE);
                    tv_hint.setText("命名重复");
                } else if(s.length()>15){
                    tv_hint.setVisibility(View.VISIBLE);
                }else{
                    tv_hint.setVisibility(View.GONE);
                    video.setTitle(s);
                    VideoManager videoManager = VideoManager.getInstance(context);
                    videoManager.updateVideo(video);
                }
            }
        });

        tv_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = et_message.getText().toString();
                if(s!=null&&s.length()>0&&s.length()<15){
                    dismiss();
                }else {
                    Utils.ToastShort(context,"命名不规范");
                }

            }
        });

    }




}

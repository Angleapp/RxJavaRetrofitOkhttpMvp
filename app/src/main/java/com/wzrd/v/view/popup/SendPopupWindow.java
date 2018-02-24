package com.wzrd.v.view.popup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.been.TSYSCONTANTS;
import com.wzrd.v.activity.calendar.CarcletarActivity;
import com.wzrd.v.view.CircleBarView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lk on 2018/2/23.
 */

public class SendPopupWindow extends PopupWindow {


    private View mMenuView;

    public SendPopupWindow(final Activity context, String message, final List<TSYSCONTANTS> list, final Boolean exituser) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.dailog_send_pop, null, false);
        final TextView tv_immediately = (TextView) binding.getRoot().findViewById(R.id.tv_immediately);
        TextView tv_timer = (TextView) binding.getRoot().findViewById(R.id.tv_timer);
        TextView tv_cancle = (TextView) binding.getRoot().findViewById(R.id.tv_cancle);
        TextView tv_message = (TextView) binding.getRoot().findViewById(R.id.tv_message);
        final TextView tv_tile = (TextView) binding.getRoot().findViewById(R.id.tv_tile);
        final TextView tv_sure = (TextView) binding.getRoot().findViewById(R.id.tv_sure);

        final TextView tv_message_send = (TextView) binding.getRoot().findViewById(R.id.tv_message_send);
        final LinearLayout ll_send = (LinearLayout) binding.getRoot().findViewById(R.id.ll_send);
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
        message = "给" + message + "\n 爱的消息";
        tv_message.setText(message);

        final String finalMessage = message;
        tv_immediately.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_sending.setVisibility(View.VISIBLE);
                ll_send.setVisibility(View.GONE);
                tv_message_send.setText(finalMessage);
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
                            ll_send.setVisibility(View.GONE);
                            rl_send_complece.setVisibility(View.VISIBLE);
                            tv_tile.setText(finalMessage);
                        }

                    }
                });
            }
        });
        /**
         * 取消
         */
        tv_cancle.setOnClickListener(new View.OnClickListener() {
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

        tv_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, CarcletarActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", (Serializable) list);
                intent.putExtras(bundle);
                intent.putExtra("id", exituser);
                context.startActivity(intent);
                dismiss();
            }
        });


    }

}


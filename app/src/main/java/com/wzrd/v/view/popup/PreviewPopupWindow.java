package com.wzrd.v.view.popup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.been.Poem;
import com.wzrd.m.been.TEXTIFORMATION;
import com.wzrd.m.db.manger.PoemManager;
import com.wzrd.m.db.manger.TextInformationManager;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.v.activity.home.poem.AddPoemActivity;
import com.wzrd.v.activity.homepage.WisdomActivity;
import com.wzrd.v.activity.preview.TextPreviewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lk on 2018/2/25.
 */

public class PreviewPopupWindow extends PopupWindow {
    private TextInformationManager manager;

    /**
     * @param context
     * @param type           来源 1 短视屏 2 结束语 3 线下 4智慧之语 5自拍 6口难开 7诗歌 8虚拟礼物 9(消息里面) 文字 10 语音 11 相册 12相机
     * @param previewmessage 预览显示内容
     * @param delmessage     删除预览内容
     */

    public PreviewPopupWindow(final Activity context, final int type, String previewmessage, String delmessage) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.dailog_prview_pop, null, false);

        TextView tv_prview = (TextView) binding.getRoot().findViewById(R.id.tv_prview);
        TextView tv_del = (TextView) binding.getRoot().findViewById(R.id.tv_del);
        LinearLayout ll_cancle = (LinearLayout) binding.getRoot().findViewById(R.id.ll_cancle);
        manager = new TextInformationManager(context);
        this.setContentView(binding.getRoot());
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        this.setWidth(9 * widthPixels / 10);
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);
        Drawable drawable = context.getResources().getDrawable(R.drawable.bg_shape);
        setBackgroundDrawable(drawable);
        tv_prview.setText(previewmessage);
        tv_del.setText(delmessage);
        tv_prview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perform(type, 0, context);

            }
        });
        tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perform(type, 1, context);

            }
        });
        ll_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

            }
        });


    }

    /**
     * 预览删除执行的方法
     *
     * @param type    1 短视屏 2 结束语 3 线下 4智慧之语 5自拍 6口难开 7诗歌 8虚拟礼物 9(消息里面) 文字 10 语音 11 相册 12相机
     * @param del     判断是删除还是预览 0 预览 1 删除
     * @param context
     */
    private void perform(int type, int del, Activity context) {
        switch (type) {
            case 1:
                break;
            case 2:
                String isexit = isexit(context, "2");
                if (!TextUtils.isEmpty(isexit)) {
                    if (del == 0) {
                        Intent intent2 = new Intent(context, TextPreviewActivity.class);
                        intent2.putExtra("type", "2");
                        context.startActivity(intent2);
                        dismiss();

                    } else {
                        manager.deleteid(isexit);
                        dismiss();
                    }
                } else {
                    dismiss();
                }


                break;
            case 3:
                dismiss();
                break;
            case 4:
                if (del == 0) {
                    Intent intent4 = new Intent(context, WisdomActivity.class);
                    intent4.putExtra("type", "popre");
                    context.startActivity(intent4);
                } else {
                    SharedPreferencesUtil.saveString(context, "wisdom", "");
                }
                dismiss();

                break;
            case 5:
                dismiss();
                break;
            case 6:
                String isexit1 = isexit(context, "1");
                if (!TextUtils.isEmpty(isexit1)) {
                    if (del == 0) {
                        Intent intent6 = new Intent(context, TextPreviewActivity.class);
                        intent6.putExtra("type", "6");
                        context.startActivity(intent6);
                        dismiss();

                    } else {
                        manager.deleteid(isexit1);
                        dismiss();
                    }
                } else {
                    dismiss();
                }
                break;
            case 7:
                PoemManager manager = new PoemManager(context);
                Poem poem = manager.getLastPoem();
                if (del == 0) {
                    if (poem == null) {
                        dismiss();
                    } else {
                        Intent intent = new Intent(context, AddPoemActivity.class);
                        intent.putExtra("id", poem.getId());
                        context.startActivity(intent);
                        dismiss();
                    }
                } else {
                    manager.deletePoem(poem);
                    dismiss();
                }
                break;
            case 8:
                dismiss();
                break;
            case 9:
                String isexit2 = isexit(context, "0");
                if (!TextUtils.isEmpty(isexit2)) {
                    if (del == 0) {
                        Intent intent9 = new Intent(context, TextPreviewActivity.class);
                        intent9.putExtra("type", "9");
                        context.startActivity(intent9);
                        dismiss();
                    } else {
                        this.manager.deleteid(isexit2);
                        dismiss();
                    }
                } else {
                    dismiss();
                }
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;


        }

    }

    /**
     * @param activity
     * @param sqltype  1 口难开 2结束语 0 消息
     * @return
     */
    private String isexit(Activity activity, String sqltype) {
        //  1 口难开 2结束语 0 消息
        List<TEXTIFORMATION> list = new ArrayList<>();
        list = manager.getBytype(sqltype);
        if (list != null && list.size() > 0) {
            return list.get(0).getT_pm_id();
        } else {
            return null;
        }
    }

}


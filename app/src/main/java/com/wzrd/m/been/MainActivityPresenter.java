package com.wzrd.m.been;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * [description about this class]
 *
 * @author zhangqil
 * @DATE 2017-04-12 14:55
 * @copyright Copyright 2010 RD information technology Co.,ltd.. All Rights Reserved.
 */

public class MainActivityPresenter {
    private Context mContext;

    public MainActivityPresenter(Context context) {
        mContext = context;
    }

    public void myTextOnClick(View view) {
        Toast.makeText(view.getContext(), "hello dataBind", Toast.LENGTH_SHORT).show();
    }

    public void testListenerBindingClick() {
        Toast.makeText(mContext, "打雷了 收衣服了。。。", Toast.LENGTH_SHORT).show();
    }

    public String getTextStr() {
        return "天气阴";
    }

    public String getMyName(View view, UserMessage user) {
//        Toast.makeText(view.getContext(), user.name, Toast.LENGTH_SHORT).show();
        EditText textView = (EditText) view;
        textView.setText(user.getNickname());
        return user.getNickname();
    }




}

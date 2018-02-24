package com.wzrd.v.activity.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wzrd.R;
import com.wzrd.m.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_setting1)
    LinearLayout llSetting1;
    @BindView(R.id.ll_setting2)
    LinearLayout llSetting2;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.iv_back, R.id.ll_setting1, R.id.ll_setting2, R.id.ll_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_setting1:
                Utils.ToastShort(this,"设置1");
                break;
            case R.id.ll_setting2:
                Utils.ToastShort(this,"设置2");
                break;
            case R.id.ll_about:
                Utils.ToastShort(this,"关于");
                break;
        }
    }
}

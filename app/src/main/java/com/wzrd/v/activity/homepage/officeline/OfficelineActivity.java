package com.wzrd.v.activity.homepage.officeline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.wzrd.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OfficelineActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officeline);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}

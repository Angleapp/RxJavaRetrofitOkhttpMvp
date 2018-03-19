package com.wzrd.v.activity.message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wzrd.R;
import com.wzrd.m.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageActivity extends AppCompatActivity {

    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        String photolj = SharedPreferencesUtil.getString(this, "photolj", "");
        Glide.with(this).load(photolj). error(R.mipmap.icon_signin_default)
                .into(iv);

    }
}

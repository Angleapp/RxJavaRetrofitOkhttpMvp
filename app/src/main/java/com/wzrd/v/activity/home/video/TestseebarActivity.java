package com.wzrd.v.activity.home.video;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wzrd.R;
import com.wzrd.v.view.SeekRangeBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestseebarActivity extends AppCompatActivity {

    @BindView(R.id.seekrangebar)
    SeekRangeBar seekrangebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testseebar);
        ButterKnife.bind(this);
        seekrangebar.setEditable(true);//设置是否可以滑动
        seekrangebar.setProgressLow(0);//设置初始为a
        seekrangebar.setProgressHigh(50);//设置初始位置b
        seekrangebar.setTotal(50);//设置总的proress值
        /**
         * 设置监听
         */
        seekrangebar.setOnSeekBarChangeListener(new SeekRangeBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekRangeBar seekBar, double progressLow, double progressHigh) {
                Log.e("progressLow","progressLow-->"+progressLow+"---progressHigh--->"+progressHigh);

            }
        });

    }
}

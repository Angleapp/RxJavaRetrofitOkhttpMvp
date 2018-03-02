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
        seekrangebar.setEditable(true);
        seekrangebar.setProgressLow(0);
        seekrangebar.setProgressHigh(50);
        seekrangebar.setTotal(50);
        seekrangebar.setOnSeekBarChangeListener(new SeekRangeBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekRangeBar seekBar, double progressLow, double progressHigh) {
                Log.e("progressLow","progressLow-->"+progressLow+"---progressHigh--->"+progressHigh);

            }
        });

    }
}

package com.wzrd.v.activity.home.video;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.v.view.SeekRangeBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoDetailActivity extends AppCompatActivity {

    @BindView(R.id.seekbar)
    SeekBar seekbar;
    @BindView(R.id.fullScreen)
    ImageView fullScreen;
    @BindView(R.id.handle)
    LinearLayout handle;
    @BindView(R.id.reduce)
    ImageView reduce;
    @BindView(R.id.text_time)
    TextView textTime;
    @BindView(R.id.plus)
    ImageView plus;
    @BindView(R.id.select_area)
    HorizontalScrollView selectArea;
    @BindView(R.id.seekrangebar)
    SeekRangeBar seekrangebar;
    @BindView(R.id.clip)
    ImageView clip;
    @BindView(R.id.text)
    ImageView text;
    @BindView(R.id.icon)
    ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);
        seekrangebar.setEditable(true);
        seekrangebar.setOnSeekBarChangeListener(new SeekRangeBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekRangeBar seekBar, double progressLow, double progressHigh) {

            }
        });
    }
}

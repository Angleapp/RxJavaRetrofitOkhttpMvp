package com.wzrd.v.activity.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wzrd.R;
import com.wzrd.m.services.RecordingService;
import com.yyx.beautifylib.utils.Constants;
import com.yyx.beautifylib.utils.SDCardUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoviceActivity extends AppCompatActivity {


    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    long timeWhenPaused = 0;
    @BindView(R.id.record_audio_iv_close)
    ImageView recordAudioIvClose;
    @BindView(R.id.record_audio_chronometer_time)
    Chronometer recordAudioChronometerTime;
    @BindView(R.id.record_audio_fab_record)
    ImageView recordAudioFabRecord;
    @BindView(R.id.tv_last)
    TextView tvLast;
    @BindView(R.id.iv_cancle)
    ImageView ivCancle;
    @BindView(R.id.iv_save)
    ImageView ivSave;
    @BindView(R.id.ll_wc)
    LinearLayout llWc;
    @BindView(R.id.record_audio_cv)
    LinearLayout recordAudioCv;
    @BindView(R.id.LL_image)
    LinearLayout LLImage;
    @BindView(R.id.ll_start)
    LinearLayout llStart;
    private boolean mStartRecording = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movice);
        ButterKnife.bind(this);
        llWc.setVisibility(View.GONE);
        tvLast.setVisibility(View.GONE);
    }

    private void onRecord(boolean start) {

        Intent intent = new Intent(this, RecordingService.class);

        if (start) {
            recordAudioFabRecord.setImageResource(R.mipmap.icon_poen_rec_stop);
            Toast.makeText(this, "开始录音...", Toast.LENGTH_SHORT).show();
            String videosDir = SDCardUtils.getSDBasePath() + Constants.VIDEOS_PATH;
            File folder = new File(videosDir);
            if (!folder.exists()) {
                folder.mkdir();
            }
//            recordAudioChronometerTime.clearComposingText();
            recordAudioChronometerTime.start();


        } else {
            //stop recording
            recordAudioFabRecord.setImageResource(R.mipmap.icon_poen_rec_stop);
            //mPauseButton.setVisibility(View.GONE);

            String base = recordAudioChronometerTime.getText().toString();
//            Toast.makeText(this, "录音结束..."+base, Toast.LENGTH_SHORT).show();
            recordAudioChronometerTime.stop();
            LLImage.setVisibility(View.GONE);
            llStart.setVisibility(View.GONE);
            tvLast.setVisibility(View.VISIBLE);
            llWc.setVisibility(View.VISIBLE);
            tvLast.setText(base);
            timeWhenPaused = 0;

            stopService(intent);


        }
    }


    @OnClick({R.id.record_audio_iv_close, R.id.ll_title, R.id.record_audio_chronometer_time, R.id.record_audio_fab_record, R.id.tv_last, R.id.iv_cancle, R.id.iv_save, R.id.ll_wc, R.id.record_audio_cv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.record_audio_iv_close:
                finish();
                break;

            case R.id.record_audio_fab_record:
                onRecord(mStartRecording);
                mStartRecording = !mStartRecording;
                break;
            case R.id.iv_cancle:
                break;
            case R.id.iv_save:
                break;
            case R.id.ll_wc:
                break;
            case R.id.record_audio_cv:
                break;
        }
    }
}

package com.wzrd.v.activity.message;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wzrd.R;
import com.wzrd.m.services.RecordingService;
import com.wzrd.m.utils.Utils;
import com.yyx.beautifylib.utils.Constants;
import com.yyx.beautifylib.utils.SDCardUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

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

    private BroadcastReceiver broadcastReceiver;
    private IntentFilter dynamic_filter;
    private String videoname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movice);
        ButterKnife.bind(this);
        llWc.setVisibility(View.GONE);
        tvLast.setVisibility(View.GONE);

        // 广播接收
        broadcastReceiver();
        // 注册广播
        registeBoardCast();
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
            startService(intent);
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
                deletdfile();
                finish();
                break;

            case R.id.record_audio_fab_record:
                requestperssion();

                break;
            case R.id.iv_cancle:
                deletdfile();
                finish();

                break;
            case R.id.iv_save:
                finish();

                break;
            case R.id.ll_wc:
                break;
            case R.id.record_audio_cv:
                break;
        }
    }


    /**
     * 广播接收
     */
    private void broadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (com.wzrd.m.utils.Constants.vidicerecond.equals(intent.getAction())) {

                    videoname = intent.getExtras().getString("videoname");
                    Log.e("videoname", "videoname" + videoname);
                }

            }
        };
    }


    /**
     * 注册广播
     */
    private void registeBoardCast() {
        dynamic_filter = new IntentFilter();
        dynamic_filter.addAction(com.wzrd.m.utils.Constants.vidicerecond);
        registerReceiver(broadcastReceiver, dynamic_filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    /**
     * 删除文件
     */
    private void deletdfile() {
        if(videoname!=null&&!"".equals(videoname)){
            String mFilePath = SDCardUtils.getSDBasePath() + Constants.VIDEOS_PATH + videoname;
            File file = new File(mFilePath);
            if (file.exists()) {
                file.delete();
            }
        }

    }

    /**
     * 请求权限
     */

    public void requestperssion() {


        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Observer<Boolean>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        /**
                         * aBoolean为true  同意权限
                         * aBoolean为false 没同意权限
                         */

                        if (!aBoolean) {
                            Utils.ToastShort(MoviceActivity.this, com.wzrd.m.utils.Constants.RECORD_AUDIO);
                        } else {
                            onRecord(mStartRecording);
                            mStartRecording = !mStartRecording;
                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}

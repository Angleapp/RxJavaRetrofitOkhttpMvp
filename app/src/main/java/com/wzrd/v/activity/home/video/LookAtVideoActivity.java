package com.wzrd.v.activity.home.video;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wzrd.R;
import com.wzrd.m.been.Video;
import com.wzrd.m.been.VideoContent;
import com.wzrd.m.db.manger.VideoContentManager;
import com.wzrd.m.db.manger.VideoManager;
import com.wzrd.m.utils.Constants;
import com.wzrd.m.utils.TextUtil;
import com.wzrd.m.utils.Utils;
import com.yyx.beautifylib.utils.ToastUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import pl.droidsonroids.gif.GifImageView;

public class LookAtVideoActivity extends AppCompatActivity {

    @BindView(R.id.video)
    VideoView mVideo;
    @BindView(R.id.content)
    TextView mContent;
    @BindView(R.id.line)
    GifImageView mLine;
    @BindView(R.id.emoticon)
    GifImageView mEmoticon;
    @BindView(R.id.pre)
    ImageView mPre;
    @BindView(R.id.play)
    ImageView mPlay;
    @BindView(R.id.next)
    ImageView mNext;
    @BindView(R.id.faceImage)
    ImageView mFaceImage;
    private boolean isPlay = false;//false 暂停 true播放
    private List<Video> mVideoList;
    private Video mCurrentVideo;
    private int currentPosition = 0;
    private int[] gifs = {
            R.drawable.line0,
            R.drawable.line1,
            R.drawable.line2,
            R.drawable.line3,
            R.drawable.line4,
            R.drawable.line6,
            R.drawable.line7,
            R.drawable.line8,
            R.drawable.line9
    };
    private int[] emoticons = {
            R.drawable.emoticon1, R.drawable.emoticon2, R.drawable.emoticon3, R.drawable.emoticon4, R.drawable.emoticon5, R.drawable.emoticon6,
            R.drawable.emoticon7, R.drawable.emoticon8, R.drawable.emoticon9, R.drawable.emoticon10
    };
    private int UPDATE_UI = 1;//更新UI
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE_UI) {
                int currentPosition = mVideo.getCurrentPosition();
                refreshContent(currentPosition);
                mHandler.sendEmptyMessageDelayed(UPDATE_UI, 500);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_at_video);
        VideoManager videoManager = VideoManager.getInstance(this);
        mVideoList = videoManager.getAllVideo();
        ButterKnife.bind(this);
        if (mVideoList != null && mVideoList.size() > 0) {
            currentPosition = mVideoList.size() - 1;
            mCurrentVideo = mVideoList.get(currentPosition);
            //初始化权限
            requestSDPermission();
            //设置视频路径
            initVideoPath();
        }
    }

    @OnClick({R.id.pre, R.id.play, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pre:
                if (currentPosition == 0) {
                    ToastUtils.toast(this, "已经是第一个了");
                } else {
                    currentPosition--;
                    mCurrentVideo = mVideoList.get(currentPosition);
                    initVideoPath();
                }
                break;
            case R.id.play:
                playVideo();
                break;
            case R.id.next:
                if (currentPosition == mVideoList.size() - 1) {
                    ToastUtils.toast(this, "已经是最后一个了");
                } else {
                    currentPosition++;
                    mCurrentVideo = mVideoList.get(currentPosition);
                    initVideoPath();
                }
                break;
        }
    }

    /**
     * 申请权限
     */
    private void requestSDPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions
                    .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
                                Utils.ToastShort(LookAtVideoActivity.this, Constants.RDWISDPREMISS);
                            } else {
                                initVideoPath();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } else {
            initVideoPath();
        }
    }

    /**
     * 初始化视频地址
     */

    private void initVideoPath() {
        String pathname = Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + mCurrentVideo.getVideo_path();
        File file = new File(pathname);
        mVideo.setVideoPath(pathname);
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(file.getAbsolutePath());
        Bitmap firstFrame = mmr.getFrameAtTime();
        mFaceImage.setImageBitmap(firstFrame);
        mFaceImage.setVisibility(View.VISIBLE);
        mPlay.setImageResource(R.mipmap.icon_play_start);
        mVideo.seekTo(0);
        mVideo.pause();
        isPlay = false;
        refreshContent(0);
        mVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer player) {
                mPlay.setImageResource(R.mipmap.icon_play_start);
                isPlay = false;
                mFaceImage.setVisibility(View.VISIBLE);
            }
        });

    }

    private void refreshContent(int time) {
        VideoContentManager contentManager = VideoContentManager.getInstance(this);
        VideoContent videoContent= contentManager.findVideoContentByVideoIdAndTime(mCurrentVideo.getId(), Utils.intToStr(time));
        if (videoContent != null) {
            if (!TextUtil.isEmpty(videoContent.getText())) {
                mContent.setVisibility(View.VISIBLE);
                mContent.setText(videoContent.getText());
            } else {
                mContent.setVisibility(View.GONE);
            }
            if (videoContent.getLineId() == -1) {
                mLine.setVisibility(View.VISIBLE);
                mLine.setImageResource(gifs[videoContent.getLineId()]);
            } else {
                mLine.setVisibility(View.GONE);
            }
            if (videoContent.getIconId()!=-1) {
                mEmoticon.setVisibility(View.VISIBLE);
               mFaceImage.setImageResource(emoticons[videoContent.getIconId()]);
            } else {
                mEmoticon.setVisibility(View.GONE);
            }

        }
    }

    /**
     * 播放视频
     */
    private void playVideo() {
        if (isPlay == false) {//此时为未播放
            mVideo.start();
            isPlay = true; //修改播放状态
            mPlay.setImageResource(R.mipmap.icon_play_pause);//设置播放状态
            mFaceImage.setVisibility(View.GONE);
            mHandler.sendEmptyMessageDelayed(UPDATE_UI, 500);
        } else if (isPlay == true) { //此时为正在播放
            mVideo.pause();
            isPlay = false; //修改播放状态
            mPlay.setImageResource(R.mipmap.icon_play_start);//设置播放状态
            mFaceImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initVideoPath();
                } else {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
}

package com.wzrd.v.activity.home.video;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wzrd.R;
import com.wzrd.m.been.EmoticonCode;
import com.wzrd.m.been.Video;
import com.wzrd.m.been.VideoContent;
import com.wzrd.m.db.manger.EmoticonCodeManager;
import com.wzrd.m.db.manger.VideoContentManager;
import com.wzrd.m.db.manger.VideoManager;
import com.wzrd.m.utils.Constants;
import com.wzrd.m.utils.DensityUtils;
import com.wzrd.m.utils.TextUtil;
import com.wzrd.m.utils.Utils;
import com.wzrd.v.view.MyVideoView;
import com.wzrd.v.view.SeekRangeBar;
import com.wzrd.v.view.popup.VideoSavePopwindow;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import pl.droidsonroids.gif.GifImageView;

public class VideoDetailActivity extends AppCompatActivity {

    //    头部的关闭
    @BindView(R.id.close)
    ImageView mClose;
    //    保存编辑
    @BindView(R.id.save)
    ImageView mSave;
    //    toolbar的布局
    @BindView(R.id.toolbar)
    RelativeLayout mToolbar;
    //    视频控件
    @BindView(R.id.videoView)
    MyVideoView mVideoView;
    //    显示第一帧图片
    @BindView(R.id.first_frame)
    ImageView mFirstFrame;
    //    删除视频下方的文本编辑
    @BindView(R.id.delete)
    ImageView mDelete;
    @BindView(R.id.video_text)
    EditText mVideoText;
    @BindView(R.id.contentShow)
    RelativeLayout mContentShow;
    @BindView(R.id.video_layout)
    LinearLayout mVideoLayout;
    @BindView(R.id.start_play)
    ImageView mStartPlay;
    @BindView(R.id.seekbar)
    SeekBar mSeekbar;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.fullScreen)
    ImageView mFullScreen;
    @BindView(R.id.handle)
    LinearLayout mHandle;
    @BindView(R.id.reduce)
    ImageView mReduce;
    @BindView(R.id.text_time)
    TextView mTextTime;
    @BindView(R.id.plus)
    ImageView mPlus;
    @BindView(R.id.selectTime)
    LinearLayout mSelectTime;
    @BindView(R.id.seekRangeBar)
    SeekRangeBar mSeekRangeBar;
    @BindView(R.id.selectTextType)
    LinearLayout mSelectTextType;
    @BindView(R.id.clip)
    ImageView mClip;
    @BindView(R.id.text)
    ImageView mText;
    @BindView(R.id.icon)
    ImageView mIcon;
    @BindView(R.id.bottom_layout)
    LinearLayout mBottomLayout;
    @BindView(R.id.emoticon)
    ImageView mEmoticon;
    @BindView(R.id.text_style)
    LinearLayout mTextStyle;
    @BindView(R.id.line_gif)
    GifImageView mLineGif;
    private Unbinder mUnbinder;
    private int PLAY_BNT_STATE = 0;//0  未播放 1 播放
    private boolean isFullScreen = false;//是否是全屏
    //刷新UI的标志
    private static final int UPDATE_UI = 1;
    private boolean isEditText = false;//是否在编辑文本
    private int currentEditContent = 0;//0 裁剪 1 文本 2 表情
    private VideoContentManager mVideoContentManager;
    private int preSelected = 0;
    private Video mVideo;//传过来的视频
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
    private int startTime = 0;
    private int endTime = 0;
    private int lineId = -1;
    private String iconPath = "";
    private Bitmap mBitmap = null;
    private boolean isBack = false;
    private int max = 0;//视频总共的毫秒值
    private int high;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        VideoManager videoManager = VideoManager.getInstance(this);
        mVideo = videoManager.findVideoById(id);
        mSeekRangeBar.setEditable(true);
        mUnbinder = ButterKnife.bind(this);
        //模拟数据
        setData();
        //初始化权限
        requestSDPermission();
        //设置视频路径
        initVideoPath();
        //设置进度条的进度
        synchScrollSeekBarAndTime();
        //初始化view
        initViewData();
    }

    /**
     * 模拟数据
     */
    private void setData() {
        EmoticonCodeManager emoticonCodeManager = EmoticonCodeManager.getInstance(this);
        List<EmoticonCode> emoticonCodes = emoticonCodeManager.getAllEmoticonCode();
        if (emoticonCodes == null || emoticonCodes.size() == 0) {
            emoticonCodes.add(new EmoticonCode(Utils.getuuid(), "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520147822485&di=24354639016c4b828e973eca2e74929a&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F5bafa40f4bfbfbed4eff075a72f0f736afc31f74.jpg"));
            emoticonCodes.add(new EmoticonCode(Utils.getuuid(), "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4046513553,596615943&fm=200&gp=0.jpg"));
            emoticonCodes.add(new EmoticonCode(Utils.getuuid(), "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520147822906&di=1a6d7581dbb51915e65ecae8ab606dfb&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F279759ee3d6d55fb58f8020067224f4a20a4ddde.jpg"));
            emoticonCodes.add(new EmoticonCode(Utils.getuuid(), "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520147822906&di=ac2bf5cd66582c34fa580cd8887b25a7&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F6609c93d70cf3bc7058ce4c9db00baa1cd112a02.jpg"));
            emoticonCodes.add(new EmoticonCode(Utils.getuuid(), "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520147822905&di=162d00b36a30bd3894ef4cca285e32b2&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F2fdda3cc7cd98d10de02a8132b3fb80e7bec903a.jpg"));
            emoticonCodes.add(new EmoticonCode(Utils.getuuid(), "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520147822905&di=c5fee25aa3a104a3ef6b49663d48a197&imgtype=0&src=http%3A%2F%2Fimg11.weikeimg.com%2Fdata%2Fuploads%2F2014%2F03%2F11%2F1733328762531ea9cb3789d.gif"));
            emoticonCodes.add(new EmoticonCode(Utils.getuuid(), "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520147822905&di=6243751eb9ae971b30d6e5791b9cbc7b&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fd53f8794a4c27d1ec79abde111d5ad6eddc43852.jpg"));
            emoticonCodes.add(new EmoticonCode(Utils.getuuid(), "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520147822911&di=ecfc62c4bbf491d637a9e9e3e004a8fe&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fe850352ac65c1038aa3ba5e4b8119313b07e894b.jpg"));
            emoticonCodeManager.insertMultEmoticonCode(emoticonCodes);
        }
    }

    /**
     * 初始化View的值
     */
    private void initViewData() {
        mTime.setText("00:00");
        mSeekRangeBar.setProgressLow(0);
        high = mVideoView.getDuration();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                high = mediaPlayer.getDuration();
                mSeekRangeBar.setTotal(high);
                mSeekRangeBar.setProgressHigh(high);
                endTime = high;
                max = high;
            }
        });
        mSeekRangeBar.setColora(Color.WHITE);
        mSeekRangeBar.setFontSizea(24);
        mSeekRangeBar.setColorb(Color.parseColor("#FF007AFF"));
        updateTime(mTextTime, mVideoView.getDuration());
        mVideoContentManager = VideoContentManager.getInstance(this);
    }

    /**
     * 进度条的拖动时间
     */
    private void synchScrollSeekBarAndTime() {
        mSeekbar.setMax(mVideoView.getDuration());
        mSeekbar.setProgress(0);
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar bar, int i, boolean b) {
                //进度改变
                updateTime(mTime, i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar bar) {
                //拖动进度条
                mHandler.removeMessages(UPDATE_UI);//停止刷新UI
            }

            @Override
            public void onStopTrackingTouch(SeekBar bar) {
                //停止拖动进度条
                int progress = mSeekbar.getProgress();
                mVideoView.seekTo(progress);
                mHandler.sendEmptyMessage(UPDATE_UI);
            }
        });
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
                                Utils.ToastShort(VideoDetailActivity.this, Constants.RDWISDPREMISS);
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
        mSeekRangeBar.setOnSeekBarChangeListener(new SeekRangeBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekRangeBar seekBar, double progressLow, double progressHigh) {
                startTime = (int) progressLow;
                endTime = (int) progressHigh;
                //设置终止时间
                updateTime(mTextTime, (int) progressHigh);
            }
        });
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + mVideo.getVideo_path();
        File file = new File(path);
        Log.e("====", path + file.exists());

        mVideoView.setVideoPath(path);
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(file.getAbsolutePath());
        Bitmap firstFrame = mmr.getFrameAtTime();
        mFirstFrame.setImageBitmap(firstFrame);

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer player) {
                mStartPlay.setImageResource(R.mipmap.icon_video_replay);
                PLAY_BNT_STATE = 0;
                mFirstFrame.setVisibility(View.VISIBLE);
            }
        });
        mVideoView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });
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

    @OnClick({R.id.close, R.id.save, R.id.start_play, R.id.fullScreen, R.id.reduce, R.id.plus, R.id.clip, R.id.text, R.id.icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close:
                if (isBack) {
                    finish();
                } else {
                    mContentShow.setVisibility(View.GONE);
                }
                break;
            case R.id.save:
                saveEditData();
                break;
            case R.id.start_play:
                //播放视频
                playVideo();
                break;
            case R.id.fullScreen:
                //全屏
                setFullScreen();
                break;
            case R.id.reduce:
                if (endTime >= 1000) {
                    endTime = endTime - 1000;
                    mSeekRangeBar.setProgressHigh(endTime);
                    updateTime(mTextTime, endTime);
                }
                break;
            case R.id.plus:
                if (endTime < max) {
                    endTime = endTime + 1000;
                    mSeekRangeBar.setProgressHigh(endTime);
                    updateTime(mTextTime, endTime);
                }
                break;
            case R.id.clip:
                currentEditContent = 0;
                setTimeAreaClip();
                break;
            case R.id.text:
                currentEditContent = 1;
                setTimeAreaText();
                break;
            case R.id.icon:
                currentEditContent = 2;
                setTimeAreaIcon();
                break;
        }
    }

    /**
     * 保存编辑的数据
     */
    private void saveEditData() {
        VideoSavePopwindow popupwindow = new VideoSavePopwindow(VideoDetailActivity.this);
        popupwindow.showAtLocation(mBottomLayout, Gravity.CENTER, 0, 0);
        List<VideoContent> list = new ArrayList<>();
        if (currentEditContent == 0) {//裁剪

        } else if (currentEditContent == 1) {//文本
            for (int i = startTime; i <= endTime; i += 1000) {
                VideoContent videoContent = new VideoContent(Utils.getuuid(), mVideo.getId(), intToStr(i), lineId, "", "", mVideoText.getText().toString());
                list.add(videoContent);
            }
        } else {//表情
            for (int i = startTime; i <= endTime; i += 1000) {
                VideoContent videoContent = new VideoContent(Utils.getuuid(), mVideo.getId(), intToStr(i), -1, "", iconPath, "");
                list.add(videoContent);
            }
        }
        mVideoView.setEnabled(false);
        mClose.setImageResource(R.mipmap.icon_titlebar_back);
        isBack = true;
        mContentShow.setVisibility(View.GONE);
        mSeekRangeBar.setVisibility(View.GONE);
        mSelectTextType.setVisibility(View.GONE);
        mSelectTime.setVisibility(View.GONE);
        mSave.setVisibility(View.GONE);
        mDelete.setVisibility(View.GONE);
        mLineGif.setVisibility(View.GONE);
        mEmoticon.setVisibility(View.GONE);
        mText.setImageResource(R.mipmap.icon_video_text);
        mClip.setEnabled(true);
        mIcon.setEnabled(true);
        mText.setEnabled(true);

        mVideoContentManager.insertMultVideoContent(list);

    }

    /**
     * 设置表情图标
     */
    private void setTimeAreaClip() {
        if (isEditText) {
            mClip.setImageResource(R.mipmap.icon_video_cut);
            mContentShow.setVisibility(View.GONE);//显示视屏上的文本
            mSeekRangeBar.setVisibility(View.GONE);
            mSelectTextType.setVisibility(View.GONE);
            mSelectTime.setVisibility(View.GONE);
            mTextStyle.setVisibility(View.VISIBLE);
            mToolbar.setVisibility(View.GONE);
            mIcon.setEnabled(true);
            mText.setEnabled(true);
            isEditText = false;
        } else {
            mClip.setImageResource(R.mipmap.icon_video_cut_edit);
            mSeekRangeBar.setVisibility(View.VISIBLE);
            mSelectTime.setVisibility(View.VISIBLE);
            mToolbar.setVisibility(View.VISIBLE);
            mClose.setImageResource(R.mipmap.icon_video_close);
            mSave.setVisibility(View.VISIBLE);
            isBack = false;
            mTextStyle.setVisibility(View.GONE);
            updateList("裁剪");
            mText.setEnabled(false);
            mClip.setEnabled(false);
            isEditText = true;
        }
    }

    /**
     * 设置表情图标
     */
    private void setTimeAreaIcon() {
        if (isEditText) {
            mIcon.setImageResource(R.mipmap.icon_video_expression);
            mContentShow.setVisibility(View.GONE);//显示视屏上的文本
            mSeekRangeBar.setVisibility(View.GONE);
            mSelectTextType.setVisibility(View.GONE);
            mSelectTime.setVisibility(View.GONE);
            mTextStyle.setVisibility(View.VISIBLE);
            mToolbar.setVisibility(View.GONE);
            mClip.setEnabled(true);
            mText.setEnabled(true);
            isEditText = false;
        } else {
            mClose.setImageResource(R.mipmap.icon_video_close);
            mSave.setVisibility(View.VISIBLE);
            isBack = false;
            mIcon.setImageResource(R.mipmap.icon_video_expression_edit);
            mSeekRangeBar.setVisibility(View.VISIBLE);
            mSelectTime.setVisibility(View.VISIBLE);
            mToolbar.setVisibility(View.VISIBLE);
            mTextStyle.setVisibility(View.GONE);
            updateList("表情");
            mText.setEnabled(false);
            mClip.setEnabled(false);
            isEditText = true;
        }
    }

    /**
     * 播放视频
     */
    private void playVideo() {
        if (PLAY_BNT_STATE == 0) {//此时为未播放
            mVideoView.start();
            PLAY_BNT_STATE = 1; //修改播放状态
            mHandler.sendEmptyMessage(UPDATE_UI); //开启刷新UI
            mStartPlay.setImageResource(R.mipmap.icon_video_close);//设置播放状态
            mFirstFrame.setVisibility(View.GONE);
        } else if (PLAY_BNT_STATE == 1) { //此时为正在播放
            mVideoView.pause();
            PLAY_BNT_STATE = 0; //修改播放状态
            mHandler.removeMessages(UPDATE_UI);//停止刷新UI
            mStartPlay.setImageResource(R.mipmap.icon_video_play);//设置播放状态
            mFirstFrame.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置文本
     */
    private void setTimeAreaText() {
        if (isEditText) {
            mText.setImageResource(R.mipmap.icon_video_text);
            mContentShow.setVisibility(View.GONE);//显示视屏上的文本
            mSeekRangeBar.setVisibility(View.GONE);
            mSelectTextType.setVisibility(View.GONE);
            mSelectTime.setVisibility(View.GONE);
            mToolbar.setVisibility(View.GONE);
            mTextStyle.setVisibility(View.VISIBLE);
            mClip.setEnabled(true);
            mIcon.setEnabled(true);
            isEditText = false;
        } else {
            mClose.setImageResource(R.mipmap.icon_video_close);
            mSave.setVisibility(View.VISIBLE);
            isBack = false;
            mText.setImageResource(R.mipmap.icon_video_text_edit);
            mSeekRangeBar.setVisibility(View.VISIBLE);
            mSelectTime.setVisibility(View.VISIBLE);
            mToolbar.setVisibility(View.VISIBLE);
            mTextStyle.setVisibility(View.GONE);
            updateList("文本");
            mClip.setEnabled(false);
            mIcon.setEnabled(false);
            isEditText = true;
        }
    }

    /**
     * 更新列表
     *
     * @param type
     */
    private void updateList(String type) {
        if (mSelectTextType.getChildCount() != 0) {
            mSelectTextType.removeAllViews();
        }
        mSelectTextType.setVisibility(View.VISIBLE);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if ("裁剪".equals(type)) {
            updataClipList(layoutInflater, layoutParams);
        } else if ("文本".equals(type)) {//文本
            updataTextList(layoutInflater, layoutParams);
        } else {//表情
            updataIconList(layoutInflater, layoutParams);
        }

    }

    private void updataClipList(LayoutInflater layoutInflater, LinearLayout.LayoutParams layoutParams) {
        List<Bitmap> list = getBitmapsFromVideo(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + mVideo.getVideo_path());
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                final int index = i;
                final Bitmap bitmap = list.get(i);
                View view = layoutInflater.inflate(R.layout.video_detail_list_item, null, false);
                view.setLayoutParams(layoutParams);
                final ImageView imageView = (ImageView) view.findViewById(R.id.icon);
                imageView.setImageBitmap(bitmap);
               /* view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View v = mSelectTextType.getChildAt(preSelected);
                        v.setSelected(false);
                        view.setSelected(true);
                        preSelected = index;
                        //在屏幕编辑区显示表情符号
                        mContentShow.setVisibility(View.VISIBLE);
                        mEmoticon.setVisibility(View.VISIBLE);
                        mDelete.setVisibility(View.GONE);
                        mVideoText.setVisibility(View.GONE);
                        mLineGif.setVisibility(View.GONE);

                    }
                });*/
                mSelectTextType.addView(view);
            }
        } else {
            mSelectTextType.setVisibility(View.GONE);
        }
    }

    /**
     * 更新表情list
     *
     * @param layoutInflater
     * @param layoutParams
     */
    private void updataIconList(LayoutInflater layoutInflater, LinearLayout.LayoutParams layoutParams) {
        EmoticonCodeManager emoticonCodeManager = EmoticonCodeManager.getInstance(this);
        List<EmoticonCode> allEmoticonCode = emoticonCodeManager.getAllEmoticonCode();
        if (allEmoticonCode != null && allEmoticonCode.size() > 0) {
            for (int i = 0; i < allEmoticonCode.size(); i++) {
                final int index = i;
                final EmoticonCode emoticonCode = allEmoticonCode.get(i);
                View view = layoutInflater.inflate(R.layout.video_detail_list_item, null, false);
                view.setLayoutParams(layoutParams);
                final ImageView imageView = (ImageView) view.findViewById(R.id.icon);
                Glide.with(this).load(emoticonCode.getPath()).into(imageView);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View v = mSelectTextType.getChildAt(preSelected);
                        v.setSelected(false);
                        view.setSelected(true);
                        preSelected = index;
                        //在屏幕编辑区显示表情符号
                        mContentShow.setVisibility(View.VISIBLE);
                        mEmoticon.setVisibility(View.VISIBLE);
                        mDelete.setVisibility(View.GONE);
                        mVideoText.setVisibility(View.GONE);
                        mLineGif.setVisibility(View.GONE);
                        iconPath = emoticonCode.getPath();
                        Glide.with(VideoDetailActivity.this).load(emoticonCode.getPath()).into(mEmoticon);
                    }
                });
                mSelectTextType.addView(view);
            }
        } else {
            mSelectTextType.setVisibility(View.GONE);
        }
    }

    /**
     * 更新文本list
     *
     * @param layoutInflater
     * @param layoutParams
     */
    private void updataTextList(LayoutInflater layoutInflater, LinearLayout.LayoutParams layoutParams) {
        View view = layoutInflater.inflate(R.layout.video_detail_list_item_text, null, false);
        view.setLayoutParams(layoutParams);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = mSelectTextType.getChildAt(preSelected);
                v.setSelected(false);
                view.setSelected(true);
                preSelected = 0;
                //在屏幕编辑区显示表情符号
                mContentShow.setVisibility(View.VISIBLE);
                mEmoticon.setVisibility(View.GONE);
                mDelete.setVisibility(View.VISIBLE);
                mVideoText.setVisibility(View.VISIBLE);
                mLineGif.setVisibility(View.GONE);
            }
        });
        mSelectTextType.addView(view);
        for (int i = 0; i < gifs.length; i++) {
            final int index = i + 1;
            View line = layoutInflater.inflate(R.layout.video_detail_list_item_line, null, false);
            line.setLayoutParams(layoutParams);
            ImageView imageView = (ImageView) line.findViewById(R.id.line);
            Glide.with(this).load(gifs[i]).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            line.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //在屏幕编辑区显示表情符号
                    mContentShow.setVisibility(View.VISIBLE);
                    mEmoticon.setVisibility(View.GONE);
                    mDelete.setVisibility(View.VISIBLE);
                    mVideoText.setVisibility(View.VISIBLE);
                    mLineGif.setVisibility(View.VISIBLE);
                    mLineGif.setImageResource(gifs[index - 1]);
                    View v = mSelectTextType.getChildAt(preSelected);
                    v.setSelected(false);
                    view.setSelected(true);
                    lineId = index - 1;
                    preSelected = index;
                }
            });
            mSelectTextType.addView(line);
        }
    }

    /**
     * 设置全屏
     */
    private void setFullScreen() {
        if (isFullScreen) {
            //切换为竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            //改变全屏图标
            mFullScreen.setImageResource(R.mipmap.icon_video_fullscreen);
            //显示底部操作布局
            mBottomLayout.setVisibility(View.VISIBLE);
            isFullScreen = false;
        } else {
            //切换为横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            //改变全屏图标
            mFullScreen.setImageResource(R.mipmap.icon_video_close);
            //显示底部操作布局
            mBottomLayout.setVisibility(View.GONE);
//            mVideoView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            isFullScreen = true;
        }
    }

    //配置横竖屏切换
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //当屏幕方向是横屏的时候,我们应该对VideoView以及包裹VideoView的布局（也就是对整体）进行拉伸
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,"1");
        } else {  //当屏幕方向是竖屏的时候，竖屏的时候的高我们需要把dp转为px
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dip2px(this, 240),"2");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.suspend();
        }
    }

    /**
     * 设置VideoView的大小
     *
     * @param width
     * @param height
     * type 1 全屏 2 书评
     */
    private void setVideoViewScale(int width, int height,String type) {
        //获取VideoView宽和高
        ViewGroup.LayoutParams layoutParams = mVideoView.getLayoutParams();
        //赋值给VideoView的宽和高
        layoutParams.width = width;
        layoutParams.height = height;
        //设置VideoView的宽和高
        if("1".equals(type)){
            mFirstFrame.setVisibility(View.GONE);
        }else{
            mFirstFrame.setVisibility(View.VISIBLE);
        }
        mVideoView.setLayoutParams(layoutParams);
    }

    /**
     * 时间的格式化 * @param textView * @param millisecond
     */
    public void updateTime(TextView textView, int millisecond) {
        String str = intToStr(millisecond);
        textView.setText(str);
    }

    private String intToStr(int millisecond) {
        int second = millisecond / 1000; //总共换算的秒
        int hh = second / 3600; //小时
        int mm = second % 3600 / 60; //分钟
        int ss = second % 60; //时分秒中的秒的得数
        String str = null;
        if (hh != 0) { //如果是个位数的话，前面可以加0 时分秒
            str = String.format("%02d:%02d:%02d", hh, mm, ss);
        } else {
            str = String.format("%02d:%02d", mm, ss);
        }
        return str;
    }


    //更新UI的handler
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE_UI) {
                int currentPosition = mVideoView.getCurrentPosition();
                int totalDuration = mVideoView.getDuration();
                mSeekbar.setMax(totalDuration);
                updateTime(mTime, currentPosition);
                updateTime(mTextTime, totalDuration);
                startTime = currentPosition;
                mSeekRangeBar.setProgressLow(currentPosition);
                mSeekbar.setProgress(currentPosition);
                setAlreadyContent(currentPosition);
                mHandler.sendEmptyMessageDelayed(UPDATE_UI, 500);
            }

        }
    };

    private void setAlreadyContent(int currentPosition) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = 10;
        layoutParams.rightMargin = 10;
        layoutParams.topMargin = 5;
        layoutParams.leftMargin = 5;
        List<VideoContent> videoContents = mVideoContentManager.findVideoContentByVideoId(mVideo.getId(), intToStr(currentPosition));
        if (videoContents != null && videoContents.size() > 0) {
            mTextStyle.setVisibility(View.VISIBLE);
            mTextStyle.removeAllViews();
            for (int i = 0; i < videoContents.size(); i++) {
                VideoContent videoContent = videoContents.get(i);
                if (!TextUtil.isEmpty(videoContent.getText())) {
                    TextView textView = new TextView(VideoDetailActivity.this);
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(15);
                    textView.setPadding(13, 10, 13, 10);
                    textView.setText(videoContent.getText());
                    textView.setGravity(Gravity.CENTER);
                    if (videoContent.getLineId() != -1) {
                        textView.setCompoundDrawablePadding(10);
                        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, gifs[videoContent.getLineId()]);
                    }
                    mTextStyle.addView(textView);
                }
                if (!TextUtil.isEmpty(videoContent.getIconPath())) {
                    ImageView imageView = new ImageView(VideoDetailActivity.this);
                    imageView.setLayoutParams(layoutParams);
                    Glide.with(VideoDetailActivity.this).load(videoContent.getIconPath()).into(imageView);
                    mTextStyle.addView(imageView);
                }
            }
        } else {
            mTextStyle.setVisibility(View.GONE);
        }
    }

    public List<Bitmap> getBitmapsFromVideo(String clipPath) {
        File file = new File(clipPath);
        List<Bitmap> list = new ArrayList<>();
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();

        retriever.setDataSource(file.getAbsolutePath());

        // 取得视频的长度(单位为秒)
        int seconds = max / 1000;
        // 得到每一秒时刻的bitmap比如第一秒,第二秒
        for (int i = 1; i <= seconds; i=i+8) {
            Bitmap bitmap = retriever.getFrameAtTime(i * 1000 * 1000);
            list.add(bitmap);
        }
        return list;
    }


    /**
     * 保存帧图片
     *
     * @param savePath
     * @param bitmap
     */
    private void saveFrameBitmap(String savePath, Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(savePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
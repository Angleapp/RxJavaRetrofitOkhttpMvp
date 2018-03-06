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
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.wzrd.m.been.Video;
import com.wzrd.m.been.VideoContent;
import com.wzrd.m.db.manger.VideoContentManager;
import com.wzrd.m.db.manger.VideoManager;
import com.wzrd.m.utils.Constants;
import com.wzrd.m.utils.DensityUtils;
import com.wzrd.m.utils.TextUtil;
import com.wzrd.m.utils.Utils;
import com.wzrd.v.view.MyVideoView;
import com.wzrd.v.view.SeekRangeBar;
import com.wzrd.v.view.popup.VideoSavePopwindow;
import com.yyx.beautifylib.utils.ToastUtils;

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


    @BindView(R.id.close)
    ImageView mClose;
    @BindView(R.id.save)
    ImageView mSave;
    @BindView(R.id.toolbar)
    RelativeLayout mToolbar;
    @BindView(R.id.videoView)
    MyVideoView mVideoView;
    @BindView(R.id.first_frame)
    ImageView mFirstFrame;
    @BindView(R.id.video_text)
    EditText mVideoText;
    @BindView(R.id.delete)
    ImageView mDelete;
    @BindView(R.id.contentShow)
    RelativeLayout mContentShow;
    @BindView(R.id.line_gif)
    GifImageView mLineGif;
    @BindView(R.id.emoticon)
    GifImageView mEmoticon;
    @BindView(R.id.videoContent)
    LinearLayout mVideoContent;
    @BindView(R.id.start_play)
    ImageView mStartPlay;
    @BindView(R.id.seekbar)
    SeekBar mSeekbar;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.fullScreen)
    ImageView mFullScreen;
    @BindView(R.id.control)
    LinearLayout mControl;
    @BindView(R.id.reduce)
    ImageView mReduce;
    @BindView(R.id.text_time)
    TextView mTextTime;
    @BindView(R.id.plus)
    ImageView mPlus;
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
    @BindView(R.id.handleArea)
    LinearLayout mHandlerArea;
    private Unbinder mUnbinder;
    private int PLAY_BNT_STATE = 0;//0  未播放 1 播放
    private boolean isFullScreen = false;//是否是全屏
    //刷新UI的标志
    private static final int UPDATE_UI = 1;
    private int UPDATE_CLIP_UI = 2;//更新视频页面
    private boolean isEditText = false;//是否在编辑文本
    private int currentEditContent = 0;//0 裁剪 1 文本 2 表情
    private VideoContentManager mVideoContentManager;
    private int preSelected = 0;
    private Video mVideo;//传过来的视频
    private int[] gifs = {
            R.drawable.line0, R.drawable.line1, R.drawable.line2, R.drawable.line3,
            R.drawable.line4, R.drawable.line6, R.drawable.line7, R.drawable.line8,
            R.drawable.line9
    };
    private int[] emoticons = {
            R.drawable.emoticon1, R.drawable.emoticon2, R.drawable.emoticon3, R.drawable.emoticon4, R.drawable.emoticon5, R.drawable.emoticon6,
            R.drawable.emoticon7, R.drawable.emoticon8, R.drawable.emoticon9, R.drawable.emoticon10
    };
    private List<Bitmap> list = new ArrayList<>();
    private List<Bitmap> bitmaps = new ArrayList<>();
    private int startTime = 0;
    private int endTime = 0;
    private int lineId = -1;
    private int iconId = -1;
    private Bitmap mBitmap = null;
    private boolean isBack = false;
    private int max = 0;//视频总共的毫秒值
    private int high;
    private VideoContent mCurrentVideoContent;
    private Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        String type = intent.getStringExtra("type");
        String title = intent.getStringExtra("title");
        mVideo = new Video(Utils.getuuid(),type,path,"",title,0);
        mSeekRangeBar.setEditable(true);
        mUnbinder = ButterKnife.bind(this);
        //获取数据
        setData();
        //初始化权限
        requestSDPermission();
        initView();
        //设置视频路径
        initVideoPath();
        //设置进度条的进度
        synchScrollSeekBarAndTime();
        //初始化view
        initViewData();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        setToolbarContent(1);
        setVideoContentLayout();
        setHandlerAreaContent();
    }

    /**
     * 设置操作区域布局
     */
    private void setHandlerAreaContent() {
        mHandlerArea.setVisibility(View.INVISIBLE);//操作内容区域
    }

    /**
     * 设置视频内容布局
     */
    private void setVideoContentLayout() {
        if (mCurrentVideoContent != null) {
            mVideoContent.setVisibility(View.VISIBLE);//视频内容布局
            if (!TextUtil.isEmpty(mCurrentVideoContent.getText())) {
                mVideoText.setVisibility(View.VISIBLE);
                mDelete.setVisibility(View.VISIBLE);
                mVideoText.setText(mCurrentVideoContent.getText());
            } else {
                mDelete.setVisibility(View.GONE);
                mVideoText.setVisibility(View.GONE);
            }
            if (mCurrentVideoContent.getLineId() != -1) {
                mLineGif.setVisibility(View.VISIBLE);
                mLineGif.setImageResource(gifs[mCurrentVideoContent.getLineId()]);
            } else {
                mLineGif.setVisibility(View.GONE);
            }
            if (mCurrentVideoContent.getIconId() != -1) {
                mEmoticon.setVisibility(View.VISIBLE);
                mEmoticon.setImageResource(emoticons[mCurrentVideoContent.getIconId()]);
            } else {
                mEmoticon.setVisibility(View.GONE);
            }
        } else {
            mVideoContent.setVisibility(View.GONE);//视频内容布局
            mVideoText.setVisibility(View.GONE);
            mDelete.setVisibility(View.GONE);
            mLineGif.setVisibility(View.GONE);
            mEmoticon.setVisibility(View.GONE);
        }
    }

    /**
     * 设置toolbar布局
     */
    private void setToolbarContent(int num) {
        if (num == 1) {
            mToolbar.setVisibility(View.GONE);//toolbar布局
            mClose.setImageResource(R.mipmap.icon_video_close);
            mSave.setVisibility(View.VISIBLE);
            isBack = false;
        } else if (num == 2) {
            mToolbar.setVisibility(View.VISIBLE);//toolbar布局
            mClose.setImageResource(R.mipmap.icon_video_close);
            mSave.setVisibility(View.VISIBLE);
            isBack = false;
        } else {
            mToolbar.setVisibility(View.VISIBLE);
            mClose.setImageResource(R.mipmap.icon_titlebar_back);
            mSave.setVisibility(View.GONE);
            isBack = true;
        }
    }

    /**
     * 获取数据
     */
    private void setData() {
        mVideoContentManager = VideoContentManager.getInstance(this);
        mCurrentVideoContent = mVideoContentManager.findVideoContentByVideoIdAndTime(mVideo.getId(), Utils.intToStr(0));
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
        String path =mVideo.getVideo_path();
        File file = new File(path);

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
                    setToolbarContent(1);
                    mHandlerArea.setVisibility(View.INVISIBLE);
                    //当前选中的按钮状态的更改
                    if (currentEditContent == 0) {
                        mClip.setImageResource(R.mipmap.icon_video_cut);
                    } else if (currentEditContent == 1) {
                        mText.setImageResource(R.mipmap.icon_video_text);
                    } else {
                        mIcon.setImageResource(R.mipmap.icon_video_expression);
                    }
                    mCurrentVideoContent = null;
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
        if (mCurrentVideoContent != null) {
            VideoSavePopwindow popupwindow = new VideoSavePopwindow(VideoDetailActivity.this);
            popupwindow.showAtLocation(mBottomLayout, Gravity.CENTER, 0, 0);
            List<VideoContent> list = new ArrayList<>();
            for (int i = startTime; i <= endTime; i = i + 1000) {
                mCurrentVideoContent.setTime(Utils.intToStr(i));
                VideoContent videoContent = mVideoContentManager.findVideoContentByVideoIdAndTime(mVideo.getId(), Utils.intToStr(i));
                if (videoContent == null) {
                    videoContent = new VideoContent(Utils.getuuid(), mVideo.getId(), Utils.intToStr(i), mCurrentVideoContent.getLineId(), mCurrentVideoContent.getPicPath(), mCurrentVideoContent.getIconId(), mCurrentVideoContent.getText());
                    list.add(videoContent);
                } else {
                    mCurrentVideoContent.setId(videoContent.getId());
                    mVideoContentManager.updateVideoContent(mCurrentVideoContent);
                }
            }
            mVideo.setIsEdit(1);
            VideoManager.getInstance(this).updateVideo(mVideo);
            mVideoContentManager.insertMultVideoContent(list);
            setToolbarContent(3);
            mVideoContent.setVisibility(View.GONE);
            mHandlerArea.setVisibility(View.INVISIBLE);
            mClip.setEnabled(true);
            mIcon.setEnabled(true);
            mText.setEnabled(true);
            isEditText = false;
            mCurrentVideoContent = null;
            //当前选中的按钮状态的更改
            if (currentEditContent == 0) {
                mClip.setImageResource(R.mipmap.icon_video_cut);
            } else if (currentEditContent == 1) {
                mText.setImageResource(R.mipmap.icon_video_text);
            } else {
                mIcon.setImageResource(R.mipmap.icon_video_expression);
            }
        } else {
            ToastUtils.toast(this, "当前没有编辑的数据");
        }

    }

    /**
     * 设置表情图标
     */
    private void setTimeAreaClip() {
        if (isEditText) {
            mClip.setImageResource(R.mipmap.icon_video_cut);
            mSelectTextType.removeAllViews();
            setGoneLayout();
            mIcon.setEnabled(true);
            mText.setEnabled(true);
            isEditText = false;
        } else {
            mClip.setImageResource(R.mipmap.icon_video_cut_edit);
            setVisibleLayout();
            updateList("裁剪");
            mText.setEnabled(false);
            mIcon.setEnabled(false);
            isEditText = true;
        }
    }

    /**
     * 设置显示布局
     */
    private void setVisibleLayout() {
        if (PLAY_BNT_STATE == 1) {//此时正在播放
            mVideoView.pause();
            PLAY_BNT_STATE = 0;
            mHandler.removeMessages(UPDATE_UI);
            mStartPlay.setImageResource(R.mipmap.icon_video_play);
        }
        setToolbarContent(2);
        mVideoContent.setVisibility(View.VISIBLE);
        mHandlerArea.setVisibility(View.VISIBLE);
    }

    /**
     * 设置隐藏布局
     */
    private void setGoneLayout() {
        setToolbarContent(1);
        mVideoContent.setVisibility(View.GONE);
        mHandlerArea.setVisibility(View.INVISIBLE);
    }

    /**
     * 设置表情图标
     */
    private void setTimeAreaIcon() {
        if (isEditText) {
            mIcon.setImageResource(R.mipmap.icon_video_expression);
            setGoneLayout();
            mClip.setEnabled(true);
            mText.setEnabled(true);
            isEditText = false;
        } else {
            mIcon.setImageResource(R.mipmap.icon_video_expression_edit);
            setVisibleLayout();
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
            setGoneLayout();
            mClip.setEnabled(true);
            mIcon.setEnabled(true);
            isEditText = false;
        } else {
            mText.setImageResource(R.mipmap.icon_video_text_edit);
            setVisibleLayout();
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
            updateClipList();
        } else if ("文本".equals(type)) {//文本
            updateTextList(layoutInflater, layoutParams);
        } else {//表情
            updateIconList(layoutInflater, layoutParams);
        }

    }

    private void updateClipList() {
        getBitmapsFromVideo( mVideo.getVideo_path());
    }

    /**
     * 更新表情list
     *
     * @param layoutInflater
     * @param layoutParams
     */
    private void updateIconList(LayoutInflater layoutInflater, LinearLayout.LayoutParams layoutParams) {
        if (emoticons != null && emoticons.length > 0) {
            for (int i = 0; i < emoticons.length; i++) {
                final int index = i;
                View view = layoutInflater.inflate(R.layout.video_detail_list_item, null, false);
                view.setLayoutParams(layoutParams);
                final ImageView imageView = (ImageView) view.findViewById(R.id.icon);
                imageView.setImageResource(emoticons[i]);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View v = mSelectTextType.getChildAt(preSelected);
                        v.setSelected(false);
                        view.setSelected(true);
                        preSelected = index;
                        iconId = index;
                        //在屏幕编辑区显示表情符号
                        if (mCurrentVideoContent == null) {
                            mCurrentVideoContent = new VideoContent(Utils.getuuid(), mVideo.getId(), "", -1, "", index, "");
                        } else {
                            mCurrentVideoContent.setIconId(index);
                        }
                        setVideoContentLayout();
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
    private void updateTextList(LayoutInflater layoutInflater, LinearLayout.LayoutParams layoutParams) {
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
                if (mCurrentVideoContent == null) {
                    mCurrentVideoContent = new VideoContent(Utils.getuuid(), mVideo.getId(), "", -1, "", -1, "点击编辑文本");
                } else {
                    mCurrentVideoContent.setText("点击编辑文本");
                }
                mCurrentVideoContent.setText("点击编辑文本");
                setVideoContentLayout();
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
                    View v = mSelectTextType.getChildAt(preSelected);
                    v.setSelected(false);
                    view.setSelected(true);
                    lineId = index - 1;
                    preSelected = index;
                    if (mCurrentVideoContent == null) {
                        mCurrentVideoContent = new VideoContent(Utils.getuuid(), mVideo.getId(), "", lineId, "", -1, "");
                    } else {
                        mCurrentVideoContent.setLineId(lineId);
                    }
                    setVideoContentLayout();
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
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, "1");
        } else {  //当屏幕方向是竖屏的时候，竖屏的时候的高我们需要把dp转为px
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dip2px(this, 240), "2");
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
     * @param height type 1 全屏 2 竖屏
     */
    private void setVideoViewScale(int width, int height, String type) {
        //获取VideoView宽和高
        ViewGroup.LayoutParams layoutParams = mVideoView.getLayoutParams();
        //赋值给VideoView的宽和高
        layoutParams.width = width;
        layoutParams.height = height;
        //设置VideoView的宽和高
        if ("1".equals(type)) {
            mFirstFrame.setVisibility(View.GONE);
            mHandlerArea.setVisibility(View.GONE);
        } else {
            mFirstFrame.setVisibility(View.VISIBLE);
            mHandlerArea.setVisibility(View.INVISIBLE);
        }
        mVideoView.setLayoutParams(layoutParams);
    }

    /**
     * 时间的格式化 * @param textView * @param millisecond
     */
    public void updateTime(TextView textView, int millisecond) {
        String str = Utils.intToStr(millisecond);
        textView.setText(str);
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
                VideoContent videoContent = mVideoContentManager.findVideoContentByVideoIdAndTime(mVideo.getId(), Utils.intToStr(currentPosition));
                mCurrentVideoContent = videoContent;
                setVideoContentLayout();
                mHandler.sendEmptyMessageDelayed(UPDATE_UI, 500);
            } else if (msg.what == UPDATE_CLIP_UI) {
                //更新视频布局
                list.clear();
                list.addAll(bitmaps);
                bitmaps.clear();
                setClipLayout();
            }
        }
    };

    private void setClipLayout() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (list != null && list.size() > 0) {
            mSelectTextType.setVisibility(View.VISIBLE);
            for (int i = 0; i < list.size(); i++) {
                final Bitmap bitmap = list.get(i);
                View view = layoutInflater.inflate(R.layout.video_detail_list_item_clip, null, false);
                view.setLayoutParams(layoutParams);
                final ImageView imageView = (ImageView) view.findViewById(R.id.icon);
                imageView.setImageBitmap(bitmap);
                mSelectTextType.addView(view);
            }
        }
        if (mSelectTextType.getChildCount() == 0) {
            mSelectTextType.setVisibility(View.GONE);
        }
    }


    public void getBitmapsFromVideo(String clipPath) {
        File file = new File(clipPath);
        final MediaMetadataRetriever retriever = new MediaMetadataRetriever();

        retriever.setDataSource(file.getAbsolutePath());

        // 取得视频的长度(单位为秒)

        // 得到每一秒时刻的bitmap比如第一秒,第二秒

        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //子线程操作
                int seconds = max / 1000;
                for (int i = 1; i <= seconds; i += 3) {
                    Bitmap bitmap = retriever.getFrameAtTime(i * 1000 * 1000);
                    bitmaps.add(bitmap);
                    int i1 = i % 5;
                    if (i1 == 0 || i >= seconds) {
                        mHandler.sendEmptyMessageDelayed(UPDATE_CLIP_UI, 500);
                    }

                }
            }
        });
        mThread.start();


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
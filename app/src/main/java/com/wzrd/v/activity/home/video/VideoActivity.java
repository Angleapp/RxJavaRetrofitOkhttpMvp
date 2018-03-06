package com.wzrd.v.activity.home.video;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wzrd.R;
import com.wzrd.m.been.Video;
import com.wzrd.m.db.manger.VideoManager;
import com.wzrd.m.utils.Constants;
import com.wzrd.m.utils.Utils;
import com.wzrd.v.adapter.VideoGridViewAdapter;
import com.wzrd.v.view.popup.VideoPopupWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class VideoActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_back)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_menu)
    ImageView mToolbarMenu;
    @BindView(R.id.toolbar_menu_text)
    TextView mToolbarMenuText;
    @BindView(R.id.express_love)
    TextView mExpressLove;
    @BindView(R.id.apologize)
    TextView mApologize;
    @BindView(R.id.deepen_love)
    TextView mDeepenLove;
    @BindView(R.id.user_defined)
    TextView mUserDefined;
    @BindView(R.id.video_list)
    GridView mVideoList;
    List<Video> mList = new ArrayList<>();
    private VideoGridViewAdapter mGridViewAdpter;
    private VideoManager mVideoManager;
    private Map<String,Bitmap> mMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        Utils.backToolbar(this, mToolbarBack, mToolbarTitle, "短视频", mToolbarMenu, 0, null, mToolbarMenuText, "");
        mExpressLove.setSelected(true);
        
        mList = getLoadMedia("0");
        //初始化权限
        requestSDPermission();
        mGridViewAdpter = new VideoGridViewAdapter(mList, this,mMap);
        mVideoList.setAdapter(mGridViewAdpter);
        mVideoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View view1, int i, long l) {
                Intent intent = new Intent(VideoActivity.this, VideoDetailActivity.class);
                Video video = mList.get(i);
                intent.putExtra("path",video.getVideo_path() );
                intent.putExtra("type",video.getVideo_type());
                intent.putExtra("title",video.getTitle());
                startActivity(intent);
            }
        });
        mVideoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> view, View view1, int i, long l) {
                Video video = mList.get(i);
                if (video.getVideo_type().equals("3")){//自定义
                    VideoPopupWindow videoPopupWindow = new VideoPopupWindow(VideoActivity.this,video,VideoActivity.this);
                    videoPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                }
                return true;
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
                                Utils.ToastShort(VideoActivity.this, Constants.RDWISDPREMISS);
                            } else {
                                getFirstFrame();
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
            getFirstFrame();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getFirstFrame();
                } else {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
    /**
     * 获取方法
     */
    private void getFirstFrame() {
        if (mList!=null&&mList.size()>0){
            for (int i = 0; i < mList.size(); i++) {
                Video video =mList.get(i);
                File file = new File(video.getVideo_path());
                if (file.exists()){
                    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                    mmr.setDataSource(file.getAbsolutePath());
                    Bitmap firstFrame = mmr.getFrameAtTime();
                    mMap.put(video.getVideo_path(),firstFrame);
                }
            }

        }

    }

    /**
     * 获取数据
     */
    private void getListData() {
        mVideoManager = VideoManager.getInstance(this);
        List<Video> allVideo = mVideoManager.getAllVideo();
        if (allVideo==null || allVideo.size()==0){
            List<Video> videoList = new ArrayList<>();
            videoList.add(new Video(Utils.getuuid(), "0", "video.mp4","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520070975167&di=ebb075892e6b15ba5a6bc5fed6e2752a&imgtype=0&src=http%3A%2F%2Fpic15.nipic.com%2F20110805%2F7017024_111804711000_2.jpg", "书写爱",0));
            videoList.add(new Video(Utils.getuuid(), "0", "video1.mp4","https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2269428676,3617972594&fm=27&gp=0.jpg", "爱的声音",0));
            videoList.add(new Video(Utils.getuuid(),  "0","video1.mp4","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520070975167&di=559af3f7e3353ba6c97ca7b0a10cdb9a&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fb17eca8065380cd7d55f8dd7aa44ad3459828142.jpg", "旅游记录",0));
            videoList.add(new Video(Utils.getuuid(), "0", "video0.mp4", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520070975166&di=468fcfbe813f01ad21f86a0aa676ffd2&imgtype=0&src=http%3A%2F%2Fp1.wmpic.me%2Farticle%2F2017%2F01%2F07%2F1483769832_IoSTSUWn.jpg","摄影游记",0));
            videoList.add(new Video(Utils.getuuid(), "1", "video.mp4", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520070975165&di=777ab862618ff73492602b7b4f83ed96&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F3801213fb80e7bec0d082ddb242eb9389b506b3d.jpg","sorry",0));
            videoList.add(new Video(Utils.getuuid(), "1", "video1.mp4", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520070975164&di=1369ebff43bda5f8d8f8118f6ad7bb25&imgtype=0&src=http%3A%2F%2Fwww.th7.cn%2Fd%2Ffile%2Fp%2F2014%2F06%2F26%2Fae984cda0475c2c00347a7daf4dac2f5.jpg","why",0));
            videoList.add(new Video(Utils.getuuid(),  "1","video1.mp4", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520070975166&di=468fcfbe813f01ad21f86a0aa676ffd2&imgtype=0&src=http%3A%2F%2Fp1.wmpic.me%2Farticle%2F2017%2F01%2F07%2F1483769832_IoSTSUWn.jpg","对不起",0));
            videoList.add(new Video(Utils.getuuid(), "1", "video0.mp4","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520070975164&di=1369ebff43bda5f8d8f8118f6ad7bb25&imgtype=0&src=http%3A%2F%2Fwww.th7.cn%2Fd%2Ffile%2Fp%2F2014%2F06%2F26%2Fae984cda0475c2c00347a7daf4dac2f5.jpg", "歉意",0));
            videoList.add(new Video(Utils.getuuid(), "2", "video.mp4", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520070975165&di=777ab862618ff73492602b7b4f83ed96&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F3801213fb80e7bec0d082ddb242eb9389b506b3d.jpg","书写爱",0));
            videoList.add(new Video(Utils.getuuid(), "2", "video1.mp4","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520070975166&di=468fcfbe813f01ad21f86a0aa676ffd2&imgtype=0&src=http%3A%2F%2Fp1.wmpic.me%2Farticle%2F2017%2F01%2F07%2F1483769832_IoSTSUWn.jpg", "爱的声音",0));
            videoList.add(new Video(Utils.getuuid(),  "2","video1.mp4", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520070975166&di=468fcfbe813f01ad21f86a0aa676ffd2&imgtype=0&src=http%3A%2F%2Fp1.wmpic.me%2Farticle%2F2017%2F01%2F07%2F1483769832_IoSTSUWn.jpg","旅游记录",0));
            videoList.add(new Video(Utils.getuuid(), "2", "video0.mp4","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520070975164&di=1369ebff43bda5f8d8f8118f6ad7bb25&imgtype=0&src=http%3A%2F%2Fwww.th7.cn%2Fd%2Ffile%2Fp%2F2014%2F06%2F26%2Fae984cda0475c2c00347a7daf4dac2f5.jpg", "摄影游记",0));
            videoList.add(new Video(Utils.getuuid(), "3", "video.mp4","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520071122258&di=7ed2da0afe9b13d6f8e748efbf8a08f2&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D207814470%2C4197991601%26fm%3D214%26gp%3D0.jpg", "书写爱",0));
            videoList.add(new Video(Utils.getuuid(), "3", "video1.mp4","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520071122258&di=7ed2da0afe9b13d6f8e748efbf8a08f2&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D207814470%2C4197991601%26fm%3D214%26gp%3D0.jpg", "爱的声音",0));
            videoList.add(new Video(Utils.getuuid(),  "3","video1.mp4","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520070975165&di=777ab862618ff73492602b7b4f83ed96&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F3801213fb80e7bec0d082ddb242eb9389b506b3d.jpg", "旅游记录",0));
            videoList.add(new Video(Utils.getuuid(), "3", "video0.mp4", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520070975166&di=468fcfbe813f01ad21f86a0aa676ffd2&imgtype=0&src=http%3A%2F%2Fp1.wmpic.me%2Farticle%2F2017%2F01%2F07%2F1483769832_IoSTSUWn.jpg","摄影游记",0));
            mVideoManager.insertMultVideo(videoList);
        }
        List<Video> videos = mVideoManager.findVideoByVideoTypeAndIsEdit("0",0);
        mList.addAll(videos);
    }

    @OnClick({R.id.express_love, R.id.apologize, R.id.deepen_love, R.id.user_defined})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.express_love:
                setTabState(mExpressLove);
                updateListData("0");
                break;
            case R.id.apologize:
                setTabState(mApologize);
                updateListData("1");
                break;
            case R.id.deepen_love:
                setTabState(mDeepenLove);
                updateListData("2");
                break;
            case R.id.user_defined:
                setTabState(mUserDefined);
                updateListData("3");
                break;
        }
    }

    /**
     * 更换列表的数据
     * @param type
     */
    private void updateListData(String type) {
        List<Video> videoType = getLoadMedia(type);
        mList.clear();
        mList.addAll(videoType);
        mGridViewAdpter.notifyDataSetChanged();
    }

    /**
     * 设置tab的状态
     *
     * @param selectedTab
     */
    private void setTabState(TextView selectedTab) {
        mExpressLove.setSelected(false);
        mApologize.setSelected(false);
        mDeepenLove.setSelected(false);
        mUserDefined.setSelected(false);
        selectedTab.setSelected(true);
    }

    public void refreshList(){
        mGridViewAdpter.notifyDataSetChanged();
    }

    public List<Video>  getLoadMedia(String type) {
        List<Video> list = new ArrayList<>();
        Cursor cursor = this.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        try {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)); // id
                String displayName =cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));//标题
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM)); // 专辑
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST)); // 艺术家
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 显示名称
                String mimeType =cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)); // 路径
                long duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)); // 时长
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)); // 大小
                String resolution =cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION));
                if (size!=0){
                    Video video = new Video(Utils.getuuid(),type,path,"",title,0);
                    list.add(video);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return list;
    }
}

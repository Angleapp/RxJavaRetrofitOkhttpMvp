package com.wzrd.v.activity.home.video;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.been.Video;
import com.wzrd.m.db.manger.VideoManager;
import com.wzrd.m.utils.Utils;
import com.wzrd.v.adapter.VideoGridViewAdapter;
import com.wzrd.v.view.popup.VideoDownloadPopwindow;
import com.wzrd.v.view.popup.VideoPopupWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoActivity extends AppCompatActivity {

    private static ContentResolver mContentResolver;
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
    @BindView(R.id.root_layout)
    LinearLayout mRootLayout;
    private VideoGridViewAdapter mGridViewAdpter;
    private VideoManager mVideoManager;
    private Map<String, Bitmap> mMap = new HashMap<>();
    private int position = 0;
    VideoDownloadPopwindow videoDownloadPopwindow;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                videoDownloadPopwindow.dismiss();
                mGridViewAdpter = new VideoGridViewAdapter(mList, VideoActivity.this, mMap);
                mVideoList.setAdapter(mGridViewAdpter);
            } else if (msg.what == 200) {
                videoDownloadPopwindow = new VideoDownloadPopwindow(VideoActivity.this);
                videoDownloadPopwindow.showAtLocation(mRootLayout, Gravity.CENTER, 0, 0);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        Utils.backToolbar(this, mToolbarBack, mToolbarTitle, "短视频", mToolbarMenu, 0, null, mToolbarMenuText, "");
        mExpressLove.setSelected(true);
        mContentResolver = this.getContentResolver();
        mHandler.sendEmptyMessageDelayed(200, 100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mList = getLoadMedia("0");
                getFirstFrame();
                mHandler.sendEmptyMessageDelayed(100, 1000);
            }
        }).start();
        mHandler.sendEmptyMessageDelayed(100, 1000);
        mVideoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View view1, int i, long l) {
                Intent intent = new Intent(VideoActivity.this, VideoDetailActivity.class);
                Video video = mList.get(i);
                intent.putExtra("path", video.getVideo_path());
                intent.putExtra("type", video.getVideo_type());
                intent.putExtra("title", video.getTitle());
                startActivity(intent);
            }
        });
        mVideoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> view, View view1, int i, long l) {
                Video video = mList.get(i);
                if (video.getVideo_type().equals("3")) {//自定义
                    position = i;
                    VideoPopupWindow videoPopupWindow = new VideoPopupWindow(VideoActivity.this, video, VideoActivity.this);
                    videoPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                }
                return true;
            }
        });
    }

    /**
     * 获取方法
     */
    private void getFirstFrame() {
        if (mList != null && mList.size() > 0) {
            for (int i = 0; i < mList.size(); i++) {
                Video video = mList.get(i);
                Bitmap bitmap = getVideoThumbnail(Integer.parseInt(video.getId()));
                mMap.put(video.getVideo_path(), bitmap);
            }

        }

    }

    // 获取视频缩略图
    private static Bitmap getVideoThumbnail(int id) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bitmap = MediaStore.Video.Thumbnails.getThumbnail(mContentResolver, id, MediaStore.Images.Thumbnails.MINI_KIND, options);
        return bitmap;
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
     *
     * @param type
     */
    private void updateListData(String type) {
        List<Video> videoType = changeList(type);
        mList.clear();
        mList.addAll(videoType);
        mGridViewAdpter.notifyDataSetChanged();
    }

    private List<Video> changeList(String type) {
        List<Video> list = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            Video video = mList.get(i);
            video.setVideo_type(type);
            list.add(video);
        }
        return list;
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

    public void refreshList(String path, String name) {
        Video video = mList.get(position);
        mMap.remove(video.getVideo_path());
        video.setVideo_path(path);
        video.setTitle(name);
        Bitmap bitmap = getVideoThumbnail(Integer.parseInt(video.getId()));
        mMap.put(path, bitmap);
        mGridViewAdpter.notifyDataSetChanged();
    }


    public void refreshList() {
        mGridViewAdpter.notifyDataSetChanged();
    }

    public List<Video> getLoadMedia(String type) {
        List<Video> list = new ArrayList<>();
        Cursor cursor = this.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        try {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)); // id
                String displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));//标题
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM)); // 专辑
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST)); // 艺术家
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 显示名称
                String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)); // 路径
                long duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)); // 时长
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)); // 大小
                String resolution = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION));
                if (size != 0) {
                    Video video = new Video(String.valueOf(id), type, path, "", title, 0);
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

package com.wzrd.v.activity.home.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.been.Video;
import com.wzrd.m.db.manger.VideoManager;
import com.wzrd.m.utils.Utils;
import com.wzrd.v.adapter.VideoGridViewAdapter;
import com.wzrd.v.view.popup.VideoPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        Utils.backToolbar(this, mToolbarBack, mToolbarTitle, "短视频", mToolbarMenu, 0, null, mToolbarMenuText, "");
        mExpressLove.setSelected(true);
        
        getListData();
        mGridViewAdpter = new VideoGridViewAdapter(mList, this);
        mVideoList.setAdapter(mGridViewAdpter);

        mVideoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View view1, int i, long l) {
                Intent intent = new Intent(VideoActivity.this, VideoDetailActivity.class);
                intent.putExtra("id", mList.get(i).getId());
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
        List<Video> videoType = mVideoManager.findVideoByVideoTypeAndIsEdit(type,0);
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
}

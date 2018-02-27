package com.wzrd.v.activity.home.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.been.Video;
import com.wzrd.m.utils.Utils;
import com.wzrd.v.adapter.VideoRecyclerAdapter;

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
    private VideoRecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        Utils.backToolbar(this, mToolbarBack, mToolbarTitle, "短视频", mToolbarMenu, 0, null, mToolbarMenuText, "");
        mExpressLove.setSelected(true);
        getListData();
        mRecyclerAdapter = new VideoRecyclerAdapter(mList, this);
        mVideoList.setAdapter(mRecyclerAdapter);

        mVideoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View view1, int i, long l) {
                Intent intent = new Intent(VideoActivity.this, VideoDetailActivity.class);
                intent.putExtra("id", mList.get(i).getId());
                startActivity(intent);

            }
        });
    }

    /**
     * 获取数据
     */
    private void getListData() {
        mList.add(new Video("1", "http://img5.imgtn.bdimg.com/it/u=104961686,3757525983&fm=27&gp=0.jpg", "", "书写爱"));
        mList.add(new Video("2", "http://pic4.nipic.com/20091121/3764872_215617048242_2.jpg", "", "爱的声音"));
        mList.add(new Video("3", "http://pic2.ooopic.com/12/42/25/02bOOOPIC95_1024.jpg", "", "旅游记录"));
        mList.add(new Video("4", "", "", "摄影游记"));
    }

    @OnClick({R.id.express_love, R.id.apologize, R.id.deepen_love, R.id.user_defined})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.express_love:
                setTabState(mExpressLove);
                break;
            case R.id.apologize:
                setTabState(mApologize);
                break;
            case R.id.deepen_love:
                setTabState(mDeepenLove);
                break;
            case R.id.user_defined:
                setTabState(mUserDefined);
                break;
        }
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
}

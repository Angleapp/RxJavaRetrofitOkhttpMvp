package com.wzrd.v.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.wzrd.R;
import com.wzrd.m.utils.ActivityCollector;
import com.wzrd.v.adapter.ViewPagerFragmentAdapter;
import com.wzrd.v.fragment.main.ContactFragment;
import com.wzrd.v.fragment.main.MineFragment;
import com.wzrd.v.fragment.main.NewFragment;
import com.wzrd.v.fragment.main.TimingFragment;
import com.wzrd.v.view.BannerTabHost;
import com.wzrd.v.view.popup.EixtPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tab_auslese)
    BannerTabHost tabAuslese;
    @BindView(R.id.tab_classfition)
    BannerTabHost tabClassfition;
    @BindView(R.id.tab_host)
    BannerTabHost tabHost;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    @BindView(R.id.tab_mine)
    BannerTabHost tabMine;
    private List<Fragment> mList = new ArrayList<>();
    private List<BannerTabHost> bannerList = new ArrayList<>();
    private NewFragment newFragment;
    private TimingFragment timingFragment;
    private ContactFragment contactFragment;
    private MineFragment mineFragment;
    private int preItem = 0;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view=getLayoutInflater().inflate(R.layout.activity_main,null);
        setContentView(view);
        ButterKnife.bind(this);
        //初始化tab
        initBannerTabHost();
        //初始化控件
        initView();
        viewPager.setCurrentItem(0);
        ActivityCollector.addActivity(this);
    }


    private void initView() {
        newFragment = new NewFragment();
        timingFragment = new TimingFragment();
        contactFragment = new ContactFragment();
        mineFragment = new MineFragment();
        mList.add(newFragment);
        mList.add(timingFragment);
        mList.add(contactFragment);
        mList.add(mineFragment);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), mList));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        viewPager.setCurrentItem(0);
                        bannerList.get(preItem).setRbtLeftIconChecked(false);
                        tabAuslese.setRbtLeftIconChecked(true);
                        preItem = 0;
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        bannerList.get(preItem).setRbtLeftIconChecked(false);
                        tabClassfition.setRbtLeftIconChecked(true);
                        preItem = 1;
                        break;
                    case 2:
                        viewPager.setCurrentItem(2);
                        bannerList.get(preItem).setRbtLeftIconChecked(false);
                        tabHost.setRbtLeftIconChecked(true);
                        preItem = 2;
                        break;
                    case 3:
                        viewPager.setCurrentItem(3);
                        bannerList.get(preItem).setRbtLeftIconChecked(false);
                        tabMine.setRbtLeftIconChecked(true);
                        preItem = 3;
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void initBannerTabHost() {
        bannerList.add(tabAuslese);
        bannerList.add(tabClassfition);
        bannerList.add(tabHost);
        bannerList.add(tabMine);
    }


    @OnClick({R.id.tab_auslese, R.id.tab_classfition, R.id.tab_host,R.id.tab_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_auslese:
                viewPager.setCurrentItem(0);
                bannerList.get(preItem).setRbtLeftIconChecked(false);
                tabAuslese.setRbtLeftIconChecked(true);
                tabAuslese.setBtMessageState(true);//设置小红点显示
                preItem = 0;
                break;
            case R.id.tab_classfition:
                viewPager.setCurrentItem(1);
                bannerList.get(preItem).setRbtLeftIconChecked(false);
                tabClassfition.setRbtLeftIconChecked(true);
                tabClassfition.setBtMessageState(true);//设置小红点显示
                preItem = 1;
                break;
            case R.id.tab_host:
                viewPager.setCurrentItem(2);
                bannerList.get(preItem).setRbtLeftIconChecked(false);
                tabHost.setRbtLeftIconChecked(true);
                preItem = 2;
                break;

            case R.id.tab_mine:
                viewPager.setCurrentItem(3);
                bannerList.get(preItem).setRbtLeftIconChecked(false);
                tabMine.setRbtLeftIconChecked(true);
                preItem = 3;
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
        }
        return false;
    }

    private void back() {
        EixtPopupWindow relifePopupWindow=new EixtPopupWindow(MainActivity.this);
        relifePopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);



//        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
//        dialog.setMessage("确定退出程序");
//        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                ActivityCollector.finishAll();
//                finish();
//            }
//        });
//
//        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();

    }
}

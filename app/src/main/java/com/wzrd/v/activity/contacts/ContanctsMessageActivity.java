package com.wzrd.v.activity.contacts;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.v.adapter.ViewPagerFragmentAdapter;
import com.wzrd.v.fragment.contanct.InboxFragment;
import com.wzrd.v.fragment.contanct.OutboxFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContanctsMessageActivity extends AppCompatActivity {
    @BindView(R.id.rb_a)
    TextView rbA;
    @BindView(R.id.rb_b)
    TextView rbB;
    @BindView(R.id.vp_concatns)
    ViewPager vpConcatns;
    private String username;
    private List<Fragment> mList = new ArrayList<Fragment>();
    private ViewPagerFragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contancts_message);
        ButterKnife.bind(this);
        username = getIntent().getExtras().getString("name");
//        Utils.ToastShort(this,username);
        initFragments();
        setTextState(0);
        setViewPager();
    }

    private void setViewPager() {
        fragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), mList);
        vpConcatns.setAdapter(fragmentAdapter);
    }

    /**
     * 初始化fragment
     */

    private void initFragments() {
        mList.add(new OutboxFragment());
        mList.add(new InboxFragment());
    }

    @OnClick({R.id.rb_a, R.id.rb_b})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_a:
                setTextState(0);
                break;
            case R.id.rb_b:
                setTextState(1);
                break;
        }
    }

    /**
     * 设置选中的textview的状态
     *
     * @param i
     */

    private void setTextState(int i) {
        switch (i) {
            case 0:
                rbA.setBackgroundColor(Color.parseColor("#007AFF"));
                rbA.setTextColor(Color.WHITE);
                rbB.setTextColor(Color.parseColor("#007AFF"));
                rbB.setBackgroundColor(Color.BLACK);
                break;
            case 1:
                rbB.setBackgroundColor(Color.parseColor("#007AFF"));
                rbB.setTextColor(Color.WHITE);
                rbA.setTextColor(Color.parseColor("#007AFF"));
                rbA.setBackgroundColor(Color.BLACK);
                break;

        }

    }
}

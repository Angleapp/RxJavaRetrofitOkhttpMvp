package com.wzrd.v.activity.contacts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.v.adapter.ViewPagerFragmentAdapter;
import com.wzrd.v.fragment.contanct.InboxFragment;
import com.wzrd.v.fragment.contanct.OutboxFragment;
import com.wzrd.v.view.NoScrollViewPager;

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
    NoScrollViewPager vpConcatns;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private String username;
    private List<Fragment> mList = new ArrayList<Fragment>();
    private ViewPagerFragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contancts_message);
        ButterKnife.bind(this);
        username = getIntent().getExtras().getString("name");
        tvName.setText(username);
//        Utils.ToastShort(this,username);
        initFragments();
        setTextState(0);
        setViewPager();
    }

    private void setViewPager() {
        fragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), mList);
        vpConcatns.setAdapter(fragmentAdapter);
        vpConcatns.setNoScroll(true);

    }

    /**
     * 初始化fragment
     */

    private void initFragments() {
        OutboxFragment fragment = new OutboxFragment();
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        fragment.setArguments(bundle);
        mList.add(new InboxFragment());
        mList.add(fragment);

    }

    @OnClick({R.id.rb_a, R.id.rb_b,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_a:
                setTextState(0);
                vpConcatns.setCurrentItem(0);
                break;
            case R.id.rb_b:
                setTextState(1);
                vpConcatns.setCurrentItem(1);
                break;
            case R.id.iv_back:
                finish();
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
                rbA.setSelected(true);
                rbB.setSelected(false);
                break;
            case 1:
                rbA.setSelected(false);
                rbB.setSelected(true);
                break;

        }

    }
}

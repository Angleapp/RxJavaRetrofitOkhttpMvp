package com.wzrd.v.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wzrd.R;
import com.wzrd.m.been.TSYSUSER;
import com.wzrd.m.db.manger.UserManager;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.v.fragment.base.NoNetBaseLayFragment;
import com.wzrd.v.view.GlideCircleTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lk on 2018/1/3.
 */

public class ContactFragment extends NoNetBaseLayFragment {
    @BindView(R.id.tv_addlover)
    TextView tvAddlover;
    @BindView(R.id.iv_lover_iocn)
    ImageView ivLoverIocn;
    @BindView(R.id.tv_lover_name)
    TextView tvLoverName;
    @BindView(R.id.ll_lover)
    LinearLayout llLover;
    @BindView(R.id.iv_add_family)
    ImageView ivAddFamily;
    Unbinder unbinder;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contact_fragment, null);
        unbinder = ButterKnife.bind(this, view);
        isPrepared = true;
        lazyLoad();

        return view;
    }

    private void initdata() {
        String id = SharedPreferencesUtil.getString(getActivity(), "userphonenum", "");
        UserManager manager = new UserManager(getActivity());
        List<TSYSUSER> userName = manager.getByUserid(id);
        if (userName != null && userName.size() > 0) {
            tvAddlover.setVisibility(View.GONE);
            Glide.with(view.getContext())
                    .load(userName.get(0).getT_sys_usericonpath())
                    .placeholder(R.mipmap.feilei_on)
                    .bitmapTransform(new GlideCircleTransform(view.getContext()))
                    .error(R.mipmap.feilei_on)
                    .into(ivLoverIocn);
            tvLoverName.setText(userName.get(0).getT_sys_lover_name());
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_addlover, R.id.ll_lover, R.id.iv_add_family})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_addlover:
                break;
            case R.id.ll_lover:
                break;
            case R.id.iv_add_family:
                break;
        }
    }

    @Override
    public void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initdata();
    }
}

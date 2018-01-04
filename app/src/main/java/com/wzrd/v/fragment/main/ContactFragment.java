package com.wzrd.v.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzrd.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lk on 2018/1/3.
 */

public class ContactFragment extends Fragment {
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
        return view;
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
}

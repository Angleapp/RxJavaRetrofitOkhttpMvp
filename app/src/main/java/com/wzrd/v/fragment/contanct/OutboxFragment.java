package com.wzrd.v.fragment.contanct;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzrd.R;

/**
 * Created by lk on 2018/2/9.
 */

public class OutboxFragment extends Fragment {
    private ViewDataBinding dataBinding;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      DataBindingUtil.inflate(inflater, R.layout.outbox_fragment, container, false);

        return dataBinding.getRoot();
    }
}

package com.wzrd.v.fragment.contanct;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzrd.R;
import com.wzrd.databinding.OutboxFragmentBinding;
import com.wzrd.m.been.ContactMessage;
import com.wzrd.m.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lk on 2018/2/9.
 */

public class OutboxFragment extends Fragment {
    private OutboxFragmentBinding dataBinding;
    private String username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.outbox_fragment, container, false);
        Bundle arguments = getArguments();
        username  = arguments.getString("username");
        initdata();
        return dataBinding.getRoot();
    }

    /**
     * 设置临时数据
     */
    private void initdata() {
        List<ContactMessage> list = new ArrayList<>();
        ContactMessage message = new ContactMessage();
        message.setMotifitytime(DateUtils.getCurrentDate());
        message.setUsername(username);
        for (int i = 0; i < 9; i++) {
            list.add(message);
        }
        dataBinding.setData(list);
    }
}

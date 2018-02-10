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
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.v.fragment.base.NoNetBaseLayFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lk on 2018/2/9.
 */

public class OutboxFragment extends NoNetBaseLayFragment {
    private OutboxFragmentBinding dataBinding;
    private String username;
    private String imagepath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.outbox_fragment, container, false);
        Bundle arguments = getArguments();
        username  = arguments.getString("username");
        imagepath= SharedPreferencesUtil.getString(getContext(),"icon","");

        isPrepared = true;
        lazyLoad();

        return dataBinding.getRoot();
    }

    /**
     * 设置临时数据
     */
    private void initdata() {
        List<ContactMessage> list = new ArrayList<>();


        for (int i = 0; i < 3; i++) {
            ContactMessage message = new ContactMessage();
            message.setMotifitytime(DateUtils.getCurrentDate());
            message.setIconpath(imagepath);
            message.setUsername(username+i);
            list.add(message);
        }


        for (int i = 0; i < 3; i++) {
            ContactMessage message = new ContactMessage();
            Date date = DateUtils.strToDateLong(DateUtils.getCurrentDate());
            message.setMotifitytime(DateUtils.formatDateDaysAgoString(date)+"");
            message.setIconpath(imagepath);
            message.setUsername(username+(i+3));
            list.add(message);
        }
        for (int i = 0; i < 3; i++) {
            ContactMessage message = new ContactMessage();
            Date date = DateUtils.strToDateLong(DateUtils.getCurrentDate());
            message.setMotifitytime(DateUtils.formatDateDaysAfterString(date));
            message.setIconpath(imagepath);
            if(i==1){
                message.setCancle(true);
            }

            message.setUsername(username+(i+6));
            list.add(message);
        }
        dataBinding.setOutboxdata(list);
    }

    @Override
    public void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initdata();
    }
}

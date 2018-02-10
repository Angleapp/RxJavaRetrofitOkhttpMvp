package com.wzrd.v.fragment.contanct;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzrd.R;
import com.wzrd.databinding.InboxFragmentBinding;
import com.wzrd.m.been.ContactMessage;
import com.wzrd.m.been.InboxContactMessage;
import com.wzrd.m.utils.DateUtils;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.v.activity.welcome.IconActivity;
import com.wzrd.v.fragment.base.NoNetBaseLayFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lk on 2018/2/9.
 */

public class InboxFragment extends NoNetBaseLayFragment{
    private  InboxFragmentBinding dataBinding;
    private String TAG="InboxFragment";
    private String imagepath;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         dataBinding= DataBindingUtil.inflate(inflater, R.layout.inbox_fragment, container, false);
        imagepath= SharedPreferencesUtil.getString(getContext(),"icon","");
        isPrepared = true;
        intidata();
        return dataBinding.getRoot();
    }

    /**
     * 临时数据
     */
    private void intidata() {
        List<ContactMessage> list = new ArrayList<>();


        for (int i = 0; i < 9; i++) {
            ContactMessage message = new ContactMessage();
            message.setMotifitytime(DateUtils.getCurrentDate());
            message.setIconpath(imagepath);
            message.setUsername("张三"+i);
            list.add(message);
        }
        dataBinding.setInboxdata(list);
    }

    @Override
    public void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
//        intidata();
    }
}

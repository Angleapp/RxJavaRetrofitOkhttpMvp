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
import com.wzrd.databinding.OutboxFragmentBinding;
import com.wzrd.m.been.ContactMessage;
import com.wzrd.m.utils.DateUtils;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.m.utils.Utils;
import com.wzrd.p.inteface.OutboxMessageId;
import com.wzrd.v.fragment.base.NoNetBaseLayFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lk on 2018/2/9.
 */

public class OutboxFragment extends Fragment implements OutboxMessageId.getmessageid {
    private OutboxFragmentBinding dataBinding;
    private String username;
    private String imagepath;
    private List<ContactMessage> list = new ArrayList<>();
    ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.outbox_fragment, container, false);
        Bundle arguments = getArguments();
        username = arguments.getString("username");
        imagepath = SharedPreferencesUtil.getString(getContext(), "icon", "");
        initdata();



        return dataBinding.getRoot();
    }

    /**
     * 设置临时数据
     */
    private void initdata() {


        for (int i = 0; i < 3; i++) {
            ContactMessage message = new ContactMessage();
            message.setMotifitytime(DateUtils.getCurrentDate());
            message.setIconpath(imagepath);
            message.setUsername(username + i);
            message.setMessageid(Utils.getuuid());
            list.add(message);
        }
        for (int i = 0; i < 3; i++) {
            ContactMessage message = new ContactMessage();
            Date date = DateUtils.strToDateLong(DateUtils.getCurrentDate());
            message.setMotifitytime(DateUtils.formatDateDaysAgoString(date) + "");
            message.setIconpath(imagepath);
            message.setUsername(username + (i + 3));
            message.setMessageid(Utils.getuuid());
            list.add(message);
        }
        for (int i = 0; i < 3; i++) {
            ContactMessage message = new ContactMessage();
            Date date = DateUtils.strToDateLong(DateUtils.getCurrentDate());
            message.setMotifitytime(DateUtils.formatDateDaysAfterString(date));
            message.setMessageid(Utils.getuuid());
            message.setIconpath(imagepath);
            if (i == 1) {
                message.setCancle(true);
            }

            message.setUsername(username + (i + 6));
            list.add(message);
        }
        dataBinding.setOutboxdata(list);
    }




    @Override
    public void onRefresh(String messageid) {
        List<ContactMessage> newlist = new ArrayList<>();
        if (list != null && list.size() > 0 && messageid != null) {
            for (int i = 0; i < list.size(); i++) {
                if (messageid.equals(list.get(i).getMessageid())) {
                    newlist.add(list.get(i));
                }
            }
            list.removeAll(newlist);
            dataBinding.setOutboxdata(list);

        }

    }
}

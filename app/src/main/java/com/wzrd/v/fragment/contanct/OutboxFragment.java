package com.wzrd.v.fragment.contanct;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzrd.R;
import com.wzrd.databinding.OutboxFragmentBinding;
import com.wzrd.m.been.ContactMessage;
import com.wzrd.m.utils.DateUtils;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.m.utils.Utils;
import com.wzrd.p.Rypresenter;
import com.wzrd.p.inteface.OutboxMessageId;
import com.wzrd.v.view.RecycleResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lk on 2018/2/9.
 */

public class OutboxFragment extends Fragment implements OutboxMessageId.getmessageid ,RecycleResult {
    private OutboxFragmentBinding dataBinding;
    private String username;
    private String imagepath;
    private List<ContactMessage> list = new ArrayList<>();
    private  SwipeRefreshLayout swlaout;
    private RecyclerView  recycle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.outbox_fragment, container, false);
        Bundle arguments = getArguments();
        username = arguments.getString("username");
        imagepath = SharedPreferencesUtil.getString(getContext(), "icon", "");
        initdata();
        Refresh();
        Rypresenter rypresenter = new Rypresenter(this);
        rypresenter.getlast(recycle, "OutboxFragment");


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
        for (int i = 0; i < 13; i++) {
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

    /**
     * 数据刷新
     */
    private void Refresh() {

        recycle= (RecyclerView) dataBinding.getRoot().getRootView().findViewById(R.id.recycle);
        swlaout= (SwipeRefreshLayout) dataBinding.getRoot().getRootView().findViewById(R.id.swlaout);
        swlaout.setSize(0);
        swlaout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        swlaout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swlaout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Utils.ToastShort(getContext(),"此处可以填写下拉刷新逻辑");
                swlaout.setRefreshing(false);

            }
        });

    }

    @Override
    public void last(String message) {
        Utils.ToastShort(getContext(),"滑动到底部.此处可以填写上拉刷新逻辑");
    }
}

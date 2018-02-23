package com.wzrd.v.fragment.contanct;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzrd.R;
import com.wzrd.databinding.InboxFragmentBinding;
import com.wzrd.m.been.ContactMessage;
import com.wzrd.m.utils.DateUtils;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.m.utils.Utils;
import com.wzrd.p.Rypresenter;
import com.wzrd.v.fragment.base.NoNetBaseLayFragment;
import com.wzrd.v.view.RecycleResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lk on 2018/2/9.
 */

public class InboxFragment extends NoNetBaseLayFragment  implements RecycleResult {
    private  InboxFragmentBinding dataBinding;
    private String TAG="InboxFragment";
    private String imagepath;
    private  SwipeRefreshLayout swlaout;
    private RecyclerView  recycle;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        dataBinding= DataBindingUtil.inflate(inflater, R.layout.inbox_fragment, container, false);
        imagepath= SharedPreferencesUtil.getString(getContext(),"icon","");
        isPrepared = true;
        intidata();
        Refresh();
        Rypresenter rypresenter = new Rypresenter(this);
        rypresenter.getlast(recycle, "inbox");
        return dataBinding.getRoot();
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

    /**
     * 临时数据
     */
    private void intidata() {
        List<ContactMessage> list = new ArrayList<>();


        for (int i = 0; i < 19; i++) {
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

    /**
     * 判断滑动到底部
     * @param message
     */
    @Override
    public void last(String message) {
        Utils.ToastShort(getContext(),"滑动到底部.此处可以填写上拉刷新逻辑");

    }
}

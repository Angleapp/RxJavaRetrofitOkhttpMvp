package com.wzrd.v.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.wzrd.R;
import com.wzrd.m.been.MoviceBeen;
import com.wzrd.p.ParsetPresenter;
import com.wzrd.v.adapter.LyAadapter;
import com.wzrd.v.fragment.base.BaseFragment;
import com.wzrd.v.view.ResultView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by lk on 2017/10/30.
 */

public class ClassfitionFragment extends BaseFragment<ResultView, ParsetPresenter> {

    Unbinder unbinder;
    @BindView(R.id.lrv)
    LuRecyclerView lrv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.test, null);
        unbinder = ButterKnife.bind(this, view);
        this.presenter.parsemovice("", "36");
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Thread.currentThread();
                        try {
                            Thread.sleep(2000);
//                            srl.setRefreshing(false);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            }
        });
        return view;
    }

    @Override
    public void Result(Object result, String message) {

        MoviceBeen moviceBeen = (MoviceBeen) result;
        Log.e("moviceBeen", "moviceBeen-->" + moviceBeen.getNextPageUrl());
        List<MoviceBeen.ItemListBean> itemList = moviceBeen.getItemList();
        LyAadapter testRBadapter = new LyAadapter(getActivity(), itemList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        lrv.setLayoutManager(layoutManager);
        LuRecyclerViewAdapter luRecyclerViewAdapter = new LuRecyclerViewAdapter(testRBadapter);
        lrv.setAdapter(luRecyclerViewAdapter);
        testRBadapter.setDataList(itemList);
//        luRecyclerViewAdapter.addHeaderView(new SampleHeader(getActivity()));
        lrv.refreshComplete(10);
        lrv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {


            presenter.parsemovice("", "36");

            }
        });
        //设置底部加载颜色
        lrv.setFooterViewColor(R.color.colorAccent, R.color.colorAccent, android.R.color.white);
        //设置底部加载文字提示
        lrv.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");


    }

    @Override
    public void Errar(Object result) {

    }

    @Override
    public void Refresh(Object refresh, String message) {

    }


    @Override
    public ParsetPresenter bindPresenter() {
        return new ParsetPresenter();
    }

    @Override
    public ResultView bindView() {
        return this;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

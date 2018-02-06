package com.wzrd.v.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wzrd.R;
import com.wzrd.m.been.TSYSCONTANTS;
import com.wzrd.m.been.TSYSUSER;
import com.wzrd.m.db.manger.ContactsManager;
import com.wzrd.m.db.manger.UserManager;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.v.activity.contacts.AddContactsActivity;
import com.wzrd.v.adapter.ReclycleContactAdapter;
import com.wzrd.v.adapter.RecycleViewDivider;
import com.wzrd.v.fragment.base.NoNetBaseLayFragment;
import com.wzrd.v.view.GlideCircleTransform;
import com.wzrd.v.view.SwipeMenuLayout;

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
    @BindView(R.id.btnDelete)
    Button btnDelete;
    @BindView(R.id.rv_contacts)
    RecyclerView rvContacts;
    @BindView(R.id.sm)
    SwipeMenuLayout sm;
    private View view;
    private List<TSYSCONTANTS> tsyscontantsList;
    private String TAG = "ContactFragment";
    private String id;
    private UserManager manager;
    private List<TSYSUSER> userName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contact_fragment, null);
        unbinder = ButterKnife.bind(this, view);
        isPrepared = true;
        lazyLoad();
        return view;
    }

    /**
     * 从数据库中取值
     */
    private void initdata() {
        id = SharedPreferencesUtil.getString(getActivity(), "userphonenum", "");

        manager = new UserManager(getActivity());
        userName = manager.getByUserid(id);
        if (userName != null && userName.size() > 0) {
            tvAddlover.setVisibility(View.GONE);

            sm.setVisibility(View.VISIBLE);
            tvAddlover.setVisibility(View.GONE);
            Glide.with(view.getContext())
                    .load(userName.get(0).getT_sys_usericonpath())
                    .placeholder(R.mipmap.icon_signin_default)
                    .bitmapTransform(new GlideCircleTransform(view.getContext()))
                    .error(R.mipmap.icon_signin_default)
                    .into(ivLoverIocn);
            tvLoverName.setText(userName.get(0).getT_sys_lover_name());
        } else {
            sm.setVisibility(View.GONE);
            tvAddlover.setVisibility(View.VISIBLE);
        }

        ContactsManager contactsManager = new ContactsManager(getActivity());
        tsyscontantsList = contactsManager.getByid(id);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        isPrepared = true;
        lazyLoad();
    }

    @OnClick({R.id.tv_addlover, R.id.ll_lover, R.id.iv_add_family, R.id.btnDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_addlover:
                Intent intent1 = new Intent(getActivity(), AddContactsActivity.class);
                intent1.putExtra("message", "love");
                startActivity(intent1);
                break;
            case R.id.ll_lover:
                break;
            case R.id.iv_add_family:
                Intent intent = new Intent(getActivity(), AddContactsActivity.class);
                intent.putExtra("message", "contact");
                startActivity(intent);
                break;
            case R.id.btnDelete:

                if (userName != null && userName.size() > 0) {
                    manager.deleteUser(userName.get(0));
                }

                sm.setVisibility(View.GONE);
                tvAddlover.setVisibility(View.VISIBLE);

                break;
        }
    }


    @Override
    public void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initdata();
        setadapter();
        setonclick();
    }

    /**
     * 设置adapter
     */
    private void setadapter() {

        ReclycleContactAdapter adapter = new ReclycleContactAdapter(getContext(), tsyscontantsList);
        Log.e(TAG, "tsyscontantsList-->" + tsyscontantsList.size());
        rvContacts.addItemDecoration(new RecycleViewDivider(
                getActivity(), LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.cardview_shadow_start_color)));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvContacts.setLayoutManager(mLayoutManager);
        rvContacts.setAdapter(adapter);

    }

    /**
     * 点击空白部分消失  删除
     */
    private void setonclick() {
        rvContacts.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    SwipeMenuLayout viewCache = SwipeMenuLayout.getViewCache();
                    if (null != viewCache) {
                        viewCache.smoothClose();
                    }
                }
                return false;
            }
        });
    }
}

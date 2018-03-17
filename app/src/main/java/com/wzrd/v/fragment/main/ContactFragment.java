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
import com.wzrd.m.utils.DateUtils;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.m.utils.Utils;
import com.wzrd.p.inteface.AdapterClickPosition;
import com.wzrd.v.activity.contacts.AddContactsActivity;
import com.wzrd.v.activity.contacts.ContanctsMessageActivity;
import com.wzrd.v.adapter.ReclycleContactAdapter;
import com.wzrd.v.adapter.RecycleViewDivider;
import com.wzrd.v.fragment.base.NoNetBaseLayFragment;
import com.wzrd.v.view.GlideCircleTransform;
import com.wzrd.v.view.NumImageView;
import com.wzrd.v.view.SwipeMenuLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lk on 2018/1/3.
 */

public class ContactFragment extends NoNetBaseLayFragment implements AdapterClickPosition.position {
    @BindView(R.id.tv_addlover)
    TextView tvAddlover;
    @BindView(R.id.iv_lover_iocn)
    NumImageView ivLoverIocn;
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
    @BindView(R.id.ll_startlove)
    LinearLayout llStartlove;
    private View view;
    private List<TSYSCONTANTS> tsyscontantsList;
    private String TAG = "ContactFragment";
    private String id;
    private UserManager manager;
    private List<TSYSUSER> userName;

    //git pull origin master down   git push origin master up
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
            llLover.setVisibility(View.VISIBLE);
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
            llLover.setVisibility(View.GONE);
            tvAddlover.setVisibility(View.VISIBLE);
        }

        ContactsManager contactsManager = new ContactsManager(getActivity());
        tsyscontantsList = contactsManager.getAllUser();
        for (int i = 0; i < 10; i++) {
            String uuid = Utils.getuuid();
            TSYSCONTANTS modle = new TSYSCONTANTS(uuid, uuid, "测试" + i, uuid, uuid, "/storage/emulated/0/Photo_LJ/fd0bf2e399684aa29740baf6d83e865e.jpg",
                    "/storage/emulated/0/Photo_LJ/fd0bf2e399684aa29740baf6d83e865e.jpg",
                    DateUtils.getCurrentDate(), "", false);
            tsyscontantsList.add(modle);
        }

        llStartlove.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        llLover.setBackgroundColor(getResources().getColor(R.color.white_b9));
                        break;
                    case MotionEvent.ACTION_UP:
                        llLover.setBackgroundColor(0);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int mx = (int) motionEvent.getX();
                        int my = (int) motionEvent.getY();
                        if (Math.abs(mx - x) > 5 || Math.abs(my - y) > 5) {
                            llLover.setBackgroundColor(0);
                        }
                        break;
                    default:
                        llLover.setBackgroundColor(0);
                        break;

                }


                return false;
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        llLover.setBackgroundColor(this.getResources().getColor(R.color.settingBg));
        isPrepared = true;
        lazyLoad();
    }

    @OnClick({R.id.tv_addlover, R.id.ll_lover, R.id.iv_add_family, R.id.btnDelete, R.id.ll_startlove})
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
                llLover.setVisibility(View.GONE);
                close();

                break;
            case R.id.ll_startlove:
                Log.e("123", "1234");
//                llLover.setBackgroundColor(this.getResources().getColor(R.color.white_b9));
                if (userName != null && userName.size() > 0) {
                    Intent intent2 = new Intent(getActivity(), ContanctsMessageActivity.class);
                    intent2.putExtra("name", userName.get(0).getT_sys_lover_name());
                    getActivity().startActivity(intent2);
                }
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

        ReclycleContactAdapter adapter = new ReclycleContactAdapter(getContext(), tsyscontantsList, this);
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
                    close();
                }
                return false;
            }
        });
    }

    private void close() {
        SwipeMenuLayout viewCache = SwipeMenuLayout.getViewCache();
        if (null != viewCache) {
            viewCache.smoothClose();
        }
    }

    /**
     * recycleview 点击item监听
     *
     * @param position
     */
    @Override
    public void adapterposition(int position) {
        TSYSCONTANTS item = tsyscontantsList.get(position);
        Intent intent = new Intent(getActivity(), ContanctsMessageActivity.class);
        intent.putExtra("name", item.getT_sys_contacts_name());
        getActivity().startActivity(intent);
    }
}

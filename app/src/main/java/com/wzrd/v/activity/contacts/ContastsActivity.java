package com.wzrd.v.activity.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wzrd.R;
import com.wzrd.m.been.TSYSCONTANTS;
import com.wzrd.m.been.TSYSUSER;
import com.wzrd.m.db.manger.ContactsManager;
import com.wzrd.m.db.manger.UserManager;
import com.wzrd.m.utils.Constants;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.m.utils.Utils;
import com.wzrd.p.inteface.AdapterClickPosition;
import com.wzrd.v.adapter.RecycleViewDivider;
import com.wzrd.v.adapter.TimerContactAdapter;
import com.wzrd.v.view.GlideCircleTransform;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContastsActivity extends AppCompatActivity implements AdapterClickPosition.position {

    @BindView(R.id.tv_addlover)
    TextView tvAddlover;
    @BindView(R.id.iv_lover_iocn)
    ImageView ivLoverIocn;
    @BindView(R.id.tv_lover_name)
    TextView tvLoverName;
    @BindView(R.id.ll_lover)
    LinearLayout llLover;
    @BindView(R.id.rv_contacts)
    RecyclerView rvContacts;
    @BindView(R.id.iv_lovered)
    ImageView ivLovered;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    private List<TSYSCONTANTS> tsyscontantsList;
    private String TAG = "ContactFragment";
    private String id;
    private UserManager manager;
    private List<TSYSUSER> userName;
    private View view;
    private boolean ischeckdlover = false;
    private TimerContactAdapter adapter;
    private String type;//0 首页面  1 定时页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.activity_contasts, null);
        setContentView(view);
        ButterKnife.bind(this);
        type = getIntent().getExtras().getString("type");
        ischeked();
        initdata();
        setadapter();
    }

    /**
     * 设置adapter
     */
    private void setadapter() {

        adapter = new TimerContactAdapter(this, tsyscontantsList, this);
        rvContacts.addItemDecoration(new RecycleViewDivider(
                ContastsActivity.this, LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.cardview_shadow_start_color)));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ContastsActivity.this);
        rvContacts.setLayoutManager(mLayoutManager);
        rvContacts.setAdapter(adapter);

    }


    private void ischeked() {
        if (ischeckdlover) {
            ivLovered.setBackgroundResource(R.mipmap.icon_lover_choseed);
        } else {
            ivLovered.setBackgroundResource(R.mipmap.icon_lover_chose);
        }
    }

    private void initdata() {

        id = SharedPreferencesUtil.getString(ContastsActivity.this, "userphonenum", "");
        manager = new UserManager(ContastsActivity.this);
        userName = manager.getByUserid(id);
        if (userName != null && userName.size() > 0) {
            tvAddlover.setVisibility(View.GONE);
            Glide.with(view.getContext())
                    .load(userName.get(0).getT_sys_usericonpath())
                    .placeholder(R.mipmap.icon_signin_default)
                    .bitmapTransform(new GlideCircleTransform(view.getContext()))
                    .error(R.mipmap.icon_signin_default)
                    .into(ivLoverIocn);
            tvLoverName.setText(userName.get(0).getT_sys_lover_name());
        } else {
            if ("0".equals(type)) {
                tvAddlover.setVisibility(View.INVISIBLE);
            }else{
                tvAddlover.setVisibility(View.VISIBLE);
            }
        }

        ContactsManager contactsManager = new ContactsManager(ContastsActivity.this);
        tsyscontantsList = contactsManager.getByid(id);

    }

    @OnClick({R.id.tv_addlover, R.id.ll_lover, R.id.iv_lovered, R.id.tv_cancle, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_addlover:
                if (!"0".equals(type)) {
                    Intent intent1 = new Intent(ContastsActivity.this, AddContactsActivity.class);
                    intent1.putExtra("message", "love");
                    startActivity(intent1);
                }

                break;
            case R.id.ll_lover:
                break;
            case R.id.iv_lovered:
                ischeckdlover = !ischeckdlover;
                ischeked();
                break;
            case R.id.tv_cancle:
                finish();
                break;
            case R.id.tv_sure:
                List<TSYSCONTANTS> list = adapter.getcheckedlist();
                if (list.size() > 0 || ischeckdlover) {
                    Intent intent = new Intent();
                    String action;
                    if ("0".equals(type)) {
                        action = Constants.homeconstacts;
                    } else {
                        action = Constants.timeconstacts;
                    }
                    intent.setAction(action);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", (Serializable) list);
                    intent.putExtras(bundle);
                    intent.putExtra("id", ischeckdlover);
                    sendBroadcast(intent);
                    finish();
                } else {
                    Utils.ToastShort(this, "请至少选择一个爱人、家人、朋友");
                }

                break;

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        initdata();
        setadapter();
    }

    @Override
    public void adapterposition(int position) {

    }


}

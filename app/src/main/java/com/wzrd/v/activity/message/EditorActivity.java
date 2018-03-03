package com.wzrd.v.activity.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.been.TSYSCONTANTS;
import com.wzrd.m.utils.ActivityCollector;
import com.wzrd.m.utils.Constants;
import com.wzrd.m.utils.DateUtils;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.m.utils.Utils;
import com.wzrd.v.activity.calendar.CarcletarActivity;
import com.wzrd.v.activity.contacts.ContastsActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditorActivity extends AppCompatActivity {

    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.et_people)
    TextView etPeople;
    @BindView(R.id.ll_select_contsats)
    LinearLayout llSelectContsats;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_text)
    EditText etText;
    @BindView(R.id.fl_text)
    FrameLayout flText;
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter dynamic_filter;
    private boolean iscannext;
    private List<TSYSCONTANTS> list=new ArrayList<>();
    private Boolean exituser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        ActivityCollector.addTimerActivity(this);
        // 广播接收
        broadcastReceiver();
        // 注册广播
        registeBoardCast();
        tvNext.setTextColor(Color.parseColor("#969696"));

    }

    /**
     * 注册广播
     */
    private void registeBoardCast() {
        dynamic_filter = new IntentFilter();
        dynamic_filter.addAction(Constants.timeconstacts);
        this.registerReceiver(broadcastReceiver, dynamic_filter);
    }

    /**
     * 广播接收
     */
    private void broadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Constants.timeconstacts.equals(intent.getAction())) {

                    list = new ArrayList<>();
                    list.addAll((List<TSYSCONTANTS>) intent.getSerializableExtra("list"));
                    exituser = (Boolean) intent.getExtras().get("id");
                    StringBuffer buffer = new StringBuffer();
                    if (exituser) {
                        String username = SharedPreferencesUtil.getString(EditorActivity.this, "lovename", "");
                        buffer.append(username + ",");

                    }

                    for (int i = 0; i < list.size(); i++) {
                        buffer.append(list.get(i).getT_sys_contacts_name() + ",");
                    }

                    buffer.deleteCharAt(buffer.length() - 1);

                    etPeople.setText(buffer.toString());
                    iscannext = true;
                    tvNext.setTextColor(Color.parseColor("#ffffff"));
                }

            }
        };
    }


    @OnClick({R.id.tv_next, R.id.ll_select_contsats, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                //iscannext   判断联系人是否选择   暂时取消
                if (true) {
                    String s = etText.getText().toString();
                    if (s != null&&!"".equals(s)) {
                        Intent intent = new Intent(this, CarcletarActivity.class);
                        Bundle bundle = new Bundle();
                        if(list!=null&&list.size()==0){
                            String uuid = Utils.getuuid();
                            TSYSCONTANTS modle = new TSYSCONTANTS(uuid, uuid, "测试填充" , uuid, uuid, "/storage/emulated/0/Photo_LJ/fd0bf2e399684aa29740baf6d83e865e.jpg",
                                    "/storage/emulated/0/Photo_LJ/fd0bf2e399684aa29740baf6d83e865e.jpg",
                                    DateUtils.getCurrentDate(), "", false);
                            list.add(modle);
                        }
                        bundle.putSerializable("list", (Serializable) list);
                        intent.putExtras(bundle);
                        if(exituser==null){
                            exituser=false;
                        }
                        intent.putExtra("id", exituser);
                        startActivity(intent);
                    } else {
                        Utils.ToastShort(this, "请填写您要发送的内容");
                    }

                } else {
                    Utils.ToastShort(this, "请选择联系人");
                }

                break;
            case R.id.ll_select_contsats:
                Intent intent = new Intent(this, ContastsActivity.class);
                intent.putExtra("type","1");
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}

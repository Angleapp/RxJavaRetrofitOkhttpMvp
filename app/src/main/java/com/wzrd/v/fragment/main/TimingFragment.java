package com.wzrd.v.fragment.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wzrd.R;
import com.wzrd.m.been.TSYSCONTANTS;
import com.wzrd.m.utils.Constants;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.v.activity.MainActivity;
import com.wzrd.v.activity.message.EditorActivity;
import com.wzrd.v.view.popup.EixtPopupWindow;
import com.wzrd.v.view.popup.TimerPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lk on 2018/1/3.
 */

public class TimingFragment extends Fragment {
    @BindView(R.id.bt_editor)
    Button btEditor;
    Unbinder unbinder;
    private View view;
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter dynamic_filter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.timingfragment, null);
        unbinder = ButterKnife.bind(this, view);
        // 广播接收
        broadcastReceiver();
        // 注册广播
        registeBoardCast();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_editor)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), EditorActivity.class);
        startActivity(intent);

    }

    /**
     * 注册广播
     */
    private void registeBoardCast() {
        dynamic_filter = new IntentFilter();
        dynamic_filter.addAction(Constants.timeconstactsexit);
        getActivity().registerReceiver(broadcastReceiver, dynamic_filter);
    }

    /**
     * 广播接收
     */
    private void broadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Constants.timeconstactsexit.equals(intent.getAction())) {
                    List<TSYSCONTANTS> list = new ArrayList<>();
                    list.addAll((List<TSYSCONTANTS>) intent.getSerializableExtra("list"));
                    String  time = (String) intent.getExtras().get("time");
                    Boolean exituser = (Boolean) intent.getExtras().get("id");
                    StringBuffer buffer = new StringBuffer();
                    if (exituser) {
                        String username = SharedPreferencesUtil.getString(getActivity(), "lovename", "");
                        buffer.append(username + ",");

                    }

                    for (int i = 0; i < list.size(); i++) {
                        buffer.append(list.get(i).getT_sys_contacts_name() + ",");
                    }

                    buffer.deleteCharAt(buffer.length() - 1);
                    Log.e("buffer", "buffer---" + buffer.toString());
                    if(time!=null){
                        String a = "爱的消息将于 " + time + "发送";
                        TimerPopupWindow relifePopupWindow = new TimerPopupWindow(getActivity(), buffer.toString(), a);
                        relifePopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                    }


                }

            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }
}

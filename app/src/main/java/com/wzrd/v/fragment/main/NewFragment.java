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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.wzrd.R;
import com.wzrd.m.been.TSYSCONTANTS;
import com.wzrd.m.utils.Constants;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.m.utils.Utils;
import com.wzrd.v.activity.contacts.ContastsActivity;
import com.wzrd.v.activity.home.poem.PoemActivity;
import com.wzrd.v.activity.homepage.WisdomActivity;
import com.wzrd.v.activity.homepage.officeline.OfficelineActivity;
import com.wzrd.v.activity.homepage.virtual.VirtualGifActivity;
import com.wzrd.v.activity.message.MessagesActivity;
import com.wzrd.v.activity.message.TextActivity;
import com.wzrd.v.view.CircleLayout;
import com.wzrd.v.view.popup.SendPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lk on 2018/1/3.
 */

public class NewFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.sendMessage)
    ImageView mSendMessage;
    @BindView(R.id.message)
    LinearLayout mMessage;
    @BindView(R.id.gift)
    LinearLayout mGift;
    @BindView(R.id.poem)
    LinearLayout mPoem;
    @BindView(R.id.shy)
    LinearLayout mShy;
    @BindView(R.id.selfie)
    LinearLayout mSelfie;
    @BindView(R.id.wisdom)
    LinearLayout mWisdom;
    @BindView(R.id.offline)
    LinearLayout mOffline;
    @BindView(R.id.end)
    LinearLayout mEnd;
    @BindView(R.id.video)
    LinearLayout mVideo;
    @BindView(R.id.ll_select)
    LinearLayout llSelect;
    @BindView(R.id.bt_send)
    Button btSend;
    @BindView(R.id.et_contact)
    EditText etContact;
    @BindView(R.id.circle)
    CircleLayout circle;
    private View view;
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter dynamic_filter;
    private boolean iscannext;
    private List<TSYSCONTANTS> list;
    private Boolean exituser;
    private StringBuffer buffer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.new_fragmnet, null);
        unbinder = ButterKnife.bind(this, view);
        // 广播接收
        broadcastReceiver();
        // 注册广播
        registeBoardCast();
        setmargin();

        gif();
        return view;
    }

    private void setmargin() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int allheight = wm.getDefaultDisplay().getHeight();
        int height = llSelect.getHeight() + btSend.getHeight();
        int mag = allheight - height - 2 * width;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,mag/2,0,3*mag/4);//4个参数按顺序分别是左上右下
        circle.setLayoutParams(layoutParams);
        Log.e("mag", "'mag----" + mag);
    }


    /**
     * 加载本地gif图片
     */
    private void gif() {
        Glide.with(getActivity()).load(R.drawable.a001).into(mSendMessage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.sendMessage, R.id.message, R.id.gift, R.id.poem, R.id.shy, R.id.selfie, R.id.wisdom, R.id.offline, R.id.end, R.id.video, R.id.bt_send, R.id.ll_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sendMessage:
                break;
            case R.id.message:
                Intent messageIntent = new Intent(getActivity(), MessagesActivity.class);
                startActivity(messageIntent);
                break;
            case R.id.gift:
                startactivity(VirtualGifActivity.class);
                break;
            case R.id.poem:
                Intent poemIntent = new Intent(getActivity(), PoemActivity.class);
                startActivity(poemIntent);
                break;
            case R.id.shy:
                //口难开
                Intent textintent = new Intent(getActivity(), TextActivity.class);
                textintent.putExtra("title", "1");
                startActivity(textintent);
                break;
            case R.id.selfie:
                break;
            case R.id.wisdom:
                //智慧之语
                startactivity(WisdomActivity.class);
                break;
            case R.id.offline:
                startactivity(OfficelineActivity.class);
                break;
            case R.id.end:
                //结束语
                Intent endintent = new Intent(getActivity(), TextActivity.class);
                endintent.putExtra("title", "2");
                startActivity(endintent);
                break;
            case R.id.video:
                break;

            case R.id.bt_send:
                //发送
                String s = etContact.getText().toString();
                if (s != null && !"".equals(s)) {
                    SendPopupWindow relifePopupWindow = new SendPopupWindow(getActivity(), buffer.toString(), list, exituser);
                    relifePopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                } else {
                    Utils.ToastShort(getContext(), "还没有选择发送人");
                }
                break;
            case R.id.ll_select:
                Intent intent = new Intent(getActivity(), ContastsActivity.class);
                intent.putExtra("type", "0");
                startActivity(intent);
                break;
        }
    }


    /**
     * 广播接收
     */
    private void broadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Constants.homeconstacts.equals(intent.getAction())) {
                    list = new ArrayList<>();
                    list.addAll((List<TSYSCONTANTS>) intent.getSerializableExtra("list"));
                    exituser = (Boolean) intent.getExtras().get("id");
                    Log.e("list", "list--" + list.size() + "id---" + exituser);
                    buffer = new StringBuffer();
                    if (exituser) {
                        String username = SharedPreferencesUtil.getString(getActivity(), "lovename", "");
                        buffer.append(username + ",");
                    }

                    for (int i = 0; i < list.size(); i++) {
                        buffer.append(list.get(i).getT_sys_contacts_name() + ",");
                    }

                    buffer.deleteCharAt(buffer.length() - 1);
                    etContact.setText(buffer.toString());
                    iscannext = true;
                    etContact.setTextColor(Color.parseColor("#ffffff"));
                }

            }
        };
    }


    private void startactivity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        startActivity(intent);

    }

    /**
     * 注册广播
     */
    private void registeBoardCast() {
        dynamic_filter = new IntentFilter();
        dynamic_filter.addAction(Constants.homeconstacts);
        getActivity().registerReceiver(broadcastReceiver, dynamic_filter);
    }
}

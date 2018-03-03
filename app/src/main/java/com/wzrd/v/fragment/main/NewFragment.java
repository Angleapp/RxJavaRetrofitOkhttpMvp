package com.wzrd.v.fragment.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wzrd.R;
import com.wzrd.m.been.Poem;
import com.wzrd.m.been.TEXTIFORMATION;
import com.wzrd.m.been.TSYSCONTANTS;
import com.wzrd.m.db.manger.PoemManager;
import com.wzrd.m.db.manger.TextInformationManager;
import com.wzrd.m.utils.Constants;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.m.utils.Utils;
import com.wzrd.v.activity.contacts.ContastsActivity;
import com.wzrd.v.activity.home.camer.CamerActivity;
import com.wzrd.v.activity.home.poem.PoemActivity;
import com.wzrd.v.activity.home.video.VideoActivity;
import com.wzrd.v.activity.homepage.WisdomActivity;
import com.wzrd.v.activity.homepage.officeline.OfficelineActivity;
import com.wzrd.v.activity.homepage.virtual.VirtualGifActivity;
import com.wzrd.v.activity.message.MessagesActivity;
import com.wzrd.v.activity.message.TextActivity;
import com.wzrd.v.view.CircleLayout;
import com.wzrd.v.view.popup.PreviewPopupWindow;
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

public class NewFragment extends Fragment implements View.OnLongClickListener {
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
    TextView etContact;
    @BindView(R.id.circle)
    CircleLayout circle;
    @BindView(R.id.ll_select1)
    LinearLayout llSelect1;
    @BindView(R.id.tv_shy)
    TextView tvShy;
    @BindView(R.id.tv_end)
    TextView tvEnd;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    @BindView(R.id.tv_offline)
    TextView tvOffline;
    @BindView(R.id.tv_wisdom)
    TextView tvWisdom;
    @BindView(R.id.tv_selfile)
    TextView tvSelfile;
    @BindView(R.id.tv_poem)
    TextView tvPoem;
    @BindView(R.id.tv_gift)
    TextView tvGift;
    @BindView(R.id.ll_title_all)
    LinearLayout llTitleAll;
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
        setonlongclick();

        gif();
        setvisibility();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("onResume", "onResume");
        setvisibility();
    }

    /**
     * 下标的显示
     */
    private void setvisibility() {
        Drawable drawable = getResources().getDrawable(R.drawable.blue_circle_dot);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        //  1 口难开 2结束语 0 消息
        TextInformationManager manager = new TextInformationManager(getActivity());
        List<TEXTIFORMATION> list = new ArrayList<>();
        /**
         * 口难开
         */
        list = manager.getBytype("1");
        Log.e("list", "list1---" + list.size());
        if (list != null && list.size() > 0) {
            tvShy.setCompoundDrawables(drawable, null, null, null);
        } else {
            tvShy.setCompoundDrawables(null, null, null, null);
        }
        /**
         * 结束语
         */
        list = manager.getBytype("2");
        Log.e("list", "list2---" + list.size());

        if (list != null && list.size() > 0) {
            tvEnd.setCompoundDrawables(drawable, null, null, null);
        } else {
            tvEnd.setCompoundDrawables(null, null, null, null);
        }
        /**
         * 消息
         */
        list = manager.getBytype("0");
        Log.e("list", "list0---" + list.size());
        if (list != null && list.size() > 0) {
            tvMessage.setCompoundDrawables(drawable, null, null, null);
        } else {
            tvMessage.setCompoundDrawables(null, null, null, null);
        }
        //tvVideo  tvOffline  tvWisdom  tvSelfile  tvPoem  tvGift

        tvOffline.setCompoundDrawables(null, null, null, null);
        tvGift.setCompoundDrawables(null, null, null, null);
        tvSelfile.setCompoundDrawables(null, null, null, null);
        String wisdom = SharedPreferencesUtil.getString(getContext(), "wisdom", "");
        if ("wisdom".equals(wisdom)) {
            tvWisdom.setCompoundDrawables(drawable, null, null, null);
        } else {
            tvWisdom.setCompoundDrawables(null, null, null, null);
        }
        PoemManager poemManager = PoemManager.getInstance(getActivity());
        List<Poem> poem = poemManager.getAllPoem();
        if (poem != null && poem.size() > 0) {
            tvPoem.setCompoundDrawables(drawable, null, null, null);
        } else {
            tvPoem.setCompoundDrawables(null, null, null, null);
        }

    }

    /**
     * 设置圆心的距离
     */
    private void setmargin() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int allheight = wm.getDefaultDisplay().getHeight();
        int height = llSelect.getHeight() + btSend.getHeight();
        int mag = allheight - height - 2 * width;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, mag / 2, 0, 3 * mag / 4);//4个参数按顺序分别是左上右下
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

    @OnClick({R.id.message, R.id.gift, R.id.poem, R.id.shy, R.id.selfie, R.id.wisdom, R.id.offline, R.id.end, R.id.video, R.id.bt_send, R.id.ll_select, R.id.ll_select1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.message:
                Intent messageIntent = new Intent(getActivity(), MessagesActivity.class);
                startActivity(messageIntent);
                break;
            case R.id.gift:
                startactivity(VirtualGifActivity.class);
                break;
            case R.id.poem://诗歌
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
                startactivity(CamerActivity.class);
                break;
            case R.id.wisdom:
                //智慧之语

                Intent intent4 = new Intent(getActivity(), WisdomActivity.class);
                intent4.putExtra("type", "newfragment");
                getActivity().startActivity(intent4);
//                startactivity(WisdomActivity.class);
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
                //短视频
                Intent videoIntent = new Intent(getActivity(), VideoActivity.class);
                startActivity(videoIntent);
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
                start();
                break;
            case R.id.ll_select1:
                start();
                break;
        }
    }

    /**
     * 选择发送人
     */
    private void start() {
        Intent intent1 = new Intent(getActivity(), ContastsActivity.class);
        intent1.putExtra("type", "0");
        startActivity(intent1);
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

    /**
     * 不带参数的activity跳转
     *
     * @param c
     */
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

    private void showpop(int type, String previewmessage, String delmessage) {
        PreviewPopupWindow previewPopupWindow = new PreviewPopupWindow(getActivity(), type, previewmessage, delmessage);
        previewPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 10);
        previewPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setvisibility();
            }
        });
    }


    //1 短视屏 2 结束语 3 线下 4智慧之语 5自拍 6口难开 7诗歌 8虚拟礼物 9(消息里面) 文字 10 语音 11 相册 12相机
    private void setonlongclick() {
        mVideo.setOnLongClickListener(this);
        mEnd.setOnLongClickListener(this);
        mOffline.setOnLongClickListener(this);
        mWisdom.setOnLongClickListener(this);
        mSelfie.setOnLongClickListener(this);
        mShy.setOnLongClickListener(this);
        mPoem.setOnLongClickListener(this);
        mGift.setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.video://短视频
                showpop(1, "预览短视频", "清空短视频");
                break;
            case R.id.end://结束语
                showpop(2, "预览结束语", "清空结束语");
                break;
            case R.id.offline://线下
                showpop(3, "预览线下", "清空线下");
                break;
            case R.id.wisdom://智慧之语
                showpop(4, "预览智慧之语", "清空智慧之语");
                break;
            case R.id.selfie://自拍
                showpop(5, "预览自拍", "清空自拍");
                break;
            case R.id.shy://口难开
                showpop(6, "预览口难开", "清空口难开");
                break;
            case R.id.poem://诗歌
                showpop(7, "预览诗歌", "清空诗歌");
                break;
            case R.id.gift://虚拟礼物
                showpop(8, "预览虚拟礼物", "清空虚拟礼物");
                break;
        }
        return false;
    }
}

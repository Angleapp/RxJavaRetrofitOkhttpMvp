package com.wzrd.v.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wzrd.R;
import com.wzrd.v.activity.homepage.officeline.OfficelineActivity;
import com.wzrd.v.activity.homepage.virtual.VirtualGifActivity;
import com.wzrd.v.activity.message.MessagesActivity;

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
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.new_fragmnet, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.sendMessage, R.id.message, R.id.gift, R.id.poem, R.id.shy, R.id.selfie, R.id.wisdom, R.id.offline, R.id.end, R.id.video})
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
                break;
            case R.id.shy:
                break;
            case R.id.selfie:
                break;
            case R.id.wisdom:
                break;
            case R.id.offline:
                startactivity(OfficelineActivity.class);
                break;
            case R.id.end:
                break;
            case R.id.video:
                break;
        }
    }

    private void startactivity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        startActivity(intent);

    }
}

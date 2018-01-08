package com.wzrd.v.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.v.activity.message.MessagesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lk on 2018/1/3.
 */

public class NewFragment extends Fragment {
    @BindView(R.id.et_tocontancts)
    EditText etTocontancts;
    @BindView(R.id.short_video_iv)
    ImageView shortVideoIv;
    @BindView(R.id.short_video_tv)
    TextView shortVideoTv;
    @BindView(R.id.short_video)
    LinearLayout shortVideo;
    @BindView(R.id.message_iv)
    ImageView messageIv;
    @BindView(R.id.message_tv)
    TextView messageTv;
    @BindView(R.id.message)
    LinearLayout message;
    @BindView(R.id.virtual_gifts_iv)
    ImageView virtualGiftsIv;
    @BindView(R.id.virtual_gifts_tv)
    TextView virtualGiftsTv;
    @BindView(R.id.virtual_gifts)
    LinearLayout virtualGifts;
    @BindView(R.id.poetry_iv)
    ImageView poetryIv;
    @BindView(R.id.poetry_tv)
    TextView poetryTv;
    @BindView(R.id.poetry)
    LinearLayout poetry;
    @BindView(R.id.difficult_open_iv)
    ImageView difficultOpenIv;
    @BindView(R.id.difficult_open_tv)
    TextView difficultOpenTv;
    @BindView(R.id.difficult_open)
    LinearLayout difficultOpen;
    @BindView(R.id.take_iv)
    ImageView takeIv;
    @BindView(R.id.take_tv)
    TextView takeTv;
    @BindView(R.id.take)
    LinearLayout take;
    @BindView(R.id.wisdom_language_iv)
    ImageView wisdomLanguageIv;
    @BindView(R.id.wisdom_language_tv)
    TextView wisdomLanguageTv;
    @BindView(R.id.wisdom_language)
    LinearLayout wisdomLanguage;
    @BindView(R.id.office_iv)
    ImageView officeIv;
    @BindView(R.id.office_tv)
    TextView officeTv;
    @BindView(R.id.office)
    LinearLayout office;
    @BindView(R.id.conclusion_iv)
    ImageView conclusionIv;
    @BindView(R.id.conclusion_tv)
    TextView conclusionTv;
    @BindView(R.id.conclusion)
    LinearLayout conclusion;
    @BindView(R.id.ll_send)
    LinearLayout llSend;
    Unbinder unbinder;
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

    @OnClick({R.id.short_video, R.id.message, R.id.virtual_gifts, R.id.poetry, R.id.difficult_open, R.id.take, R.id.wisdom_language, R.id.office, R.id.conclusion, R.id.ll_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.short_video:
                break;
            case R.id.message:
                Intent intent = new Intent(getActivity(), MessagesActivity.class);
                startActivity(intent);
                break;
            case R.id.virtual_gifts:
                break;
            case R.id.poetry:
                break;
            case R.id.difficult_open:
                break;
            case R.id.take:
                break;
            case R.id.wisdom_language:
                break;
            case R.id.office:
                break;
            case R.id.conclusion:
                break;
            case R.id.ll_send:
                break;
        }
    }
}

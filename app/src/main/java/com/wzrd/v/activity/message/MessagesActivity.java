package com.wzrd.v.activity.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wzrd.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessagesActivity extends AppCompatActivity {

    @BindView(R.id.iv_message_back)
    ImageView ivMessageBack;
    @BindView(R.id.ll_text)
    LinearLayout llText;
    @BindView(R.id.ll_voice)
    LinearLayout llVoice;
    @BindView(R.id.ll_camera)
    LinearLayout llCamera;
    @BindView(R.id.ll_phone_album)
    LinearLayout llPhoneAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_message_back, R.id.ll_text, R.id.ll_voice, R.id.ll_camera, R.id.ll_phone_album})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_message_back:
                finish();
                break;
            case R.id.ll_text:
                startactivity(TextActivity.class);
                break;
            case R.id.ll_voice:
                break;
            case R.id.ll_camera:
                break;
            case R.id.ll_phone_album:
                break;
        }
    }

    public void startactivity(Class aclass) {
        Intent intent = new Intent(MessagesActivity.this, aclass);
        startActivity(intent);

    }
}

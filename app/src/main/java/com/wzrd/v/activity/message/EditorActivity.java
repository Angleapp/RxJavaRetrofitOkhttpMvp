package com.wzrd.v.activity.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.utils.ActivityCollector;
import com.wzrd.v.activity.contacts.ContastsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditorActivity extends AppCompatActivity {

    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.et_people)
    EditText etPeople;
    @BindView(R.id.ll_select_contsats)
    LinearLayout llSelectContsats;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);

    }


    @OnClick({R.id.tv_next, R.id.ll_select_contsats, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                break;
            case R.id.ll_select_contsats:
                Intent intent = new Intent(this, ContastsActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}

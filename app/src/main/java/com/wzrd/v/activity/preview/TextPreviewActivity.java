package com.wzrd.v.activity.preview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.been.TEXTIFORMATION;
import com.wzrd.m.db.manger.TextInformationManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TextPreviewActivity extends AppCompatActivity {

    @BindView(R.id.iv_message_back)
    ImageView ivMessageBack;
    @BindView(R.id.et_text)
    EditText etText;
    @BindView(R.id.tv_tile)
    TextView tvTile;
    @BindView(R.id.fl_text)
    FrameLayout flText;
    private String type;
    private String sqltype;

    //1 短视屏 2 结束语 3 线下 4智慧之语 5自拍 6口难开 7诗歌 8虚拟礼物 9(消息里面) 文字 10 语音 11 相册 12相机
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_preview);
        ButterKnife.bind(this);
        type = getIntent().getExtras().getString("type");

        if ("2".equals(type)) {
            tvTile.setText("结束语");
            sqltype = "2";
        } else if ("6".equals(type)) {
            tvTile.setText("口难开");
            sqltype = "1";
        } else {
            tvTile.setText("消息");
            sqltype = "0";
        }
        //  1 口难开 2结束语 0 消息
        TextInformationManager manager = new TextInformationManager(this);
        List<TEXTIFORMATION> list = new ArrayList<>();
        list = manager.getBytype(sqltype);
        if (list != null && list.size() > 0) {
            etText.setText(list.get(0).getT_pm_message());
            flText.setBackgroundResource(list.get(0).getT_pm_imagepath());
        }

    }

    @OnClick(R.id.iv_message_back)
    public void onViewClicked() {
        finish();
    }
}

package com.wzrd.v.activity.message;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.been.SelectBean;
import com.wzrd.m.been.TEXTIFORMATION;
import com.wzrd.m.db.manger.TextInformationManager;
import com.wzrd.m.utils.DateUtils;
import com.wzrd.m.utils.Savephoto;
import com.wzrd.m.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TextActivity extends AppCompatActivity {

    @BindView(R.id.iv_message_back)
    ImageView ivMessageBack;
    @BindView(R.id.tv_complete)
    TextView tvComplete;
    @BindView(R.id.et_text)
    EditText etText;
    @BindView(R.id.fl_text)
    FrameLayout flText;
    @BindView(R.id.iv_bage)
    TextView ivBage;
    private static final int SELECTCODE = 0x0002;
    private final String IMAGE_TYPE = "image/*";
    public static final int TEXT_IMAGE_REQUEST_CODE = 0x103;
    @BindView(R.id.tv_tile)
    TextView tvTile;
    @BindView(R.id.tv_shy)
    TextView tvShy;
    private String titlenum = "";//1 口难开
    private   SelectBean bean;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        ButterKnife.bind(this);
        titlenum = getIntent().getExtras().getString("title");
        tvTile.setText(titlename(titlenum));
    }

    /**
     * 根据titlenum 来判定标题名称
     *
     * @param titlenum 1 口难开 2结束语 0 消息
     */
    private String titlename(String titlenum) {
        if ("1".equals(titlenum)) {
            tvShy.setVisibility(View.VISIBLE);
            return "口难开";
        } else if ("2".equals(titlenum)) {
            tvShy.setVisibility(View.GONE);
            return "结束语";
        } else {
            tvShy.setVisibility(View.GONE);
            return "消息";
        }
    }

    @OnClick({R.id.iv_message_back, R.id.tv_complete, R.id.et_text, R.id.iv_bage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_message_back:
                finish();
                break;
            case R.id.tv_complete:
                 s = etText.getText().toString();
                 if(TextUtils.isEmpty(s)){
                     setdata();
                     finish();
                 }else {
                     Utils.ToastShort(this,"未填写内容");
                 }


                break;
            case R.id.et_text:
                break;
            case R.id.iv_bage:
                Intent intent = new Intent(this, SelectBackActivity.class);
                startActivityForResult(intent, SELECTCODE);

                break;
        }
    }

    /**
     * 保存数据
     */
    private void setdata() {
        TextInformationManager manager=new TextInformationManager(this);
        TEXTIFORMATION textiformation=new TEXTIFORMATION();
        textiformation.setT_pm_id(Utils.getuuid());
        if(bean==null){
            textiformation.setT_pm_imagepath(R.drawable.a19049ea3874d0bb4837f114095abd601);
        }else{
            textiformation.setT_pm_imagepath(bean.getPath());
        }
        textiformation.setT_pm_modify_time(DateUtils.getCurrentDate());
        textiformation.setT_pm_type(titlenum);
        textiformation.setT_pm_message(s);
        manager.insertUser(textiformation);



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (TEXT_IMAGE_REQUEST_CODE == requestCode && data != null) {
            Uri resultUri = data.getData();
            Bitmap bm = null;
            ContentResolver cr = this.getContentResolver();
            try {
                bm = BitmapFactory.decodeStream(cr.openInputStream(resultUri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String path = Environment.getExternalStorageDirectory() + "/Photo_LJ/";
            File file = new File(path);

            if (!file.exists()) {
                file.mkdirs();
            }
            String filePath = path + UUID.randomUUID() + ".jpg";
            Savephoto savephoto = new Savephoto();
            savephoto.save(bm, filePath);
            Drawable d = Drawable.createFromPath(filePath);
            flText.setBackground(d);
        } else if (SELECTCODE == requestCode && data != null && resultCode == 0x123) {

             bean = (SelectBean) data.getExtras().get("bean");
            if (bean != null) {
                flText.setBackgroundResource(bean.getPath());
            }

        }


    }
}

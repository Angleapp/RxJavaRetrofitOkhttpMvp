package com.wzrd.v.activity.message;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.been.TEXTIFORMATION;
import com.wzrd.m.db.manger.TextInformationManager;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.v.activity.home.camer.CamerActivity;
import com.wzrd.v.view.popup.PhotoPopupWindow;
import com.wzrd.v.view.popup.PreviewPopupWindow;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessagesActivity extends AppCompatActivity implements View.OnLongClickListener {

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
    private final String IMAGE_TYPE = "image/*";
    private static final int MESSIMAGE_REQUEST_CODE = 0x104;
    private static final int REQUEST_CODE_BEAUTIFY_IMAGE = 0x105;
    public static final String KEY = "beautify_image";
    @BindView(R.id.tv_text_ring)
    TextView tvTextRing;
    @BindView(R.id.tv_movice_ring)
    TextView tvMoviceRing;
    @BindView(R.id.tv_phone_album_ring)
    TextView tvPhoneAlbumRing;
    @BindView(R.id.tv_camera_ring)
    TextView tvCameraRing;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.activity_messages, null);
        setContentView(view);
        ButterKnife.bind(this);
        initvisivii();
        startonlongclick();

    }

    /**
     * 判断下面的小蓝点是否显示
     */
    private void initvisivii() {
        //tvTextRing  tvMoviceRing  tvPhoneAlbumRing  tvCameraRing
        Drawable drawable = getResources().getDrawable(R.drawable.blue_circle_dot_big);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        TextInformationManager manager = new TextInformationManager(this);
        /**
         * 消息
         */

        List<TEXTIFORMATION> list = manager.getBytype("0");
        if (list != null && list.size() > 0) {
            tvTextRing.setCompoundDrawables(drawable, null, null, null);
        } else {
            tvTextRing.setCompoundDrawables(null, null, null, null);
        }
        /**
         * 录音
         */
        String movice = SharedPreferencesUtil.getString(this, "moviceactivity", "");
        if("movice".equals(movice)){
            tvMoviceRing.setCompoundDrawables(drawable, null, null, null);
        }else{
            tvMoviceRing.setCompoundDrawables(null, null, null, null);
        }

        /**
         * 相机
         */
        String camer = SharedPreferencesUtil.getString(this, "cameractivity", "");
        if("camer".equals(camer)){
            tvCameraRing.setCompoundDrawables(drawable, null, null, null);
        }else{
            tvCameraRing.setCompoundDrawables(null, null, null, null);
        }

        /**
         * 相册
         */
        String Photolj = SharedPreferencesUtil.getString(this, "PhotoljActivity", "");
        if("Photolj".equals(Photolj)){
            tvPhoneAlbumRing.setCompoundDrawables(drawable, null, null, null);
        }else{
            tvPhoneAlbumRing.setCompoundDrawables(null, null, null, null);
        }

    }

    private void startonlongclick() {
        llText.setOnLongClickListener(this);
        llVoice.setOnLongClickListener(this);
        llCamera.setOnLongClickListener(this);
        llPhoneAlbum.setOnLongClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initvisivii();
    }

    @OnClick({R.id.iv_message_back, R.id.ll_text, R.id.ll_voice, R.id.ll_camera, R.id.ll_phone_album})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_message_back:
                finish();
                break;
            case R.id.ll_text:
//                startactivity(TextActivity.class);
                Intent endintent = new Intent(this, TextActivity.class);
                endintent.putExtra("title", "0");
                startActivity(endintent);
                break;
            case R.id.ll_voice:
                startactivity(MoviceActivity.class);
                break;
            case R.id.ll_camera:
                startactivity(CamerActivity.class);
                break;
            case R.id.ll_phone_album:

                startActivity(new Intent(this, PhotoljActivity.class));
//                strartcamera();
                break;
        }
    }


    public void startactivity(Class aclass) {
        Intent intent = new Intent(MessagesActivity.this, aclass);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MESSIMAGE_REQUEST_CODE && data != null) {
            PhotoPopupWindow popupWindow = new PhotoPopupWindow(MessagesActivity.this, data.getData());
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);


        }

    }


    //1 短视屏 2 结束语 3 线下 4智慧之语 5自拍 6口难开 7诗歌 8虚拟礼物 9(消息里面) 文字 10 语音 11 相册 12相机
    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.ll_text:
                showpop(9, "预览文字", "删除文字");
                break;
            case R.id.ll_voice:
                showpop(10, "预览语音", "删除语音");
                break;
            case R.id.ll_camera:
                showpop(12, "预览相机", "删除相机");
                break;
            case R.id.ll_phone_album:

                showpop(11, "预览相册", "删除相册");
                break;
        }
        return false;
    }

    private void showpop(int type, String previewmessage, String delmessage) {
        PreviewPopupWindow previewPopupWindow = new PreviewPopupWindow(this, type, previewmessage, delmessage);
        previewPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 10);
        previewPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                initvisivii();

            }
        });
    }
}

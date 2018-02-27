package com.wzrd.v.activity.message;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wzrd.R;
import com.wzrd.m.utils.Constants;
import com.wzrd.m.utils.Utils;
import com.wzrd.v.activity.home.camer.CamerActivity;
import com.wzrd.v.view.popup.PhotoPopupWindow;
import com.wzrd.v.view.popup.PreviewPopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MessagesActivity extends AppCompatActivity implements View.OnLongClickListener{

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
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.activity_messages, null);
        setContentView(view);
        ButterKnife.bind(this);

        startonlongclick();
;
    }

    private void startonlongclick() {
        llText.setOnLongClickListener(this);
        llVoice.setOnLongClickListener(this);
        llCamera.setOnLongClickListener(this);
        llPhoneAlbum.setOnLongClickListener(this);
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
                endintent.putExtra("title","0");
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

    private void strartcamera() {
        final Activity activity = MessagesActivity.this;
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Observer<Boolean>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        /**
                         * aBoolean为true  同意权限
                         * aBoolean为false 没同意权限
                         */

                        if (!aBoolean) {
                            Utils.ToastShort(activity, Constants.RDWISDPREMISS);
                        } else {
                            Intent intent = new Intent();
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            intent.setType(IMAGE_TYPE);
                            if (Build.VERSION.SDK_INT < 19) {
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                            } else {
                                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                            }
                            activity.startActivityForResult(intent, MESSIMAGE_REQUEST_CODE);
                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
        switch (view.getId()){
            case R.id.ll_text:
                showpop(9,"预览文字","删除文字");
                break;
            case R.id.ll_voice:
                showpop(10,"预览语音","删除语音");
                break;
            case R.id.ll_camera:
                showpop(11,"预览相册","删除相册");
                break;
            case R.id.ll_phone_album:
                showpop(12,"预览相机","删除相机");
                break;
        }
        return false;
    }
    private void showpop(int type, String previewmessage, String delmessage) {
        PreviewPopupWindow previewPopupWindow = new PreviewPopupWindow(this, type, previewmessage, delmessage);
        previewPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 10);
    }
}

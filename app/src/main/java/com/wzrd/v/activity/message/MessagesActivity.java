package com.wzrd.v.activity.message;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wzrd.R;
import com.wzrd.m.utils.Constants;
import com.wzrd.m.utils.Utils;
import com.yyx.beautifylib.model.BLBeautifyParam;
import com.yyx.beautifylib.ui.activity.BLBeautifyImageActivity;
import com.yyx.beautifylib.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

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
    private final String IMAGE_TYPE = "image/*";
    private static final int MESSIMAGE_REQUEST_CODE = 0x104;
    private static final int REQUEST_CODE_BEAUTIFY_IMAGE = 0x105;
    public static final String KEY = "beautify_image";




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
                strartcamera();

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

        if(requestCode==MESSIMAGE_REQUEST_CODE&&data!=null){
                    Intent intent = new Intent(MessagesActivity.this, BLBeautifyImageActivity.class);
            List<String> list=new ArrayList();
            BLBeautifyParam param = new BLBeautifyParam();
            list.add(data.getData()+"");
        intent.putExtra(KEY, param);
        ActivityUtils.startActivityForResult(MessagesActivity.this, intent, REQUEST_CODE_BEAUTIFY_IMAGE);
        }

    }


}

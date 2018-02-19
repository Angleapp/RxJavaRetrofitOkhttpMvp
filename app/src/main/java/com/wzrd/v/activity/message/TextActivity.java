package com.wzrd.v.activity.message;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wzrd.R;
import com.wzrd.m.been.SelectBean;
import com.wzrd.m.utils.Constants;
import com.wzrd.m.utils.Savephoto;
import com.wzrd.m.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_message_back, R.id.tv_complete, R.id.et_text, R.id.iv_bage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_message_back:
                finish();
                break;
            case R.id.tv_complete:
                finish();
                break;
            case R.id.et_text:
                break;
            case R.id.iv_bage:
//                bage();

                Intent intent = new Intent(this, SelectBackActivity.class);
                startActivityForResult(intent, SELECTCODE);

                break;
        }
    }

    private void bage() {

        final Activity activity = TextActivity.this;
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
                            activity.startActivityForResult(intent, TEXT_IMAGE_REQUEST_CODE);
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

            SelectBean bean = (SelectBean) data.getExtras().get("bean");
            if (bean != null) {
                flText.setBackgroundResource(bean.getPath());
            }

        }


    }
}

package com.wzrd.v.activity.message;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.muzhi.camerasdk.library.filter.util.ImageFilterTools;
import com.muzhi.camerasdk.library.views.HorizontalListView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wzrd.R;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.yyx.beautifylib.adapter.Filter_Effect_Adapter;
import com.yyx.beautifylib.model.Filter_Effect_Info;
import com.yyx.beautifylib.utils.FilterUtils;
import com.yyx.beautifylib.view.BLBeautifyImageView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class PhotoljActivity extends AppCompatActivity implements View.OnClickListener {
    private BLBeautifyImageView imageView;
    private HorizontalListView effect_listview;
    private final String IMAGE_TYPE = "image/*";
    public static final int TEXT_IMAGE_REQUEST_CODE = 0x103;
    private List<Filter_Effect_Info> mFilterData = new ArrayList<>(); //特效
    private Filter_Effect_Adapter mFilterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        imageView = (BLBeautifyImageView) findViewById(R.id.beautify_image);
        effect_listview = (HorizontalListView) findViewById(R.id.effect_listview);
        ImageView ivback = (ImageView) findViewById(R.id.iv_photoback);
        TextView tv_complete = (TextView) findViewById(R.id.tv_photocomplete);
        ivback.setOnClickListener(this);
        tv_complete.setOnClickListener(this);

        startPhoto();
        setadapter();
        setlistener();


    }

    private void setlistener() {
        effect_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {

                mFilterAdapter.setSelectItem(position);
                final int itemWidth = view.getWidth();
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        effect_listview.scrollTo(itemWidth * (position - 1) - itemWidth / 4);
                    }
                }, 100);

                ImageFilterTools.FilterType filterType = mFilterData.get(position).getFilterType();
                imageView.addFilter(filterType);
//                String path = imageView.save();

            }
        });

    }

    private void setadapter() {
        mFilterData = FilterUtils.getEffectList();
        mFilterAdapter = new Filter_Effect_Adapter(this, mFilterData);
        effect_listview.setAdapter(mFilterAdapter);

    }


    private void startPhoto() {
        final Activity activity = PhotoljActivity.this;
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
                            Toast.makeText(activity, "No Permissions For Read", Toast.LENGTH_SHORT).show();
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
        if (requestCode == TEXT_IMAGE_REQUEST_CODE && data != null) {
            Uri resultUri = data.getData();
            Bitmap bm = null;
            ContentResolver cr = this.getContentResolver();
            try {
                bm = BitmapFactory.decodeStream(cr.openInputStream(resultUri));
                imageView.setImage(bm);
//                Glide.with(MainActivity.this).load(resultUri).into(imageView);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_photoback:
                finish();
                break;
            case R.id.tv_photocomplete:
                String path = imageView.save();
                SharedPreferencesUtil.saveString(PhotoljActivity.this,"photolj",path);
                SharedPreferencesUtil.saveString(this,"PhotoljActivity","Photolj");
//                Utils.ToastShort(this, "图片保存地址--->" + path);
                finish();
                break;


        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

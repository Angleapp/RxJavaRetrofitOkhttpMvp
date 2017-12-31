package com.wzrd.v.activity.welcome;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wzrd.R;
import com.wzrd.databinding.ActivityIconBinding;
import com.wzrd.m.been.UserMessage;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.yyx.beautifylib.utils.BLConfigManager;

import java.io.File;

public class IconActivity extends AppCompatActivity {
    private String TAG="tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_icon);
        ActivityIconBinding binding= DataBindingUtil.setContentView(this, R.layout.activity_icon);
        binding.setItembeen(new UserMessage());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG,"requestCode-->"+requestCode);
        Log.e(TAG,"resultCode-->"+resultCode);
//        Log.e(TAG,"data-->"+data.getData());
        Uri originalUri = data.getData();
        Log.e(TAG,"data-->"+originalUri);
        UCrop uCrop = UCrop.of(originalUri, Uri.fromFile(new File(getCacheDir(), getPackageName())));
        uCrop.useSourceImageAspectRatio();
//        uCrop.withAspectRatio(3,2);
        UCrop.Options options = new UCrop.Options();
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.SCALE);
////        设置裁剪框自由移动
//        options.setFreeStyleCropEnabled(true);
//        //设置裁剪比例
//        options.setAspectRatioOptions(1, new AspectRatio(null, 5, 4), new AspectRatio(null, 16,9),new AspectRatio(getString(R.string.ucrop_label_original).toUpperCase(),
//                CropImageView.SOURCE_IMAGE_ASPECT_RATIO, CropImageView.SOURCE_IMAGE_ASPECT_RATIO), new AspectRatio(null, 3, 4), new AspectRatio(null, 6, 7));

        options.setStatusBarColor(BLConfigManager.getStatusBarColor());
        options.setToolbarColor(BLConfigManager.getToolBarColor());
        options.setActiveWidgetColor(BLConfigManager.getPrimaryColor());
        uCrop.withOptions(options);
        uCrop.start(IconActivity.this);

    }
}

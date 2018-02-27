package com.wzrd.v.activity.welcome;

import android.os.Bundle;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.CropOptions;

public class CropPhotoActivity extends TakePhotoActivity {

    private TakePhoto mTakePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_crop_photo);
        mTakePhoto = getTakePhoto();
        CropOptions cropOptions=new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();
       // mTakePhoto.onPickFromGalleryWithCrop(,cropOptions);
    }

    @Override
    public void takeSuccess(String imagePath) {
        super.takeSuccess(imagePath);
    }

    @Override
    public void takeFail(String msg) {
        super.takeFail(msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }
}

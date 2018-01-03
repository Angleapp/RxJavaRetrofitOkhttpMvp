package com.wzrd.m.utils;

import android.graphics.Bitmap;

/**
 * Created by lk on 2018/1/2.
 */

public class Savephoto {


    public void save(Bitmap bitmap,String filePath){
//

        checkImageAngle(bitmap, filePath);
    }

    private void checkImageAngle(Bitmap image, String mPhotoPath) {
        int degree = PhotoUtil.getbitmapdigree(mPhotoPath);
        if (degree != 0) {

            if (image != null) {
                Bitmap image2 = PhotoUtil.rotateBitmapByDegree(image, degree);
                SystemMethod.saveBitmap(mPhotoPath, image2);
                // image2.recycle();
                // image2 = null;
            }

        }
        // 如果不想压缩拍摄的照片，请注释掉下面的else里面的代码
        else {
            SystemMethod.saveBitmap(mPhotoPath, image);
        }
    }

}

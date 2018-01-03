package com.wzrd.v.activity.welcome;

import android.content.ContentResolver;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.wzrd.R;
import com.wzrd.databinding.ActivityIconBinding;
import com.wzrd.m.been.UserMessage;
import com.wzrd.m.utils.ActivityCollector;
import com.wzrd.m.utils.Savephoto;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.yyx.beautifylib.utils.BLConfigManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

public class IconActivity extends AppCompatActivity {
    private String TAG = "tag";
    private ActivityIconBinding binding;
    private UserMessage userMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_icon);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_icon);
        userMessage = new UserMessage();
//        binding.setMainPrecenter(new MainActivityPresenter(this));
        userMessage.setIconpath(SharedPreferencesUtil.getString(IconActivity.this, "icon", null));
        binding.setItembeen(userMessage);
        ActivityCollector.addActivity(this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "requestCode-->" + requestCode);
        Log.e(TAG, "resultCode-->" + resultCode);
        if (data != null) {
            switch (requestCode) {
                case UserMessage.IMAGE_REQUEST_CODE:
                    Uri originalUri = data.getData();
                    UCrop uCrop = UCrop.of(originalUri, Uri.fromFile(new File(getCacheDir(), getPackageName())));
                    uCrop.useSourceImageAspectRatio();
                    UCrop.Options options = new UCrop.Options();
                    options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.SCALE);
                    options.setStatusBarColor(BLConfigManager.getStatusBarColor());
                    options.setToolbarColor(BLConfigManager.getToolBarColor());
                    options.setActiveWidgetColor(BLConfigManager.getPrimaryColor());
                    uCrop.withOptions(options);
                    uCrop.start(IconActivity.this);

                    break;
                case 69:
                    Uri resultUri = UCrop.getOutput(data);
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
                    SharedPreferencesUtil.saveString(IconActivity.this, "icon", filePath);
                    Savephoto savephoto = new Savephoto();
                    savephoto.save(bm, filePath);
                    File file1 = new File(filePath);
                    String nickname = ((EditText) binding.getRoot().findViewById(R.id.et_nickname)).getText().toString();
                    binding.setItembeen(new UserMessage(nickname, filePath));
                    break;
                default:
                    break;
            }
        }

    }
}

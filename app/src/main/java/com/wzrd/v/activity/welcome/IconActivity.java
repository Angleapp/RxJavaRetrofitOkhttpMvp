package com.wzrd.v.activity.welcome;

import android.content.ContentResolver;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.wzrd.R;
import com.wzrd.databinding.ActivityIconBinding;
import com.wzrd.m.been.UserMessage;
import com.wzrd.m.utils.ActivityCollector;
import com.wzrd.m.utils.Savephoto;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.m.utils.Utils;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.yyx.beautifylib.utils.BLConfigManager;

import java.io.File;
import java.io.FileNotFoundException;

public class IconActivity extends AppCompatActivity {
    private String TAG = "tag";
    private ActivityIconBinding binding;
    private UserMessage userMessage;
    private String pathfrom = null;
    private EditText nickName;
    private Button mNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_icon);
        nickName = (EditText) findViewById(R.id.et_nickname);
        mNext = (Button) findViewById(R.id.bt_next);
        pathfrom = (String) getIntent().getExtras().get("pathfrom");
        if (pathfrom == null) {
            pathfrom = "下一步";
        }
        userMessage = new UserMessage();
        userMessage.setIconpath(SharedPreferencesUtil.getString(IconActivity.this, "icon", null));
        userMessage.setNickname(SharedPreferencesUtil.getString(IconActivity.this, "nickname", null));
        userMessage.setPathfrom(pathfrom);
        binding.setItembeen(userMessage);
        ActivityCollector.addActivity(this);
        nickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(nickName.getText().toString())) {
                    mNext.setAlpha(0.6f);
                } else {
                    mNext.setAlpha(1f);
                }
            }
        });
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
//                    options.setToolbarColor(BLConfigManager.getToolBarColor());
                    options.setActiveWidgetColor(BLConfigManager.getPrimaryColor());


//                    options.setStatusBarColor(Color.WHITE);
                    options.setToolbarColor(Color.BLACK);
//                    options.setActiveWidgetColor(Color.WHITE);
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
                    String filePath = path + Utils.getuuid() + ".jpg";
                    SharedPreferencesUtil.saveString(IconActivity.this, "icon", filePath);
                    Savephoto savephoto = new Savephoto();
                    savephoto.save(bm, filePath);
                    File file1 = new File(filePath);
                    String nickname = ((EditText) binding.getRoot().findViewById(R.id.et_nickname)).getText().toString();
                    binding.setItembeen(new UserMessage(nickname, filePath, pathfrom));
                    break;
                default:
                    break;
            }
        }

    }
}

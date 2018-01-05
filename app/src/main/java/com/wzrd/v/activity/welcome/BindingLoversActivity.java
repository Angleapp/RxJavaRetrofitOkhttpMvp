package com.wzrd.v.activity.welcome;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wzrd.R;
import com.wzrd.databinding.ActivityBindingLoversBinding;
import com.wzrd.m.been.LoveUser;
import com.wzrd.m.utils.ActivityCollector;
import com.wzrd.m.utils.SharedPreferencesUtil;

public class BindingLoversActivity extends AppCompatActivity {
    private ActivityBindingLoversBinding bindingLoversActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingLoversActivity = DataBindingUtil.setContentView(BindingLoversActivity.this, R.layout.activity_binding_lovers);
        LoveUser user = new LoveUser();
        user.setLovephone(SharedPreferencesUtil.getString(this, "lovephone", null));
        user.setLovename(SharedPreferencesUtil.getString(this, "lovename", null));
        bindingLoversActivity.setItembeen(user);
        ActivityCollector.addActivity(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (LoveUser.LOVERESULT == resultCode && data != null) {
            String name = (String) data.getExtras().get("lovename");
            String lovenum = (String) data.getExtras().get("lovenum");

//            Log.e("model", "model-->" + name + lovenum);
            bindingLoversActivity.setItembeen(new LoveUser(name, lovenum));

        }


    }
}

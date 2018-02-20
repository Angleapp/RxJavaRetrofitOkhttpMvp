package com.wzrd.v.activity.welcome;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.databinding.ActivityBindingLoversBinding;
import com.wzrd.m.been.LoveUser;
import com.wzrd.m.utils.ActivityCollector;
import com.wzrd.m.utils.SharedPreferencesUtil;

public class BindingLoversActivity extends AppCompatActivity {
    private ActivityBindingLoversBinding bindingLoversActivity;
    private TextView tvInfo;
    private EditText tvLoverPhone;
    private Button btBind;
    private Button btUnBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingLoversActivity = DataBindingUtil.setContentView(BindingLoversActivity.this, R.layout.activity_binding_lovers);
        LoveUser user = new LoveUser();
        user.setLovephone(SharedPreferencesUtil.getString(this, "lovephone", null));
        user.setLovename(SharedPreferencesUtil.getString(this, "lovename", null));
        bindingLoversActivity.setItembeen(user);
        ActivityCollector.addActivity(this);
        tvInfo = (TextView) findViewById(R.id.tv_info);
        tvLoverPhone = (EditText) findViewById(R.id.et_loverPhone);
        btBind = (Button) findViewById(R.id.bt_bind);
        btUnBind = (Button) findViewById(R.id.bt_unbind);
        tvInfo.setText(Html.fromHtml("NYc(13467589863)<br>希望和你绑定为情侣"));
        tvInfo.setText(Html.fromHtml("绑定我的情侣"));
        tvLoverPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = tvLoverPhone.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    btBind.setAlpha(0.6f);
                    btUnBind.setAlpha(0.6f);
                } else {
                    btBind.setAlpha(1f);
                    btUnBind.setAlpha(1f);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (LoveUser.LOVERESULT == resultCode && data != null) {
            String name = (String) data.getExtras().get("lovename");
            String lovenum = (String) data.getExtras().get("lovenum");
            bindingLoversActivity.setItembeen(new LoveUser(name, lovenum));
        }
    }
}

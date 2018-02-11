package com.wzrd.v.activity.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wzrd.R;
import com.wzrd.m.been.TSYSUSER;
import com.wzrd.m.db.manger.ContactsManager;
import com.wzrd.m.db.manger.UserFormManager;
import com.wzrd.m.db.manger.UserManager;
import com.wzrd.m.utils.ActivityCollector;
import com.wzrd.m.utils.DateUtils;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.m.utils.Utils;
import com.wzrd.p.RxTimerPresenter;
import com.wzrd.v.view.interf.TimerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity implements TimerView {

    @BindView(R.id.et_phoneNum)
    EditText etPhoneNum;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_sendSecurity)
    TextView tvSendSecurity;
    @BindView(R.id.bt_send)
    Button btSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ActivityCollector.addActivity(this);
        UserFormManager.getInstance(WelcomeActivity.this);
        UserManager.getInstance(WelcomeActivity.this);
        ContactsManager.getInstance(WelcomeActivity.this);
        ButterKnife.bind(this);
        etPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(etCode.getText().toString()) || TextUtils.isEmpty(etPhoneNum.getText().toString())) {
                    btSend.setAlpha(0.6f);
                } else {
                    btSend.setAlpha(1f);
                }
            }
        });
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(etCode.getText().toString()) || TextUtils.isEmpty(etPhoneNum.getText().toString())) {
                    btSend.setAlpha(0.6f);
                } else {
                    btSend.setAlpha(1f);
                }
            }
        });
        tvSendSecurity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {
                setSendCodeAlpha();
            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                setSendCodeAlpha();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                setSendCodeAlpha();
            }
        });
    }

    /**
     * 设置获取验证码的文本的透明度
     */
    private void setSendCodeAlpha() {
        String content = tvSendSecurity.getText().toString();
        if ("获取短信验证码".equals(content)) {
            tvSendSecurity.setAlpha(1f);
        } else {
            tvSendSecurity.setAlpha(0.6f);
        }
    }


    @OnClick({R.id.tv_sendSecurity, R.id.bt_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sendSecurity:
                RxTimerPresenter presenter = new RxTimerPresenter(WelcomeActivity.this);
                presenter.timer(60);
                break;
            case R.id.bt_send:
                send();
                break;
        }
    }

    /**
     * 点击开始之后的逻辑
     */

    private void send() {
        String phonenum = etPhoneNum.getText().toString();
        boolean mobileNO = Utils.isMobileNO(phonenum);
        if (true) {
            SharedPreferencesUtil.saveString(WelcomeActivity.this, "userphonenum", phonenum);
            UserManager manager = new UserManager(WelcomeActivity.this);
            TSYSUSER modile = new TSYSUSER();
            modile.setT_sys_id(phonenum);
            modile.setT_sys_userid(phonenum);
            modile.setT_sys_modify_id(phonenum);
            modile.setT_sys_userphone(phonenum);
            modile.setT_sys_username(phonenum);
            modile.setT_sys_modify_time(DateUtils.getCurrentDate());
            manager.insertUser(modile);
            Intent intent = new Intent(WelcomeActivity.this, IconActivity.class);
            intent.putExtra("path", "下一步");
            startActivity(intent);
        } else {
            Utils.ToastShort(WelcomeActivity.this, "请输入正确的手机号");
        }
    }

    @Override
    public void onCompile() {
        tvSendSecurity.setText("点击发送验证码");
        tvSendSecurity.setEnabled(true);
    }

    @Override
    public void onRefresh(Object message) {
        tvSendSecurity.setText(message + "可以重新发送");
    }

    @Override
    public void onError(Object message) {
        tvSendSecurity.setText("点击发送验证码");
        tvSendSecurity.setEnabled(true);
    }

    @Override
    public void onBegin(Object message) {
        tvSendSecurity.setEnabled(false);
        tvSendSecurity.setText(message + "可以重新发送");
    }
}

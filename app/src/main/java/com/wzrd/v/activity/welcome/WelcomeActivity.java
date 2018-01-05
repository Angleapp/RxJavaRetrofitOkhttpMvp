package com.wzrd.v.activity.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @BindView(R.id.et_phonenum)
    EditText etPhonenum;
    @BindView(R.id.tv_security)
    TextView tvSecurity;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_sendsecurity)
    TextView tvSendsecurity;
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
    }

    @OnClick({R.id.et_phonenum, R.id.tv_security, R.id.et_code, R.id.tv_sendsecurity, R.id.bt_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_phonenum:
                break;
            case R.id.tv_security:
                break;
            case R.id.et_code:
                break;
            case R.id.tv_sendsecurity:
                RxTimerPresenter presenter = new RxTimerPresenter(WelcomeActivity.this);
                presenter.timer(10);
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
        String phonenum=etPhonenum.getText().toString();
        boolean mobileNO = Utils.isMobileNO(phonenum);
        if (mobileNO) {
            SharedPreferencesUtil.saveString(WelcomeActivity.this,"userphonenum",phonenum);
            UserManager manager=new UserManager(WelcomeActivity.this);
            TSYSUSER modile=new TSYSUSER();
            modile.setT_sys_id(phonenum);
            modile.setT_sys_userid(phonenum);
            modile.setT_sys_modify_id(phonenum);
            modile.setT_sys_userphone(phonenum);
            modile.setT_sys_username(phonenum);
            modile.setT_sys_modify_time(DateUtils.getCurrentDate());
            manager.insertUser(modile);
            Intent intent = new Intent(WelcomeActivity.this, IconActivity.class);
            intent.putExtra("path","下一步");
            startActivity(intent);
        } else {
            Utils.ToastShort(WelcomeActivity.this, "请输入正确的手机号");
        }
    }

    @Override
    public void onCompile() {
        tvSendsecurity.setText("点击发送验证码");
        tvSendsecurity.setEnabled(true);
    }

    @Override
    public void onRefresh(Object message) {
        tvSendsecurity.setText(message + "可以重新发送");
    }

    @Override
    public void onError(Object message) {
        tvSendsecurity.setText("点击发送验证码");
        tvSendsecurity.setEnabled(true);
    }

    @Override
    public void onBegin(Object message) {
        tvSendsecurity.setEnabled(false);
        tvSendsecurity.setText(message + "可以重新发送");
    }
}

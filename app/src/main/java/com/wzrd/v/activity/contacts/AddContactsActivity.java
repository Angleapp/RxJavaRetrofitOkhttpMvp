package com.wzrd.v.activity.contacts;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wzrd.R;
import com.wzrd.m.been.TSYSCONTANTS;
import com.wzrd.m.been.TSYSUSER;
import com.wzrd.m.contacts.ContactsActivity;
import com.wzrd.m.db.manger.ContactsManager;
import com.wzrd.m.db.manger.UserManager;
import com.wzrd.m.utils.Constants;
import com.wzrd.m.utils.DateUtils;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.m.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class AddContactsActivity extends AppCompatActivity {


    private final int CONTSCTRESULT = 0x09;
    @BindView(R.id.toolbar_back)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_menu)
    ImageView mToolbarMenu;
    @BindView(R.id.toolbar_menu_text)
    TextView mToolbarMenuText;
    @BindView(R.id.et_contactphonenum)
    EditText mEtContactphonenum;
    @BindView(R.id.iv_contants)
    ImageView mIvContants;
    @BindView(R.id.bt_add)
    Button mBtAdd;

    private String name;
    private String lovenum;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        ButterKnife.bind(this);
        Utils.backToolbar(this, mToolbarBack, mToolbarTitle, "添加家人与朋友", mToolbarMenu, 0, null, mToolbarMenuText, "");
        message = getIntent().getExtras().getString("message");
    }

    @OnClick({R.id.iv_contants, R.id.bt_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_contants:
                start();
                break;
            case R.id.bt_add:
                String username = SharedPreferencesUtil.getString(this, "nickname", "");
                String userphonenum = SharedPreferencesUtil.getString(this, "userphonenum", "");
                String icon = SharedPreferencesUtil.getString(this, "icon", "");

                if ("love".equals(message)) {
                    UserManager manager = new UserManager(this);
                    TSYSUSER tsysuser = new TSYSUSER();
                    tsysuser.setT_sys_loverphone(lovenum);
                    tsysuser.setT_sys_lover_name(name);
                    tsysuser.setT_sys_userid(userphonenum);
                    tsysuser.setT_sys_usericonpath(icon);
                    tsysuser.setT_sys_userphone(userphonenum);
                    tsysuser.setT_sys_modify_time(DateUtils.getCurrentDate());
                    tsysuser.setT_sys_id(userphonenum);
                    tsysuser.setT_sys_username(username);
                    tsysuser.setT_sys_modify_id(userphonenum);
                    SharedPreferencesUtil.saveString(this, "lovename", name);
                    SharedPreferencesUtil.saveString(this, "lovephone", lovenum);
                    manager.insertUser(tsysuser);


                } else {
                    if (lovenum != null) {

                        ContactsManager manager = new ContactsManager(this);
                        TSYSCONTANTS tsyscontants = new TSYSCONTANTS();
                        tsyscontants.setT_sys_contacts_id(lovenum);
                        tsyscontants.setT_sys_contacts_name(name);
                        tsyscontants.setT_sys_contactsconpath(icon);
                        tsyscontants.setT_sys_id(lovenum);
                        tsyscontants.setT_sys_modify_time(DateUtils.getCurrentDate());
                        tsyscontants.setT_sys_userid(userphonenum);
                        tsyscontants.setT_sys_userphone(userphonenum);
                        tsyscontants.setT_sys_usericonpath(icon);
                        manager.insertUser(tsyscontants);
                    }
                }
                finish();

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONTSCTRESULT && data != null) {
            name = (String) data.getExtras().get("lovename");
            lovenum = (String) data.getExtras().get("lovenum");
            mEtContactphonenum.setText(name);
        }
    }

    public void start() {
        RxPermissions rxPermissions = new RxPermissions(AddContactsActivity.this);
        rxPermissions
                .request(Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SYNC_SETTINGS)
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
                            Utils.ToastShort(AddContactsActivity.this, Constants.RDWISDCONTANTS);
                        } else {
                            Intent intent = new Intent(AddContactsActivity.this, ContactsActivity.class);
                            intent.putExtra("pathfrom", "add");
                            AddContactsActivity.this.startActivityForResult(intent, CONTSCTRESULT);

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
}

package com.wzrd.m.been;

import android.Manifest;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Build;
import android.text.Editable;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wzrd.BR;
import com.wzrd.m.db.manger.UserManager;
import com.wzrd.m.utils.Constants;
import com.wzrd.m.utils.DateUtils;
import com.wzrd.m.utils.SharedPreferencesUtil;
import com.wzrd.m.utils.Utils;
import com.wzrd.v.activity.welcome.BindingLoversActivity;
import com.wzrd.v.activity.welcome.IconActivity;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static com.wzrd.m.utils.SharedPreferencesUtil.getString;


/**
 * Created by lk on 2017/12/31.
 */

public class UserMessage extends BaseObservable {

    private String nickname;//昵称
    private String iconpath;//头像地址
    private String pathfrom;//那个页面跳转过来的
    private final String IMAGE_TYPE = "image/*";
    public static final int IMAGE_REQUEST_CODE = 0x102;
    private IconActivity activity;

    public UserMessage(String nickname, String iconpath, String pathfrom) {
        this.nickname = nickname;
        this.iconpath = iconpath;
        this.pathfrom = pathfrom;
//
    }

    public UserMessage() {
    }

    public String getPathfrom() {
        return pathfrom;
    }

    public void setPathfrom(String pathfrom) {
        this.pathfrom = pathfrom;
    }
    @Bindable
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
        notifyPropertyChanged(BR.nickname);
    }

    public String getIconpath() {
        return iconpath;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath;
    }

    /**
     * 选择头像的监听
     *
     * @param view
     */
    public void onClickView(View view) {

        if (Build.VERSION.SDK_INT > 21) {
            activity = (IconActivity) view.getContext();
        } else {
            activity = (IconActivity) view.getRootView().getContext();
        }


        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
                            Utils.ToastShort(activity, Constants.RDWISDPREMISS);
                        } else {

                            Intent intent = new Intent();
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            intent.setType(IMAGE_TYPE);
                            if (Build.VERSION.SDK_INT < 19) {
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                            } else {
                                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                            }
                            activity.startActivityForResult(intent, IMAGE_REQUEST_CODE);
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

    /**
     * editview 内容改变的监听
     *
     * @param
     */
    public void onChangClickView(Editable view) {

        this.nickname = view.toString();
        setNickname(this.nickname);
    }

    /**
     * 点击下一步的监听
     *
     * @param view
     */

    public void onNextClickView(View view) {
        IconActivity activity;
        if (Build.VERSION.SDK_INT > 21) {
            activity = (IconActivity) view.getContext();
        } else {
            activity = (IconActivity) view.getRootView().getContext();
        }
        if (nickname == null || "".equals(nickname)) {
            Utils.ToastShort(activity, "昵称为空，请填写");
        } else {
            UserManager manager = new UserManager(activity);
            List<TSYSUSER> user = manager.getByUserid(SharedPreferencesUtil.getString(activity, "userphonenum", ""));

            SharedPreferencesUtil.saveString(activity, "nickname", this.nickname);
            if (user != null && user.size() > 0) {
                String usernum = getString(activity, "userphonenum", null);
                String icon = SharedPreferencesUtil.getString(activity, "icon", "");
                if (usernum != null) {
                    TSYSUSER modlie = new TSYSUSER();
                    String userphone = user.get(0).getT_sys_id();
                    modlie.setT_sys_modify_time(DateUtils.getCurrentDate());
                    modlie.setT_sys_id(usernum);
                    modlie.setT_sys_username(this.nickname);
                    modlie.setT_sys_usericonpath(icon);
                    modlie.setT_sys_id(userphone);
                    modlie.setT_sys_userid(userphone);
                    modlie.setT_sys_modify_id(userphone);
                    modlie.setT_sys_userphone(userphone);
                    modlie.setT_sys_username(userphone);
                    modlie.setT_sys_lover_name(user.get(0).getT_sys_lover_name());
                    modlie.setT_sys_loverphone(user.get(0).getT_sys_loverphone());
                    manager.insertUser(modlie);
                }


            }


            if ("下一步".equals(this.pathfrom)) {
                Intent intent = new Intent(activity, BindingLoversActivity.class);
                activity.startActivity(intent);
            } else {
                activity.finish();
            }
        }
    }


}

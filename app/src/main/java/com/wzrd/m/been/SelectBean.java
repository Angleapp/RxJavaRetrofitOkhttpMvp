package com.wzrd.m.been;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import com.android.databinding.library.baseAdapters.BR;
import com.bumptech.glide.Glide;
import com.wzrd.v.activity.message.SelectBackActivity;

import java.io.Serializable;

/**
 * Created by lk on 2018/2/19.
 */

public class SelectBean extends BaseObservable implements Serializable {
    private int path;
    private String id;
    private String message;
    private boolean isCheckd;

    public SelectBean() {
    }

    public SelectBean(int path, String id, String message, boolean isCheckd) {
        this.path = path;
        this.id = id;
        this.message = message;
        this.isCheckd = isCheckd;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Bindable
    public boolean isCheckd() {
        return isCheckd;
    }

    public void setCheckd(boolean checkd) {
        isCheckd = checkd;
        notifyPropertyChanged(BR.checkd);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @BindingAdapter({"selectpath"})
    public static void grildpig(ImageView imageView, int path) {
        Glide.with(imageView.getContext()).load(path).asBitmap().into(imageView);
    }


    public void backonclick(View view) {
         SelectBackActivity activity;
        if (Build.VERSION.SDK_INT > 21) {
            activity = (SelectBackActivity) view.getContext();
        } else {
            activity = (SelectBackActivity) view.getRootView().getContext();
        }
        activity.finish();

    }
}

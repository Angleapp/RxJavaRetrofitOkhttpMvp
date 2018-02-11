package com.wzrd.m.been;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.wzrd.BR;
import com.wzrd.m.utils.DateUtils;
import com.wzrd.m.utils.Utils;
import com.wzrd.v.activity.welcome.BindingLoversActivity;
import com.wzrd.v.fragment.contanct.OutboxFragment;


/**
 * Created by lk on 2018/2/9.
 */

public class ContactMessage extends BaseObservable {
    private String username;
    private String userid;
    private String messge;
    private String motifitytime;
    private String isupte;
    private String isbox;
    private String iconpath;
    private boolean isSend;//是否已经发送，根据时间判断
    private boolean isCancle = false;//是否取消发送了
    private String messageid;

    public ContactMessage() {
    }

    public ContactMessage(String username, String userid, String messge, String motifitytime, String isupte, String isbox, String iconpath, boolean isCancle, String messageid) {
        this.username = username;
        this.userid = userid;
        this.messge = messge;
        this.motifitytime = motifitytime;
        this.isupte = isupte;
        this.isbox = isbox;
        this.iconpath = iconpath;
        this.isCancle = isCancle;
        this.messageid = messageid;
    }

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    @Bindable
    public boolean isCancle() {
        return isCancle;
    }

    public void setCancle(boolean cancle) {
        isCancle = cancle;
        notifyPropertyChanged(BR.cancle);
    }

    public String getIconpath() {
        return iconpath;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMessge() {
        return messge;
    }

    public void setMessge(String messge) {
        this.messge = messge;
    }

    public String getMotifitytime() {
        return motifitytime;
    }

    public void setMotifitytime(String motifitytime) {
        this.motifitytime = motifitytime;
    }

    public String getIsupte() {
        return isupte;
    }

    public void setIsupte(String isupte) {
        this.isupte = isupte;
    }

    public String getIsbox() {
        return isbox;
    }

    public void setIsbox(String isbox) {
        this.isbox = isbox;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * 信息字段的拼接
     *
     * @param name
     * @return
     */
    public String getinboxmessage(String name) {
        return "来自 " + name + " 爱的消息";
    }


    /**
     * 根据时间判断此条信息是否已经发送
     *
     * @param sendtime
     * @return false 显示
     */
    public boolean issend(String sendtime) {
        String currenttime = DateUtils.getCurrentDate();
        boolean b = Utils.compareTime(sendtime, currenttime);
        return b;
    }


    /**
     * 判断撤回重发是否显示
     *
     * @param sendtime
     * @param isCancle
     * @return
     */
    public boolean isCancleVisibility(String sendtime, boolean isCancle) {
        boolean issend = issend(sendtime);
        if (issend && !isCancle) {
            return true;
        }
        return false;
    }

    /**
     * 判断删除是否显示
     *
     * @param sendtime
     * @param isCancle
     * @return
     */
    public boolean isDelVisibility(String sendtime, boolean isCancle) {
        boolean issend = issend(sendtime);

        if (!issend) {
            return true;
        }
        if (isCancle) {
            return true;
        }

        if (!issend && isCancle) {
            return true;
        }
        return false;
    }

    /**
     * 撤销监听
     *
     * @param v
     */
    public void CancleClick(View v) {
        setCancle(true);
    }

    /**
     * 数据删除
     */
    public void RemoveItem(View view) {
         OutboxFragment fragment=new OutboxFragment();
         fragment.onRefresh(this.messageid);
    }




}

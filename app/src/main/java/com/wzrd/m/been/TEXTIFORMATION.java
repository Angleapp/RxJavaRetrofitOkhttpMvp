package com.wzrd.m.been;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by lk on 2018/2/26.
 */
@Entity
public class TEXTIFORMATION {
    @Id
    private String t_pm_id;//主键id
    private String t_pm_userid;
    private String t_pm_username;
    private String t_pm_type;//1 口难开 2结束语 0 消息
    private int t_pm_imagepath;
    private String t_pm_modify_time;//最后修改时间
    private String t_pm_modify_id;//最后用户id
    private String t_pm_message;
    @Generated(hash = 2097400202)
    public TEXTIFORMATION() {
    }
   @Generated(hash = 258356547)
public TEXTIFORMATION(String t_pm_id, String t_pm_userid, String t_pm_username, String t_pm_type, int t_pm_imagepath, String t_pm_modify_time, String t_pm_modify_id, String t_pm_message) {
    this.t_pm_id = t_pm_id;
    this.t_pm_userid = t_pm_userid;
    this.t_pm_username = t_pm_username;
    this.t_pm_type = t_pm_type;
    this.t_pm_imagepath = t_pm_imagepath;
    this.t_pm_modify_time = t_pm_modify_time;
    this.t_pm_modify_id = t_pm_modify_id;
    this.t_pm_message = t_pm_message;
}
    public String getT_pm_message() {
        return t_pm_message;
    }

    public void setT_pm_message(String t_pm_message) {
        this.t_pm_message = t_pm_message;
    }

    public String getT_pm_id() {
        return t_pm_id;
    }

    public void setT_pm_id(String t_pm_id) {
        this.t_pm_id = t_pm_id;
    }

    public String getT_pm_userid() {
        return t_pm_userid;
    }

    public void setT_pm_userid(String t_pm_userid) {
        this.t_pm_userid = t_pm_userid;
    }

    public String getT_pm_username() {
        return t_pm_username;
    }

    public void setT_pm_username(String t_pm_username) {
        this.t_pm_username = t_pm_username;
    }

    public String getT_pm_type() {
        return t_pm_type;
    }

    public void setT_pm_type(String t_pm_type) {
        this.t_pm_type = t_pm_type;
    }

    public int getT_pm_imagepath() {
        return t_pm_imagepath;
    }

    public void setT_pm_imagepath(int t_pm_imagepath) {
        this.t_pm_imagepath = t_pm_imagepath;
    }

    public String getT_pm_modify_time() {
        return t_pm_modify_time;
    }

    public void setT_pm_modify_time(String t_pm_modify_time) {
        this.t_pm_modify_time = t_pm_modify_time;
    }

    public String getT_pm_modify_id() {
        return t_pm_modify_id;
    }

    public void setT_pm_modify_id(String t_pm_modify_id) {
        this.t_pm_modify_id = t_pm_modify_id;
    }
}

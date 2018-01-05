package com.wzrd.m.been;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by lk on 2018/1/4.
 */
@Entity
public class TSYSUSER {
    @Id
    private String t_sys_id;//主键id
    private String t_sys_userid;//用户的id
    private String t_sys_username;//用户姓名
    private String t_sys_lover_name;//爱人姓名
    private String t_sys_lover_id;//爱人id
    private String t_sys_userphone;//用户手机号
    private String t_sys_usericonpath;//用户头像地址
    private String t_sys_lovericonpath;//爱人头像地址
    private String t_sys_loverphone;//爱人头像地址
    private String t_sys_modify_time;//最后修改时间
    private String t_sys_modify_id;//最后用户id


    @Generated(hash = 793574458)
    public TSYSUSER(String t_sys_id, String t_sys_userid, String t_sys_username, String t_sys_lover_name, String t_sys_lover_id, String t_sys_userphone, String t_sys_usericonpath, String t_sys_lovericonpath, String t_sys_loverphone, String t_sys_modify_time, String t_sys_modify_id) {
        this.t_sys_id = t_sys_id;
        this.t_sys_userid = t_sys_userid;
        this.t_sys_username = t_sys_username;
        this.t_sys_lover_name = t_sys_lover_name;
        this.t_sys_lover_id = t_sys_lover_id;
        this.t_sys_userphone = t_sys_userphone;
        this.t_sys_usericonpath = t_sys_usericonpath;
        this.t_sys_lovericonpath = t_sys_lovericonpath;
        this.t_sys_loverphone = t_sys_loverphone;
        this.t_sys_modify_time = t_sys_modify_time;
        this.t_sys_modify_id = t_sys_modify_id;
    }

    @Generated(hash = 1172787418)
    public TSYSUSER() {
    }

    public String getT_sys_loverphone() {
        return t_sys_loverphone;
    }

    public void setT_sys_loverphone(String t_sys_loverphone) {
        this.t_sys_loverphone = t_sys_loverphone;
    }

    public String getT_sys_id() {
        return t_sys_id;
    }

    public void setT_sys_id(String t_sys_id) {
        this.t_sys_id = t_sys_id;
    }

    public String getT_sys_userid() {
        return t_sys_userid;
    }

    public void setT_sys_userid(String t_sys_userid) {
        this.t_sys_userid = t_sys_userid;
    }

    public String getT_sys_username() {
        return t_sys_username;
    }

    public void setT_sys_username(String t_sys_username) {
        this.t_sys_username = t_sys_username;
    }

    public String getT_sys_lover_name() {
        return t_sys_lover_name;
    }

    public void setT_sys_lover_name(String t_sys_lover_name) {
        this.t_sys_lover_name = t_sys_lover_name;
    }

    public String getT_sys_lover_id() {
        return t_sys_lover_id;
    }

    public void setT_sys_lover_id(String t_sys_lover_id) {
        this.t_sys_lover_id = t_sys_lover_id;
    }

    public String getT_sys_userphone() {
        return t_sys_userphone;
    }

    public void setT_sys_userphone(String t_sys_userphone) {
        this.t_sys_userphone = t_sys_userphone;
    }

    public String getT_sys_usericonpath() {
        return t_sys_usericonpath;
    }

    public void setT_sys_usericonpath(String t_sys_usericonpath) {
        this.t_sys_usericonpath = t_sys_usericonpath;
    }

    public String getT_sys_lovericonpath() {
        return t_sys_lovericonpath;
    }

    public void setT_sys_lovericonpath(String t_sys_lovericonpath) {
        this.t_sys_lovericonpath = t_sys_lovericonpath;
    }

    public String getT_sys_modify_time() {
        return t_sys_modify_time;
    }

    public void setT_sys_modify_time(String t_sys_modify_time) {
        this.t_sys_modify_time = t_sys_modify_time;
    }

    public String getT_sys_modify_id() {
        return t_sys_modify_id;
    }

    public void setT_sys_modify_id(String t_sys_modify_id) {
        this.t_sys_modify_id = t_sys_modify_id;
    }
}

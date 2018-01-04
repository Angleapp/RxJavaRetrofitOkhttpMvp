package com.wzrd.m.been;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by lk on 2018/1/4.
 */
@Entity
public class TSYSCONTANTS {
    @Id
    private String t_sys_id;//主键id
    private String t_sys_userid;//用户的id
    private String t_sys_contacts_name;//联系人姓名
    private String t_sys_contacts_id;//联系人id
    private String t_sys_userphone;//用户手机号
    private String t_sys_usericonpath;//用户头像地址
    private String t_sys_contactsconpath;//联系人头像地址
    private String t_sys_modify_time;//最后修改时间
    private String t_sys_modify_id;//最后用户id

    @Generated(hash = 10711293)
    public TSYSCONTANTS(String t_sys_id, String t_sys_userid, String t_sys_contacts_name, String t_sys_contacts_id, String t_sys_userphone, String t_sys_usericonpath, String t_sys_contactsconpath, String t_sys_modify_time, String t_sys_modify_id) {
        this.t_sys_id = t_sys_id;
        this.t_sys_userid = t_sys_userid;
        this.t_sys_contacts_name = t_sys_contacts_name;
        this.t_sys_contacts_id = t_sys_contacts_id;
        this.t_sys_userphone = t_sys_userphone;
        this.t_sys_usericonpath = t_sys_usericonpath;
        this.t_sys_contactsconpath = t_sys_contactsconpath;
        this.t_sys_modify_time = t_sys_modify_time;
        this.t_sys_modify_id = t_sys_modify_id;
    }
    @Generated(hash = 674189073)
    public TSYSCONTANTS() {
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

    public String getT_sys_contacts_name() {
        return t_sys_contacts_name;
    }

    public void setT_sys_contacts_name(String t_sys_contacts_name) {
        this.t_sys_contacts_name = t_sys_contacts_name;
    }

    public String getT_sys_contacts_id() {
        return t_sys_contacts_id;
    }

    public void setT_sys_contacts_id(String t_sys_contacts_id) {
        this.t_sys_contacts_id = t_sys_contacts_id;
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

    public String getT_sys_contactsconpath() {
        return t_sys_contactsconpath;
    }

    public void setT_sys_contactsconpath(String t_sys_contactsconpath) {
        this.t_sys_contactsconpath = t_sys_contactsconpath;
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

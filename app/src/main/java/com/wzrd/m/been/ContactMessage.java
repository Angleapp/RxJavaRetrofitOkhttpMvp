package com.wzrd.m.been;

/**
 * Created by lk on 2018/2/9.
 */

public class ContactMessage {
    private String username;
    private String userid;
    private String messge;
    private String motifitytime;
    private String isupte;
    private String isbox;

    public ContactMessage() {
    }

    public ContactMessage(String username, String userid, String messge, String motifitytime, String isupte, String isbox) {
        this.username = username;
        this.userid = userid;
        this.messge = messge;
        this.motifitytime = motifitytime;
        this.isupte = isupte;
        this.isbox = isbox;
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
}

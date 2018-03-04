package com.wzrd.m.been;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by hyj on 2018/3/3.
 */
@Entity
public class VideoContent {
    @Id
    private String id;
    private String videoId;
    private String time;//时间点
    private String textId;//文本样式的id
    private String picPath;//裁剪视频的路径
    private String iconPath;//表情包路径
    private String text;//文本内容

    @Keep
    public VideoContent(String id, String videoId, String time, String textId, String picPath, String iconPath,String text) {
        this.id = id;
        this.videoId = videoId;
        this.time = time;
        this.textId = textId;
        this.picPath = picPath;
        this.iconPath = iconPath;
        this.text = text;
    }

    @Generated(hash = 1323019698)
    public VideoContent() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
}

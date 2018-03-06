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
    private int lineId;//分隔线id
    private String picPath;//裁剪视频的路径
    private int iconId;//表情包路径
    private String text;//文本内容

    @Keep
    public VideoContent(String id, String videoId, String time, int lineId, String picPath, int iconId,String text) {
        this.id = id;
        this.videoId = videoId;
        this.time = time;
        this.lineId = lineId;
        this.picPath = picPath;
        this.iconId = iconId;
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

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
}

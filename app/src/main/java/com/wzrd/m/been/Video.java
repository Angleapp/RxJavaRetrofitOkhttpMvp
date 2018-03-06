package com.wzrd.m.been;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by hyj on 2018/2/26.
 */
@Entity
public class Video {
    @Id(autoincrement = false)
    private String id;
    private String video_type;//0 表达爱 1 道歉  2 加深爱  3 自定义
    private String video_path;//视频路径
    private String face_pic_path;//视频封面图片路径
    private int isEdit = 0;//0 未编辑 1 编辑过
    private String title;

    @Keep
    public Video(String id, String video_type, String video_path, String face_pic_path, String title,int isEdit) {
        this.id = id;
        this.video_type = video_type;
        this.video_path = video_path;
        this.face_pic_path = face_pic_path;
        this.title = title;
        this.isEdit = isEdit;
    }

    @Generated(hash = 237528154)
    public Video() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideo_type() {
        return video_type;
    }

    public void setVideo_type(String video_type) {
        this.video_type = video_type;
    }

    public String getVideo_path() {
        return video_path;
    }

    public void setVideo_path(String video_path) {
        this.video_path = video_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFace_pic_path() {
        return face_pic_path;
    }

    public void setFace_pic_path(String face_pic_path) {
        this.face_pic_path = face_pic_path;
    }

    public int getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(int isEdit) {
        this.isEdit = isEdit;
    }
}

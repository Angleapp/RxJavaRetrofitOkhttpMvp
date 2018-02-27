package com.wzrd.m.been;

/**
 * Created by hyj on 2018/2/26.
 */

public class Video {
    String id;
    String videoPic;
    String path;
    String title;

    public Video(String id, String videoPic, String path, String title) {
        this.id = id;
        this.videoPic = videoPic;
        this.path = path;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoPic() {
        return videoPic;
    }

    public void setVideoPic(String videoPic) {
        this.videoPic = videoPic;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

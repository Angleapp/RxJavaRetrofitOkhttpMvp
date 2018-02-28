package com.wzrd.m.been;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by hyj on 2018/2/14.
 */
@Entity
public class Poem {
    @Id
    String id;
    String title;
    String author;
    String content;
    String isHasVideo;
    String path;
    int bgPath;

    @Generated(hash = 32421040)
    public Poem(String id, String title, String author, String content, String isHasVideo, String path, int bgPath) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.isHasVideo = isHasVideo;
        this.path = path;
        this.bgPath = bgPath;
    }

    @Generated(hash = 1852989059)
    public Poem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsHasVideo() {
        return isHasVideo;
    }

    public void setIsHasVideo(String isHasVideo) {
        this.isHasVideo = isHasVideo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getBgPath() {
        return bgPath;
    }

    public void setBgPath(int bgPath) {
        this.bgPath = bgPath;
    }
}

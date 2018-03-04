package com.wzrd.m.been;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by hyj on 2018/3/4.
 */
@Entity
public class EmoticonCode {
    @Id
    private String id;
    private String path;

    @Keep
    public EmoticonCode(String id, String path) {
        this.id = id;
        this.path = path;
    }

    @Generated(hash = 706292328)
    public EmoticonCode() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

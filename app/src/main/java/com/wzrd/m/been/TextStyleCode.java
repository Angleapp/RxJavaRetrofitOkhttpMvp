package com.wzrd.m.been;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by hyj on 2018/3/3.
 */
@Entity
public class TextStyleCode {
    @Id
    private String id;//文本样式id
    private String textColor;
    private int textBackground;
    private int textSize;
    private String text;

    @Keep
    public TextStyleCode(String id, String textColor, int textBackground, int textSize, String text) {
        this.id = id;
        this.textColor = textColor;
        this.textBackground = textBackground;
        this.textSize = textSize;
        this.text = text;
    }
    @Generated(hash = 480553914)
    public TextStyleCode() {
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public int getTextBackground() {
        return textBackground;
    }

    public void setTextBackground(int textBackground) {
        this.textBackground = textBackground;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

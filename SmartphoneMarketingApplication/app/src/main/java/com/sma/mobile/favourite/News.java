package com.sma.mobile.favourite;

/**
 * Created by longtran on 17/09/2017.
 */

import java.io.Serializable;

public class News implements Serializable {

    private String title;
    private String desc;
    private String photoUrl;

    public News(String title, String desc, String photoUrl) {
        super();
        this.title = title;
        this.desc = desc;
        this.photoUrl = photoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
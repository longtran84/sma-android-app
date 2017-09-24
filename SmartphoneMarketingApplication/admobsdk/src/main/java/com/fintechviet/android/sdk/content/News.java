package com.fintechviet.android.sdk.content;

import java.util.Date;

/**
 * Created by tungn on 9/14/2017.
 */
public class News {
    private long id;
    private String title;
    private String shortDescription;
    private String imageLink;
    private String link;
    private String newsCategoryCode;
    private long createdDate;
    private Date createdDateVal;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNewsCategoryCode() {
        return newsCategoryCode;
    }

    public void setNewsCategoryCode(String newsCategoryCode) {
        this.newsCategoryCode = newsCategoryCode;
    }

    public Date getCreatedDateVal() {
        createdDateVal = new Date(createdDate);
        return createdDateVal;
    }

    public void setCreatedDateVal(Date createdDateVal) {
        this.createdDateVal = createdDateVal;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

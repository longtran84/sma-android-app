package com.fintechviet.android.sdk.model;

import com.google.gson.annotations.SerializedName;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.io.Serializable;

/**
 * Created by longtran on 23/09/2017.
 */

public class Favourite extends RealmObject implements Serializable {

    @PrimaryKey
    @SerializedName("code")
    private String code;

    @SerializedName("id")
    private int favouriteID;

    @SerializedName("author")
    private String author;

    @SerializedName("name")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("url")
    private String url;

    @SerializedName("imageFile")
    private String urlToImage;

    @SerializedName("publishedAt")
    private String publishedAt;

    private boolean interest;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFavouriteID() {
        return favouriteID;
    }

    public void setFavouriteID(int favouriteID) {
        this.favouriteID = favouriteID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public boolean isInterest() {
        return interest;
    }

    public void setInterest(boolean interest) {
        this.interest = interest;
    }
}

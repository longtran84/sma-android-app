package com.fintechviet.android.sdk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by longtran on 24/09/2017.
 */

public class ArticlesItem implements Serializable {

    @SerializedName("id")
    private long articleId;

    @SerializedName("createdDate")
    private String createdDate;

    @SerializedName("author")
    private String author;

    @SerializedName("imageLink")
    private String urlToImage;

    @SerializedName("shortDescription")
    private String description;

    @SerializedName("title")
    private String title;

    @SerializedName("link")
    private String source;

    @SerializedName("newsCategoryCode")
    private String newsCategoryCode;

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNewsCategoryCode() {
        return newsCategoryCode;
    }

    public void setNewsCategoryCode(String newsCategoryCode) {
        this.newsCategoryCode = newsCategoryCode;
    }
}

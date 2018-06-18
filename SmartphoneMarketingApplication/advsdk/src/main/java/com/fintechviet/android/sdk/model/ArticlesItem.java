package com.fintechviet.android.sdk.model;

import com.fintechviet.android.sdk.ad.DecisionResponse;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by longtran on 24/09/2017.
 */

public class ArticlesItem implements Serializable {

    @SerializedName("id")
    private String articleId;

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

    @SerializedName("decisionResponse")
    private DecisionResponse decisionResponse;

    @SerializedName("publishDate")
    private String publishDate;

    @SerializedName("rewardPoint")
    private int rewardPoint;

    @SerializedName("type")
    private String type;

    private String earning;

    public String getEarning() {
        return earning;
    }

    public void setEarning(String earning) {
        this.earning = earning;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
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

    public DecisionResponse getDecisionResponse() {
        return decisionResponse;
    }

    public void setDecisionResponse(DecisionResponse decisionResponse) {
        this.decisionResponse = decisionResponse;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public int getRewardPoint() {
        return rewardPoint;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRewardPoint(int rewardPoint) {
        this.rewardPoint = rewardPoint;
    }


}

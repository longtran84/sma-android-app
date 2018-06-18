package com.fintechviet.android.sdk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by longtran on 24/09/2017.
 */

public class ArticlesResponse implements Serializable {

    @SerializedName("newsList")
    private List<ArticlesItem> articles;

    public List<ArticlesItem> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesItem> articles) {
        this.articles = articles;
    }
}

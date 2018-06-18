package com.fintechviet.android.sdk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by longtran on 07/04/2018.
 */

public class ListNewsByCategory implements Serializable {

    @SerializedName("newsList")
    private List<ArticlesItem> listArticlesItem;

    public List<ArticlesItem> getListArticlesItem() {
        return listArticlesItem;
    }

    public void setListArticlesItem(List<ArticlesItem> listArticlesItem) {
        this.listArticlesItem = listArticlesItem;
    }
}

package com.fintechviet.android.sdk.content;

import java.util.List;

/**
 * Created by tungn on 9/14/2017.
 */
public class NewsCategory {
    private String code;
    private String name;
    List<News> newsList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}

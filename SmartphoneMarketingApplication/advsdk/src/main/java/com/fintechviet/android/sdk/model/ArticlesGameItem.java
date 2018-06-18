package com.fintechviet.android.sdk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by longtran on 24/09/2017.
 */

public class ArticlesGameItem implements Serializable {

    @SerializedName("image")
    private String urlToImage;

    @SerializedName("name")
    private String name;

    @SerializedName("link")
    private String link;

    public String getObjectGame() {
        return objectGame;
    }

    public void setObjectGame(String objectGame) {
        this.objectGame = objectGame;
    }

    private String objectGame;



    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

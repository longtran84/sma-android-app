package com.fintechviet.android.sdk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by QLong on 10/31/2017.
 */

public class TopAds implements Serializable {

    @SerializedName("adId")
    private Integer adId;
    @SerializedName("clickUrl")
    private String clickUrl;
    @SerializedName("trackingUrl")
    private String trackingUrl;
    @SerializedName("viewUrl")
    private Object viewUrl;
    @SerializedName("impressionUrl")
    private String impressionUrl;
    @SerializedName("content")
    private Content content;

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public String getTrackingUrl() {
        return trackingUrl;
    }

    public void setTrackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }

    public Object getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(Object viewUrl) {
        this.viewUrl = viewUrl;
    }

    public String getImpressionUrl() {
        return impressionUrl;
    }

    public void setImpressionUrl(String impressionUrl) {
        this.impressionUrl = impressionUrl;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public class Content {

        @SerializedName("title")
        private String title;
        @SerializedName("template")
        private String template;
        @SerializedName("body")
        private String body;
        @SerializedName("imageUrl")
        private String imageUrl;
        @SerializedName("videoUrl")
        private String videoUrl;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }
    }
}

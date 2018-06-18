package com.fintechviet.android.sdk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by longtran on 04/04/2018.
 */

public class LatitudeLongitude implements Serializable {

    @SerializedName("icon")
    private String icon;

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String vicinity;

    @SerializedName("title")
    private String title;

    @SerializedName("snippet")
    private String snippet;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("reference")
    private String reference;

    @SerializedName("distance")
    private String distance;

//    @SerializedName("distance")
//    private LatitudeLongitudeGeometry latitudeLongitudeGeometry;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

//    public LatitudeLongitudeGeometry getLatitudeLongitudeGeometry() {
//        return latitudeLongitudeGeometry;
//    }
//
//    public void setLatitudeLongitudeGeometry(LatitudeLongitudeGeometry latitudeLongitudeGeometry) {
//        this.latitudeLongitudeGeometry = latitudeLongitudeGeometry;
//    }


    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}

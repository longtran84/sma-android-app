package com.sma.mobile.location.beans;

import java.io.Serializable;

/**
 * Created by longtran on 04/04/2018.
 */

public class LatLngBean implements Serializable {

    private String title;
    private String snippet;
    private double latitude;
    private double longitude;
    private boolean mBoolean;
    private String reference;
    private int position;
    private String distance;
    private int type;

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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public boolean ismBoolean() {
        return mBoolean;
    }

    public void setmBoolean(boolean mBoolean) {
        this.mBoolean = mBoolean;
    }

    public int getPosition() {
        return position;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

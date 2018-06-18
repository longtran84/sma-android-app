package com.fintechviet.android.sdk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by longtran on 05/04/2018.
 */

public class LatitudeLongitudeGeometry implements Serializable {

    @SerializedName("location")
    private LocationBean locationBean;

    public LocationBean getLocationBean() {
        return locationBean;
    }

    public void setLocationBean(LocationBean locationBean) {
        this.locationBean = locationBean;
    }
}

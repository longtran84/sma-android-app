package com.sma.mobile.location.beans;

import com.fintechviet.android.sdk.model.LatitudeLongitude;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by longtran on 12/04/2018.
 */

public class JMarkerOptions {

    private MarkerOptions markerOptions;
    private LatitudeLongitude latitudeLongitude;

    public MarkerOptions getMarkerOptions() {
        return markerOptions;
    }

    public void setMarkerOptions(MarkerOptions markerOptions) {
        this.markerOptions = markerOptions;
    }

    public LatitudeLongitude getLatitudeLongitude() {
        return latitudeLongitude;
    }

    public void setLatitudeLongitude(LatitudeLongitude latitudeLongitude) {
        this.latitudeLongitude = latitudeLongitude;
    }
}

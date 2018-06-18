package com.sma.mobile.notification;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by longtran on 10/04/2018.
 */

public class FirebaseMessagingResponse implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FirebaseMessagingResponse createFromParcel(Parcel in) {
            return new FirebaseMessagingResponse(in);
        }

        public FirebaseMessagingResponse[] newArray(int size) {
            return new FirebaseMessagingResponse[size];
        }
    };

    private String id;
    private double latitude;
    private double longitude;
    private String impressionUrl;
    private String clickUrl;
    private String trackingUrl;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImpressionUrl() {
        return impressionUrl;
    }

    public void setImpressionUrl(String impressionUrl) {
        this.impressionUrl = impressionUrl;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTrackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }



    public FirebaseMessagingResponse() {

    }

    // Parcelling part
    public FirebaseMessagingResponse(Parcel in) {
        this.id = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.impressionUrl = in.readString();
        this.clickUrl = in.readString();
        this.trackingUrl = in.readString();
        this.type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.impressionUrl);
        dest.writeString(this.clickUrl);
        dest.writeString(this.trackingUrl);
        dest.writeString(this.type);
    }
}

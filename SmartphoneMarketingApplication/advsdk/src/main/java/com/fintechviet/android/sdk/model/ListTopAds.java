package com.fintechviet.android.sdk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by QLong on 10/31/2017.
 */

public class ListTopAds implements Serializable {

    @SerializedName("decision")
    private TopAds decision;

    public TopAds getDecision() {
        return decision;
    }

    public void setDecision(TopAds decision) {
        this.decision = decision;
    }
}

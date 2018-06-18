package com.fintechviet.android.sdk.ad;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tungn on 9/7/2017.
 */
public class DecisionResponse implements Serializable {

    @SerializedName("decision")
    private Decision decision;

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }
}

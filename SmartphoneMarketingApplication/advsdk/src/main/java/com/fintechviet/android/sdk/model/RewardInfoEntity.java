package com.fintechviet.android.sdk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by longtran on 05/10/2017.
 */

public class RewardInfoEntity implements Serializable {

    @SerializedName("rewardCode")
    private String rewardCode;

    @SerializedName("rewardName")
    private String rewardName;

    @SerializedName("amount")
    private String amount;

    public String getRewardCode() {
        return rewardCode;
    }

    public void setRewardCode(String rewardCode) {
        this.rewardCode = rewardCode;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}

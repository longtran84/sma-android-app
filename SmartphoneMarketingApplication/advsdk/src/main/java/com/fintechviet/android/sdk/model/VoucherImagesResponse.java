package com.fintechviet.android.sdk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by QLong on 12/7/2017.
 */

public class VoucherImagesResponse implements Serializable {

    @SerializedName("id")
    private long id;

    @SerializedName("voucherId")
    private long voucherId;

    @SerializedName("image")
    private String image;

    @SerializedName("order")
    private int order;

    @SerializedName("status")
    private String status;

    @SerializedName("createdDate")
    private long createdDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(long voucherId) {
        this.voucherId = voucherId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }
}

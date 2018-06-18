package com.fintechviet.android.sdk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by QLong on 11/16/2017.
 */

public class GameCardResponse implements Serializable {

    @SerializedName("id")
    private long voucherId;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @SerializedName("image")
    private String picture;

    @SerializedName("description")
    private String description;

    @SerializedName("marketPrice")
    private BigDecimal marketPrice;

    @SerializedName("price")
    private BigDecimal price;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("pointExchange")
    private BigDecimal pointExchange;

    @SerializedName("pointExchangeText")
    private String pointExchangeText;

    @SerializedName("status")
    private String status;

    @SerializedName("createdDate")
    private long createdDate;

    private int quantityOrdered;

    public long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(long voucherId) {
        this.voucherId = voucherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public BigDecimal getPointExchange() {
        return pointExchange;
    }

    public String getPointExchangeText() {
        return pointExchangeText;
    }

    public void setPointExchangeText(String pointExchangeText) {
        this.pointExchangeText = pointExchangeText;
    }

    public void setPointExchange(BigDecimal pointExchange) {
        this.pointExchange = pointExchange;
    }


}

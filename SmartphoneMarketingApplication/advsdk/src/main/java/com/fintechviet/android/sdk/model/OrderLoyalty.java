package com.fintechviet.android.sdk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by QLong on 12/8/2017.
 */

public class OrderLoyalty implements Serializable {

    @SerializedName("id")
    private long orderId;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("price")
    private BigDecimal price;
    @SerializedName("total")
    private BigDecimal total;
    @SerializedName("totalPoint")
    private BigDecimal totalPoint;
    @SerializedName("customerName")
    private String customerName;
    @SerializedName("address")
    private String address;
    @SerializedName("phone")
    private String phone;
    @SerializedName("email")
    private String email;
    @SerializedName("status")
    private String status;
    @SerializedName("createdDate")
    private long createdDate;
    @SerializedName("voucher")
    private VoucherResponse voucherResponse;
    @SerializedName("phoneCard")
    private PhoneCardResponse phoneCardResponse;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(BigDecimal totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public VoucherResponse getVoucherResponse() {
        return voucherResponse;
    }

    public void setVoucherResponse(VoucherResponse voucherResponse) {
        this.voucherResponse = voucherResponse;
    }

    public PhoneCardResponse getPhoneCardResponse() {
        return phoneCardResponse;
    }

    public void setPhoneCardResponse(PhoneCardResponse phoneCardResponse) {
        this.phoneCardResponse = phoneCardResponse;
    }
}

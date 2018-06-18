package com.sma.mobile.cashout.adapters;

import java.io.Serializable;

/**
 * Created by longtran on 10/12/2017.
 */

public enum OrderStatus implements Serializable {

    NEW("NEW", "Mới"), PROCESSING("PROCESSING", "Đang xử lý"), CANCELLED("CANCELLED", "Đã hủy"), SUCCESSFUL("SUCCESSFUL", "Thành công"), CLOSE("CLOSE", "Đóng");

    private String value;
    private String key;

    OrderStatus(String key, String value) {
        this.value = value;
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static String valueOfOrderStatus(String value) {
        if (value.equalsIgnoreCase(NEW.key))
            return NEW.value;
        else if (value.equalsIgnoreCase(PROCESSING.key))
            return PROCESSING.value;
        else if (value.equalsIgnoreCase(CANCELLED.key))
            return CANCELLED.value;
        else if (value.equalsIgnoreCase(SUCCESSFUL.key))
            return SUCCESSFUL.value;
        else if (value.equalsIgnoreCase(CLOSE.key))
            return CLOSE.value;
        else
            return null;
    }

}

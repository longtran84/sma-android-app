package com.fintechviet.android.sdk.model;

import java.io.Serializable;

/**
 * Created by longtran on 03/10/2017.
 */

public class MessageResponse implements Serializable {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

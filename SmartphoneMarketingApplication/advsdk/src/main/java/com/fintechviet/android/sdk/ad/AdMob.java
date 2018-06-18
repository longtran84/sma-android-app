package com.fintechviet.android.sdk.ad;

import java.io.Serializable;

/**
 * Created by longtran on 01/10/2017.
 */

public class AdMob implements Serializable {

    private int id;
    private String name;
    private String icon;
    private String installLink;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getInstallLink() {
        return installLink;
    }

    public void setInstallLink(String installLink) {
        this.installLink = installLink;
    }
}

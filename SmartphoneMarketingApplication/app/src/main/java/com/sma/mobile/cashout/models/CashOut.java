package com.sma.mobile.cashout.models;

import java.io.Serializable;

/**
 * Created by QLong on 10/4/2017.
 */

public class CashOut implements Serializable {

    private int id;
    private String name;
    private String icon;
    private String installLink;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String desc;



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

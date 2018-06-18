package com.fintechviet.android.sdk.user;

import java.io.Serializable;

/**
 * Created by tungn on 9/17/2017.
 */
public class User implements Serializable {

    private String email;
    private String gender;
    private int dob;
    private String location;
    private String earning;
    private String inviteCode;
    private String inviteCodeUsed;

    public User(String email, String gender, int dob, String location, String earning) {
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.location = location;
        this.earning = earning;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getDob() {
        return dob;
    }

    public void setDob(int dob) {
        this.dob = dob;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEarning() {
        return earning;
    }

    public void setEarning(String earning) {
        this.earning = earning;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public String getInviteCodeUsed() {
        return inviteCodeUsed;
    }

    public void setInviteCodeUsed(String inviteCodeUsed) {
        this.inviteCodeUsed = inviteCodeUsed;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }


}

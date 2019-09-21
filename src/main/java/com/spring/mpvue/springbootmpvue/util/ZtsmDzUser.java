package com.spring.mpvue.springbootmpvue.util;

import java.util.Date;

public class ZtsmDzUser {
    private String Address;
    private String UserName;
    private String ztsmName;
    private Date tjsjDatetime;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getZtsmName() {
        return ztsmName;
    }

    public void setZtsmName(String ztsmName) {
        this.ztsmName = ztsmName;
    }

    public Date getTjsjDatetime() {
        return tjsjDatetime;
    }

    public void setTjsjDatetime(Date tjsjDatetime) {
        this.tjsjDatetime = tjsjDatetime;
    }
}

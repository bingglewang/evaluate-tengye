package com.spring.mpvue.springbootmpvue.mybatis.in;

import com.spring.mpvue.springbootmpvue.mybatis.po.Admin;

public class AdminIn extends Admin {
    private String token;
        private String newPwd;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}

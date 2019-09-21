package com.spring.mpvue.springbootmpvue.mybatis.in;

import com.spring.mpvue.springbootmpvue.mybatis.po.User;

import java.util.Date;

public class UserIn extends User {
    //条件搜索
    private String startRegisterDate;
    private String endRegisterDate;
    //当前页
    private Integer currentPage;
    //页面大小
    private Integer pageSize;



    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getStartRegisterDate() {
        return startRegisterDate;
    }

    public void setStartRegisterDate(String startRegisterDate) {
        this.startRegisterDate = startRegisterDate;
    }

    public String getEndRegisterDate() {
        return endRegisterDate;
    }

    public void setEndRegisterDate(String endRegisterDate) {
        this.endRegisterDate = endRegisterDate;
    }
}

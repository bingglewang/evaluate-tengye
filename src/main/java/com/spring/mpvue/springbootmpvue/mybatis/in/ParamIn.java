package com.spring.mpvue.springbootmpvue.mybatis.in;

import com.spring.mpvue.springbootmpvue.mybatis.po.Paramconfig;

public class ParamIn extends Paramconfig {
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
}

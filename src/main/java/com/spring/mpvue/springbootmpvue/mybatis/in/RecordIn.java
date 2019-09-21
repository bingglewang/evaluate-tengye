package com.spring.mpvue.springbootmpvue.mybatis.in;

import com.spring.mpvue.springbootmpvue.mybatis.po.Record;

public class RecordIn extends Record {
    //当前页
    private Integer currentPage;
    //地址或楼盘名
    private String address;
    private String openId;
    //页面大小
    private Integer pageSize;
    //条件搜索
    private String startQueryDate;
    private String endQueryDate;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getOpenId() {
        return openId;
    }

    @Override
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getStartQueryDate() {
        return startQueryDate;
    }

    public void setStartQueryDate(String startQueryDate) {
        this.startQueryDate = startQueryDate;
    }

    public String getEndQueryDate() {
        return endQueryDate;
    }

    public void setEndQueryDate(String endQueryDate) {
        this.endQueryDate = endQueryDate;
    }
}

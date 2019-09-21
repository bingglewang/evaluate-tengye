package com.spring.mpvue.springbootmpvue.mybatis.service;

import com.spring.mpvue.springbootmpvue.mybatis.in.RecordIn;
import com.spring.mpvue.springbootmpvue.mybatis.po.Record;
import com.spring.mpvue.springbootmpvue.util.PageBean;

public interface RecordService {
    //分页查询估价记录
    public PageBean<Record> getRecordByPages(Integer currentPage, RecordIn recordIn, Integer pageSize);
}

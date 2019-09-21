package com.spring.mpvue.springbootmpvue.mybatis.service;

import com.spring.mpvue.springbootmpvue.mybatis.in.ParamIn;
import com.spring.mpvue.springbootmpvue.mybatis.po.Paramconfig;
import com.spring.mpvue.springbootmpvue.util.PageBean;

import java.util.Map;

public interface ParamService {
    //分页查询估价记录
    public PageBean<Paramconfig> getParmaByPages(Integer currentPage, ParamIn paramIn, Integer pageSize);
    //根据id修改参数表
    public Map<String,Object> updateParamById(ParamIn paramIn);
}

package com.spring.mpvue.springbootmpvue.mybatis.service;

import com.spring.mpvue.springbootmpvue.mybatis.in.UserIn;
import com.spring.mpvue.springbootmpvue.mybatis.po.User;
import com.spring.mpvue.springbootmpvue.util.PageBean;

public interface UserService {
    //分页查询用户信息
    public PageBean<User> getUserLikeByPages(Integer currentPage, UserIn userIn, Integer pageSize);
}

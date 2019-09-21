package com.spring.mpvue.springbootmpvue.mybatis.service;

import com.spring.mpvue.springbootmpvue.util.Entrust;

public interface WebService {
    Object getZtsmList(String Keyword);
    int addZtsm( Entrust entrust);
}

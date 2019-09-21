package com.spring.mpvue.springbootmpvue.mybatis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.spring.mpvue.springbootmpvue.mybatis.service.WebService;
import com.spring.mpvue.springbootmpvue.util.Entrust;
import com.spring.mpvue.springbootmpvue.util.WebUtil;
import com.spring.mpvue.springbootmpvue.util.ZtsmDzUser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebServiceImpl implements WebService {
    @Override
    public Object getZtsmList(String Keyword) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("keyParam",Keyword);
        return WebUtil.parseXML(WebUtil.creatPostAndTransData(dataMap));

    }

    @Override
    public int addZtsm(Entrust entrust) {
        String jsonStr =  JSONObject.toJSONString(entrust);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("json_data", jsonStr);
        int resultCode =  WebUtil.parseXML1(WebUtil.creatPostAndTransData1(dataMap));
        return resultCode;
    }
}

package com.spring.mpvue.springbootmpvue.controller;

import com.spring.mpvue.springbootmpvue.mybatis.in.KeyWord;
import com.spring.mpvue.springbootmpvue.mybatis.mapper.UserMapper;
import com.spring.mpvue.springbootmpvue.mybatis.po.User;
import com.spring.mpvue.springbootmpvue.mybatis.po.UserExample;
import com.spring.mpvue.springbootmpvue.mybatis.service.WebService;
import com.spring.mpvue.springbootmpvue.util.Entrust;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("webService")
public class WebServiceController {

    @Autowired
    private WebService webService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("addEntrust")
    public Map<String,Object> addEntrust(@RequestBody Entrust entrust){
        Map<String,Object> result = new HashMap<>();
        entrust.setWxCompany("typg");
        //根据openid查询用户
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andOpenIdEqualTo( entrust.getToUserName());
        List<User> userList = userMapper.selectByExample(userExample);
        if(userList.size() > 0){
            entrust.setUserName(userList.get(0).getUserName());
            entrust.setPhone(userList.get(0).getPhoneNumber());
            entrust.setMail(userList.get(0).getUserMail());
        }
        entrust.setFromUserName("wx5a36af365642441e");


        int i =  webService.addZtsm(entrust);
       if(i > 0){
           result.put("code",200);
           result.put("message","添加成功");
           result.put("data",null);
       }else{
           result.put("code",2003);
           result.put("message","添加失败");
           result.put("data",null);
       }
        return result;
    }

    @PostMapping("/getZtsmList")
    @ResponseBody
    public Object getZtsmList(@RequestBody final KeyWord keyword){
        Map<String,Object> result = new HashMap<>();
        result.put("code",200);
        result.put("message","查询成功");
        result.put("data",webService.getZtsmList(keyword.getKeyword()));
        return result;
    }
}

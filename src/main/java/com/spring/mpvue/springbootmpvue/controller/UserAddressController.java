package com.spring.mpvue.springbootmpvue.controller;

import com.spring.mpvue.springbootmpvue.mybatis.po.UserAddress;
import com.spring.mpvue.springbootmpvue.mybatis.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("address")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;

    @PostMapping("add")
    public Object add(@RequestBody final UserAddress userAddress){
        Map<String,Object> result = new HashMap<>();
        int i = userAddressService.add(userAddress);
        if(i > 0){
            result.put("code",200);
            result.put("message","添加成功");
            result.put("data",null);
        }else{
            result.put("code",211);
            result.put("message","添加失败");
            result.put("data",null);
        }
        return result;
    }
}

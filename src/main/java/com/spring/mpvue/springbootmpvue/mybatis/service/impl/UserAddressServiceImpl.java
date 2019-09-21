package com.spring.mpvue.springbootmpvue.mybatis.service.impl;

import com.spring.mpvue.springbootmpvue.mybatis.mapper.UserAddressMapper;
import com.spring.mpvue.springbootmpvue.mybatis.po.UserAddress;
import com.spring.mpvue.springbootmpvue.mybatis.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAddressServiceImpl implements UserAddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public int add(UserAddress userAddress) {
        return userAddressMapper.insert(userAddress);
    }
}

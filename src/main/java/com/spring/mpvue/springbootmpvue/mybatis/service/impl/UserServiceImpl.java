package com.spring.mpvue.springbootmpvue.mybatis.service.impl;

import com.github.pagehelper.PageHelper;
import com.spring.mpvue.springbootmpvue.mybatis.in.UserIn;
import com.spring.mpvue.springbootmpvue.mybatis.mapper.UserMapper;
import com.spring.mpvue.springbootmpvue.mybatis.po.User;
import com.spring.mpvue.springbootmpvue.mybatis.po.UserExample;
import com.spring.mpvue.springbootmpvue.mybatis.service.UserService;
import com.spring.mpvue.springbootmpvue.util.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageBean<User> getUserLikeByPages(Integer currentPage, UserIn user, Integer pageSize) {
        //设置分页信息，分别是当前页和页面大小 ，（必须在mapper接口中的方法执行之前设置该分页信息）
        PageHelper.startPage(currentPage,pageSize);
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        //姓名
        if(StringUtils.isNotBlank(user.getUserName())){
            criteria.andUserNameLike("%"+user.getUserName()+"%");
        }
        //昵称
        if(StringUtils.isNotBlank(user.getNickName())){
            criteria.andNickNameLike("%"+user.getNickName()+"%");
        }
        //电话
        if(StringUtils.isNotBlank(user.getPhoneNumber())){
            criteria.andPhoneNumberLike("%"+user.getPhoneNumber()+"%");
        }
        //邮箱
        if(StringUtils.isNotBlank(user.getUserMail())){
            criteria.andUserMailLike("%"+user.getUserMail()+"%");
        }
        //性别
        if(user.getSex() != null){
            if(user.getSex() == 0){
                List sexs = new ArrayList();
                sexs.add(user.getSex());
                sexs.add(2);
                criteria.andSexIn(sexs);
            }else{
                criteria.andSexEqualTo(user.getSex());
            }
        }
        //可查次数
        if(user.getEnableCount() != null){
            criteria.andEnableCountEqualTo(user.getEnableCount());
        }
        //查询时段
        if(user.getStartEnableTime() != null  && user.getEndEnableTime() != null){
            criteria.andStartEnableTimeLessThanOrEqualTo(user.getStartEnableTime());
            criteria.andEndEnableTimeGreaterThanOrEqualTo(user.getEndEnableTime());
        }
        //注册时间
        if(StringUtils.isNotBlank(user.getStartRegisterDate()) && StringUtils.isNotBlank(user.getEndRegisterDate())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = new Date();
            Date end = new Date();
            try{
                start =  sdf.parse(user.getStartRegisterDate());
                end = sdf.parse(user.getEndRegisterDate());
            }catch (Exception e){
                e.printStackTrace();
            }
            criteria.andRegisterDateGreaterThanOrEqualTo(start);
            criteria.andRegisterDateLessThanOrEqualTo(end);
        }
       /* criteria.andDelFlagEqualTo("N");*/
        //注册时间倒叙
        userExample.setOrderByClause("register_date desc");
        //数据去重
        userExample.setDistinct(false);
        //获取所有的记录
        List<User> userList = userMapper.selectByExample(userExample);
        for(User item : userList){
            try {
                item.setNickName(URLDecoder.decode(item.getNickName(), "utf-8"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //获取记录的总数目
        int userCount = userMapper.countByExample(userExample);
        PageBean<User> result = new PageBean<User>(currentPage,pageSize,userCount);
        result.setItems(userList);
        return result;
    }
}

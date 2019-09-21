package com.spring.mpvue.springbootmpvue.controller;

import com.spring.mpvue.springbootmpvue.mybatis.in.AdminIn;
import com.spring.mpvue.springbootmpvue.mybatis.in.UserIn;
import com.spring.mpvue.springbootmpvue.mybatis.mapper.AdminMapper;
import com.spring.mpvue.springbootmpvue.mybatis.mapper.UserMapper;
import com.spring.mpvue.springbootmpvue.mybatis.po.Admin;
import com.spring.mpvue.springbootmpvue.mybatis.po.AdminExample;
import com.spring.mpvue.springbootmpvue.mybatis.po.User;
import com.spring.mpvue.springbootmpvue.mybatis.po.UserExample;
import com.spring.mpvue.springbootmpvue.mybatis.service.UserService;
import com.spring.mpvue.springbootmpvue.util.*;
import io.swagger.annotations.*;
import javafx.beans.binding.ObjectExpression;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@Api(value = "UserManageController", tags = {"用户后台管理"})
public class UserManageController {
    private static org.slf4j.Logger log = LoggerFactory.getLogger(UserManageController.class);

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    /**
     * 修改管理员密码
     */
    @RequestMapping("/changeAdminPwd")
    @ResponseBody
    @ApiOperation(value = "修改管理员密码", httpMethod = "POST", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "修改管理员密码"),
            @ApiResponse(code = 500, message = "500(系统错误)", response = String.class)
    })
    public Map<String, Object> changeAdminPwd(
            @RequestBody final AdminIn adminIn
    ) {
        Map<String, Object> result = new HashMap<String, Object>();
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        if (StringUtils.isNotBlank(adminIn.getToken())) {
            Map m = JavaWebToken.parserJavaWebToken(adminIn.getToken());
            if (StringUtils.isNotBlank(m.get("password") + "")) {
                String  ordPwd = m.get("password") + "";
                String orlPwd1 = adminIn.getPassword();
                String  newPwd = adminIn.getNewPwd();
                if(!orlPwd1.equals(ordPwd)){
                    result.put("status",-1);
                    result.put("msg","原始密码不对!");
                    result.put("data","");
                    return result;
                }else{
                    Admin updateAdmin = new Admin();
                    updateAdmin.setPassword(newPwd);
                    criteria.andIdEqualTo(Integer.parseInt(m.get("loginId") + ""));
                    int i =  adminMapper.updateByExampleSelective(updateAdmin,adminExample);
                    if(i > 0 ){
                        result.put("status",0);
                        result.put("msg","密码修改成功!");
                        result.put("data","");
                        return result;
                    }else{
                        result.put("status",-4);
                        result.put("msg","密码修改失败!");
                        result.put("data","");
                        return result;
                    }
                }
            }else{
                result.put("status",-2);
                result.put("msg","密码为空!");
                result.put("data","");
                return result;
            }
        }else {
            result.put("status",-3);
            result.put("msg","token为空!");
            result.put("data","");
            return result;
        }
    }

    /**
     * 根据用户id查询用户
     */
    @RequestMapping("/getAdminById")
    @ResponseBody
    @ApiOperation(value = "根据用户id查询用户", httpMethod = "POST", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "根据用户id查询用户"),
            @ApiResponse(code = 500, message = "500(系统错误)", response = String.class)
    })
    public Map<String, Object> getAdminById(
            @RequestBody final AdminIn adminIn
    ) {
        Map<String, Object> result = new HashMap<String, Object>();
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        if (StringUtils.isNotBlank(adminIn.getToken())) {
            Map m = JavaWebToken.parserJavaWebToken(adminIn.getToken());
            if (StringUtils.isNotBlank(m.get("loginId") + "")) {
                criteria.andIdEqualTo(Integer.parseInt(m.get("loginId") + ""));
            }
            if (StringUtils.isNotBlank(m.get("password") + "")) {
                criteria.andPasswordEqualTo(m.get("password") + "");
            }
        }
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        if (adminList.size() > 0 && adminList != null) {
            result.put("status", 0);
            result.put("msg", "用户已经登陆");
        } else {
            result.put("status", -1);
            result.put("msg", "用户需要重新登陆");
        }
        return result;
    }

    /**
     * 用户登录接口
     */
    @RequestMapping("/login")
    @ResponseBody
    @ApiOperation(value = "用户登录", httpMethod = "POST", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取用户登录状态"),
            @ApiResponse(code = 500, message = "500(系统错误)", response = String.class)
    })
    public Map<String, Object> login(
            @RequestBody final Admin admin
    ) {
        log.info("用户名：" + admin.getAccount());
        log.info("密码：" + admin.getPassword());
        Map<String, Object> result = new HashMap<String, Object>();
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        if (StringUtils.isNotBlank(admin.getAccount())) {
            criteria.andAccountEqualTo(admin.getAccount());
        }
        if (StringUtils.isNotBlank(admin.getPassword())) {
            criteria.andPasswordEqualTo(admin.getPassword());
        }

        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        if (adminList.size() > 0 && adminList != null) {
            String token = "";
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("loginId", adminList.get(0).getId());
            m.put("password", adminList.get(0).getPassword());
            try {
                token = JavaWebToken.createJavaWebToken(m);
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.put("data", adminList.get(0));
            result.put("msg", "success");
            result.put("token", token);
            result.put("status", 0);
        } else {
            result.put("data", "");
            result.put("msg", "登录失败，用户名或密码错误!");
            result.put("status", -1);
        }
        return result;
    }

    /**
     * 根据用户openid修改用户信息
     */
    @RequestMapping("/userUpdate")
    @ResponseBody
    @ApiOperation(value = "根据用户openid修改用户信息", httpMethod = "POST", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "根据用户openid修改用户信息"),
            @ApiResponse(code = 500, message = "500(系统错误)", response = String.class)
    })
    public Map<String, Object> updateUserInfo(
            @ApiParam(value = "用户修改参数", required = true)
            @RequestBody final User user
    ) {
        log.info("用户信息：" + user.getOpenId() + ",,,,," + user.getPhoneNumber() + ",,,," + user.getUserName());
        Map<String, Object> result = new HashMap<String, Object>();
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andOpenIdEqualTo(user.getOpenId());
        int i = userMapper.updateByExampleSelective(user, userExample);
        if (i > 0) {
            result.put("status", 0);
            result.put("msg", "success");
        } else {
            result.put("status", -1);
            result.put("msg", "failed");
        }
        return result;
    }

    /**
     * 根据用户id修改用户信息
     */
    @RequestMapping("/updateUserById")
    @ResponseBody
    @ApiOperation(value = "根据用户id修改用户信息", httpMethod = "POST", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "根据用户id修改用户信息"),
            @ApiResponse(code = 500, message = "500(系统错误)", response = String.class)
    })
    public Map<String, Object> updateUserById(
            @ApiParam(value = "用户修改参数", required = true)
            @RequestBody final User user
    ) {
        Map<String, Object> result = new HashMap<String, Object>();
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIdEqualTo(user.getId());
        int i = userMapper.updateByExampleSelective(user, userExample);
        if (i > 0) {
            result.put("status", 0);
            result.put("msg", "success");
        } else {
            result.put("status", -1);
            result.put("msg", "failed");
        }
        return result;
    }

    /**
     * 获取用户信息
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    @ApiOperation(value = "根据用户openid获取用户信息", httpMethod = "POST", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "根据用户openid获取用户信息"),
            @ApiResponse(code = 500, message = "500(系统错误)", response = String.class)
    })
    public Map<String, Object> getUserInfo(
            @ApiParam(value = "用户查询参数", required = true)
            @RequestBody final User user
    ) {
        Map<String, Object> result = new HashMap<String, Object>();
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andOpenIdEqualTo(user.getOpenId());
        criteria.andDelFlagEqualTo("N");
        List<User> userList = userMapper.selectByExample(userExample);
        if (userList != null) {
            result.put("status", 0);
            result.put("msg", "success");
            result.put("data", userList);
        } else {
            result.put("status", -1);
            result.put("msg", "failed");
            result.put("data", "");
        }
        return result;
    }

    /**
     * 判断用户查询权限，是否有查询次数和查询时间段正常
     */
    @RequestMapping("/isUserQueryPower")
    @ResponseBody
    @ApiOperation(value = "判断用户查询权限", httpMethod = "POST", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "判断用户查询权限"),
            @ApiResponse(code = 500, message = "500(系统错误)", response = String.class)
    })
    public Map<String, Object> isUserQueryPower(
            @RequestBody final User user
    ) {
        Map<String, Object> result = new HashMap<String, Object>();
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (StringUtils.isNotBlank(user.getOpenId())) {
            criteria.andOpenIdEqualTo(user.getOpenId());
        }
        log.info("当前日期1：" + DateUtil.getCurrentDateByPattern("yyyy-MM-dd"));
        List<User> userList = userMapper.selectByExample(userExample);
        if (userList != null && userList.size() > 0) {
            if ("Y".equals(userList.get(0).getDelFlag())) {
                result.put("status", -4);
                result.put("msg", "您已被禁用，请联系管理员");
                return result;
            }
            String startDateFormat = DateUtil.format(userList.get(0).getStartEnableTime(), "yyyy-MM-dd");
            String endDateFormat = DateUtil.format(userList.get(0).getEndEnableTime(), "yyyy-MM-dd");
            if (!DateUtil.getCompareDate(startDateFormat) && !DateUtil.getCompareDate(DateUtil.getCurrentDateByPattern("yyyy-MM-dd"), endDateFormat)) {
                //设置是否在查询时间段内标识（是，不修改查询次数）Y
                //设置是否在查询时间段内标识（不是，修改查询次数）N
                User userBack41 = new User();
                userBack41.setBack4("N");
                userMapper.updateByExampleSelective(userBack41,userExample);
                if (userList.get(0).getEnableCount() <= 0) {
                    result.put("status", -1);
                    result.put("msg", "查询次数不足,请联系管理员");
                    return result;
                }else{
                    result.put("status", 0);
                    result.put("msg", "success");
                    return result;
                }
            } else {
                result.put("status", -2);
                result.put("msg", "不在查询时间范围内,请联系管理员");
            }
        } else {
            result.put("status", -3);
            result.put("msg", "用户不存在");
        }
        return result;
    }

    /**
     * 向用户表中插入数据
     */
    @RequestMapping("/insertUser")
    @ResponseBody
    @ApiOperation(value = "向用户表中插入数据", httpMethod = "POST", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "向用户表中插入数据"),
            @ApiResponse(code = 500, message = "500(系统错误)", response = String.class)
    })
    public Map<String, Object> insertUser(
            @RequestBody final User user
    ) {
        Map<String, Object> result = new HashMap<String, Object>();
        //根据openid去用户表里查，查到的话就不插入，没查到就插入
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andOpenIdEqualTo(user.getOpenId());
        List<User> userList = userMapper.selectByExample(userExample);
        if (userList.size() > 0) {
            result.put("status", -1);
            result.put("msg", "该用户已经存在");
        } else {
            //设置用户默认查询次数
            user.setEnableCount(ParamConfig.getDefaultCount().intValue());
            //设置用户默认查询时间段（当前日期后3天）
            user.setStartEnableTime(DateUtil.getCurrentDate());
            user.setEndEnableTime(DateUtil.addDay(DateUtil.getCurrentDate(), ParamConfig.getDefaultDate().intValue()));
            //设置用户注册时间
            user.setRegisterDate(DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
            //设置用户默认状态（是否删除可用）
            user.setDelFlag("N");
            try {
                System.out.println("原始:*****>"+user.getNickName());
                user.setNickName(URLEncoder.encode(user.getNickName(), "utf-8"));
                System.out.println("解码后:*****>"+user.getNickName());
            }catch (Exception e){
                e.printStackTrace();
            }
            int i = userMapper.insertSelective(user);
            if (i > 0) {
                result.put("status", 0);
                result.put("msg", "success");
            } else {
                result.put("status", -2);
                result.put("msg", "插入失败");
            }
        }
        return result;
    }

    /**
     * 用户信息条件搜索
     */
    @RequestMapping("/getUserByPages")
    @ResponseBody
    @ApiOperation(value = "用户信息条件搜索", httpMethod = "POST", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "用户信息条件搜索"),
            @ApiResponse(code = 500, message = "500(系统错误)", response = String.class)
    })
    public PageBean<User> getUserByPages(
            @RequestBody final UserIn userIn
    ) {
        log.info("煞笔：" + userIn.getPageSize());
        Integer pageSizeSet = 10;
        if (userIn.getPageSize() != null) {
            pageSizeSet = userIn.getPageSize();
        }
        return userService.getUserLikeByPages(userIn.getCurrentPage(), userIn, pageSizeSet);
    }

}

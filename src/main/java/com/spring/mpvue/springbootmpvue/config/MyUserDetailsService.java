package com.spring.mpvue.springbootmpvue.config;

import com.spring.mpvue.springbootmpvue.mybatis.mapper.SysRoleMapper;
import com.spring.mpvue.springbootmpvue.mybatis.mapper.SysUserMapper;
import com.spring.mpvue.springbootmpvue.mybatis.mapper.SysUserRoleMapper;
import com.spring.mpvue.springbootmpvue.mybatis.mapper.UserMapper;
import com.spring.mpvue.springbootmpvue.mybatis.po.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xh on 2018/12/20 9:55.
 *
 * @author wy
 */
@Component
public class MyUserDetailsService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 验证逻辑
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //请求中获得type参数
        HttpServletRequest request;
        try{
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }catch (Exception e){
            throw new UsernameNotFoundException("用户类型不能为空");
        }
        //查询用户
        SysUserExample sysUserExample = new SysUserExample();
        SysUserExample.Criteria criteria = sysUserExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<SysUser> userList = userMapper.selectByExample(sysUserExample);
        if (CollectionUtils.isEmpty(userList)) {
            logger.info("登录用户：" + username + "类型：" + request.getParameter("type") + "不存在");
            throw new UsernameNotFoundException("用户名不存在");
        }
        logger.info("登录用户账号：" + userList.get(0).getUsername());

        //用户转型框架实体
        SecurityUser securityUser = new SecurityUser();
        BeanUtils.copyProperties(userList.get(0), securityUser);
        //获取角色
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        SysUserRoleExample.Criteria sysUserRoleParam = sysUserRoleExample.createCriteria();
        sysUserRoleParam.andUserIdEqualTo(securityUser.getUserId());
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectByExample(sysUserRoleExample);
        List<SysRole> sysRoles = sysUserRoles.stream()
                .map(item -> {
                    SysRole sysRole = sysRoleMapper.selectByPrimaryKey(item.getRoleId());
                    return sysRole;
                }).collect(Collectors.toList());
        securityUser.setRole(sysRoles);
        //获取到当前线程绑定的请求对象
        return securityUser;
    }
}

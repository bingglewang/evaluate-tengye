package com.spring.mpvue.springbootmpvue.mybatis.service.impl;

import com.github.pagehelper.PageHelper;
import com.spring.mpvue.springbootmpvue.mybatis.in.ParamIn;
import com.spring.mpvue.springbootmpvue.mybatis.mapper.ParamconfigMapper;
import com.spring.mpvue.springbootmpvue.mybatis.po.Paramconfig;
import com.spring.mpvue.springbootmpvue.mybatis.po.ParamconfigExample;
import com.spring.mpvue.springbootmpvue.mybatis.service.ParamService;
import com.spring.mpvue.springbootmpvue.util.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("paramService")
public class ParamServiceImpl implements ParamService {
    @Autowired
    private ParamconfigMapper paramconfigMapper;

    @Override
    public PageBean<Paramconfig> getParmaByPages(Integer currentPage, ParamIn paramIn, Integer pageSize) {
        //设置分页信息，分别是当前页和页面大小 ，（必须在mapper接口中的方法执行之前设置该分页信息）
        PageHelper.startPage(currentPage,pageSize);
        ParamconfigExample paramconfigExample = new ParamconfigExample();
        ParamconfigExample.Criteria criteria = paramconfigExample.createCriteria();
        //param1
        if(StringUtils.isNotBlank(paramIn.getParam1())){
            criteria.andParam1EqualTo(paramIn.getParam1());
        }
        //param2
        if(StringUtils.isNotBlank(paramIn.getParam2())){
            criteria.andParam2EqualTo(paramIn.getParam2());
        }
        //param3
        if(StringUtils.isNotBlank(paramIn.getParam3())){
            criteria.andParam3EqualTo(paramIn.getParam3());
        }
        //param4
        if(StringUtils.isNotBlank(paramIn.getParam4())){
            criteria.andParam4EqualTo(paramIn.getParam4());
        }
        //degree
        if(paramIn.getDegree() != null){
            criteria.andDegreeEqualTo(paramIn.getDegree());
        }
        paramconfigExample.setOrderByClause("update_time desc");
        paramconfigExample.setDistinct(false);
        //获取所有的记录
        List<Paramconfig> paramconfigList = paramconfigMapper.selectByExample(paramconfigExample);
        //获取记录的总数目
        int paramCount = paramconfigMapper.countByExample(paramconfigExample);
        PageBean<Paramconfig> result = new PageBean<Paramconfig>(currentPage,pageSize,paramCount);
        result.setItems(paramconfigList);
        return result;
    }

    @Override
    public Map<String, Object> updateParamById(ParamIn paramIn) {
        Map<String, Object> result = new HashMap<String,Object>();
        Paramconfig paramconfig = new Paramconfig();
        paramconfig.setId(paramIn.getId());
        paramconfig.setDegree(paramIn.getDegree());
        int i =  paramconfigMapper.updateByPrimaryKeySelective(paramconfig);
        if(i > 0 ){
            result.put("msg","编辑成功!");
            result.put("code",1);
        }else{
            result.put("msg","编辑失败!");
            result.put("code",-1);
        }
        return result;
    }
}

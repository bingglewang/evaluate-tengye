package com.spring.mpvue.springbootmpvue.controller;

import com.spring.mpvue.springbootmpvue.mybatis.in.ParamIn;
import com.spring.mpvue.springbootmpvue.mybatis.po.Paramconfig;
import com.spring.mpvue.springbootmpvue.mybatis.service.ParamService;
import com.spring.mpvue.springbootmpvue.util.PageBean;
import com.spring.mpvue.springbootmpvue.util.ParamConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("param")
public class ParamController {

    @Autowired
    private ParamService paramService;

    /**
     * 分页获取参数表中的记录
     * @param paramIn
     * @return
     */
    @RequestMapping("/getParamByPage")
    @ResponseBody
    public PageBean<Paramconfig> getParamByPage(
            @RequestBody final ParamIn paramIn
    ){
        Integer pageSizeSet = 10;
        if (paramIn.getPageSize() != null) {
            pageSizeSet = paramIn.getPageSize();
        }
        return paramService.getParmaByPages(paramIn.getCurrentPage(),paramIn,pageSizeSet);
    }

    /**
     * 根据id修改参数
     * @param paramIn
     * @return
     */
    @RequestMapping("/updateParamById")
    @ResponseBody
    public Map<String,Object> updateParamById(
            @RequestBody final ParamIn paramIn
    ){
        return paramService.updateParamById(paramIn);
    }

    /**
     * 获取下拉框数据
     * @return
     */
    @RequestMapping("/getSelectData")
    @ResponseBody
    public Object getSelectData(){
        return ParamConfig.getSelectData();
    }

    @RequestMapping("getselectDataBy")
    @ResponseBody
    public Object getselectDataBy(
            @RequestBody final String companyName
    ){
        return ParamConfig.getselectDataBy(companyName);
    }
}

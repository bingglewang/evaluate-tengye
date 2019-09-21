package com.spring.mpvue.springbootmpvue.controller;

import com.spring.mpvue.springbootmpvue.mybatis.in.RecordIn;
import com.spring.mpvue.springbootmpvue.mybatis.mapper.RecordMapper;
import com.spring.mpvue.springbootmpvue.mybatis.mapper.ReferencecaseMapper;
import com.spring.mpvue.springbootmpvue.mybatis.mapper.UserMapper;
import com.spring.mpvue.springbootmpvue.mybatis.po.*;
import com.spring.mpvue.springbootmpvue.mybatis.service.RecordService;
import com.spring.mpvue.springbootmpvue.util.PageBean;
import io.swagger.annotations.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/record")
@Api(value = "RecordController", tags = {"估价记录查询"})
public class RecordController {
    private static org.slf4j.Logger log = LoggerFactory.getLogger(RecordController.class);

    @Autowired
    private RecordService recordService;

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private ReferencecaseMapper referencecaseMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 分页查询估价记录
     */
    @RequestMapping("/getRecordByPage")
    @ResponseBody
    @ApiOperation(value = "分页查询估计记录", httpMethod = "POST", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "分页查询估计记录"),
            @ApiResponse(code = 500, message = "500(系统错误)", response = String.class)
    })
    public PageBean<Record> getRecordBuPage(
            @RequestBody final RecordIn recodIn
    ) {
        Integer pageSizeSet = 10;
        if (recodIn.getPageSize() != null) {
            pageSizeSet = recodIn.getPageSize();
        }
        return recordService.getRecordByPages(recodIn.getCurrentPage(), recodIn, pageSizeSet);
    }

    /**
     * 根据openid向record表中插入数据
     */
    @RequestMapping("/insertRecordByOpenid")
    @ResponseBody
    @ApiOperation(value = "根据openid向record表中插入数据", httpMethod = "POST", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "根据openid向record表中插入数据"),
            @ApiResponse(code = 500, message = "500(系统错误)", response = String.class)
    })
    public Map<String, Object> insertRecordByOpenid(
            @RequestBody final Record record
    ) {
        Map<String, Object> result = new HashMap<String, Object>();
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andOpenIdEqualTo(record.getOpenId());
        //用户是否被禁用
        criteria.andDelFlagEqualTo("N");
        List<User> userList = userMapper.selectByExample(userExample);
        if (userList.size() > 0) {
            //插入用户姓名
            record.setBack1(userList.get(0).getUserName());
            int i = recordMapper.insertSelective(record);
            log.info("我的id:" + record.getId());
            if (i > 0) {
                result.put("status", 0);
                result.put("msg", "success");
                result.put("data", record.getId());
            } else {
                result.put("status", -1);
                result.put("msg", "failed");
            }
        } else {
            result.put("status", -2);
            result.put("msg", "failed");
        }
        return result;
    }

    /**
     * 更新record数据根据id主键
     */
    @RequestMapping("/updateRecordById")
    @ResponseBody
    @ApiOperation(value = "更新record数据根据id主键", httpMethod = "POST", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "更新record数据根据id主键"),
            @ApiResponse(code = 500, message = "500(系统错误)", response = String.class)
    })
    public Map<String, Object> updateRecord(
            @RequestBody final Record record
    ) {
        Map<String, Object> result = new HashMap<String, Object>();
        int i = recordMapper.updateByPrimaryKeySelective(record);
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
     * 根据主键获取recor记录
     */
    @RequestMapping("/getRcordById")
    @ResponseBody
    @ApiOperation(value = "根据主键获取recor记录", httpMethod = "POST", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "根据主键获取recor记录"),
            @ApiResponse(code = 500, message = "500(系统错误)", response = String.class)
    })
    public Map<String, Object> getRecordById(
            @RequestBody final Record record
    ) {
        Map<String, Object> result = new HashMap<String, Object>();
        Record record1 = recordMapper.selectByPrimaryKey(record.getId());
        if (record1 != null) {
            result.put("status", 0);
            result.put("msg", "success");
            result.put("data", record1);
        } else {
            result.put("status", -1);
            result.put("msg", "failed");
        }
        return result;
    }

    /**
     * 获取参考案例记录
     */
    @RequestMapping("/getReferenceRecord")
    @ResponseBody
    @ApiOperation(value = "获取参考案例记录", httpMethod = "POST", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取参考案例记录"),
            @ApiResponse(code = 500, message = "500(系统错误)", response = String.class)
    })
    public Map<String, Object> getReferenceRecord(
            @RequestBody final Referencecase referencecase
    ) {
        Map<String, Object> result = new HashMap<String,Object>();
        ReferencecaseExample referencecaseExample = new ReferencecaseExample();
        ReferencecaseExample.Criteria criteria = referencecaseExample.createCriteria();
        criteria.andRecordIdEqualTo(referencecase.getRecordId());
        List<Referencecase> referencecaseList =  referencecaseMapper.selectByExample(referencecaseExample);
        if(referencecaseList != null && referencecaseList.size() >0){
            result.put("status",0);
            result.put("msg","success");
            result.put("data",referencecaseList);
        }else{
            result.put("status",-1);
            result.put("msg","failed");
            result.put("data","");
        }
        return  result;
    }
}

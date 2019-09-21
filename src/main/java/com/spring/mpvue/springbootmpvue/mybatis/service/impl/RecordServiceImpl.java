package com.spring.mpvue.springbootmpvue.mybatis.service.impl;

import com.github.pagehelper.PageHelper;
import com.spring.mpvue.springbootmpvue.mybatis.in.RecordIn;
import com.spring.mpvue.springbootmpvue.mybatis.mapper.RecordMapper;
import com.spring.mpvue.springbootmpvue.mybatis.po.Record;
import com.spring.mpvue.springbootmpvue.mybatis.po.RecordExample;
import com.spring.mpvue.springbootmpvue.mybatis.service.RecordService;
import com.spring.mpvue.springbootmpvue.util.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("recordService")
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public PageBean<Record> getRecordByPages(Integer currentPage, RecordIn record, Integer pageSize) {
        //设置分页信息，分别是当前页和页面大小 ，（必须在mapper接口中的方法执行之前设置该分页信息）
        PageHelper.startPage(currentPage,pageSize);
        RecordExample recordExample = new RecordExample();
        RecordExample.Criteria criteria = recordExample.createCriteria();
        //楼盘
        if(StringUtils.isNotBlank(record.getAddress())){
            String address = record.getAddress();
            address = "%" + address + "%";
            criteria.andDetailAddressLike(address);
        }
        if(StringUtils.isNotBlank(record.getOpenId())){
            criteria.andOpenIdEqualTo(record.getOpenId());
        }
        //根据用户查询(小程序使用)back1
        if(StringUtils.isNotBlank(record.getBack1())){
            criteria.andBack1Like("%"+record.getBack1()+"%");
        }
        //楼栋     own_status
        if(StringUtils.isNotBlank(record.getOwnStatus())){
            criteria.andOwnStatusLike("%"+record.getOwnStatus()+"%");
        }
        //楼层     floor

        //房号     indoor_structure
        if(StringUtils.isNotBlank(record.getIndoorStructure())){
            criteria.andIndoorStructureLike("%"+record.getIndoorStructure()+"%");
        }
        //户型     house_type
        if(StringUtils.isNotBlank(record.getHouseType())){
            criteria.andHouseTypeLike("%"+record.getHouseType()+"%");
        }
        //朝向     orientation
        if(StringUtils.isNotBlank(record.getOrientation())){
            criteria.andOrientationLike("%"+record.getOrientation()+"%");
        }
        //建筑面积  area
        if(StringUtils.isNotBlank(record.getArea())){
            criteria.andAreaLike("%"+record.getArea()+"%");
        }
        //装修程度  degree_decoration
        if(StringUtils.isNotBlank(record.getDegreeDecoration())){
            criteria.andDegreeDecorationLike("%"+record.getDegreeDecoration()+"%");
        }
        //外墙装饰  build_type
        if(StringUtils.isNotBlank(record.getBuildType())){
            criteria.andBuildTypeLike("%"+record.getBuildType()+"%");
        }
        //建筑年代  build_year
        if(StringUtils.isNotBlank(record.getBuildYear())){
            criteria.andBuildYearLike("%"+record.getBuildYear()+"%");
        }
        //查询时间  query_date
        if(StringUtils.isNotBlank(record.getStartQueryDate()) && StringUtils.isNotBlank(record.getEndQueryDate())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = new Date();
            Date end = new Date();
            try{
                start =  sdf.parse(record.getStartQueryDate());
                end = sdf.parse(record.getEndQueryDate());
            }catch (Exception e){
                e.printStackTrace();
            }
            criteria.andQueryDateGreaterThanOrEqualTo(start);
            criteria.andQueryDateLessThanOrEqualTo(end);
        }
        //所属用户
        if(StringUtils.isNotBlank(record.getBack1())){
            criteria.andBack1Like("%"+record.getBack1()+"%");
        }
        //查询结果  query_result
        if(StringUtils.isNotBlank(record.getQueryResult())){
            criteria.andQueryResultLike("%"+record.getQueryResult()+"%");
        }
        //结果异议
        if(StringUtils.isNotBlank(record.getDissent())){
            criteria.andDissentEqualTo(record.getDissent());
        }
        //预期结果
        if(StringUtils.isNotBlank(record.getExpectedPrice())){
            criteria.andExpectedPriceLike("%"+record.getExpectedPrice()+"%");
        }
        recordExample.setOrderByClause("query_date desc");
        recordExample.setDistinct(false);
        //获取记录的总数目
        List<Record>  recordList = recordMapper.selectByExample(recordExample);
        //获取所有的记录
        int recordCount = recordMapper.countByExample(recordExample);
        PageBean<Record> result = new PageBean<Record>(currentPage,pageSize,recordCount);
        result.setItems(recordList);
        return result;
    }
}

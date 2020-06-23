package com.spring.mpvue.springbootmpvue.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.mpvue.springbootmpvue.mybatis.in.CellSearchIn;
import com.spring.mpvue.springbootmpvue.mybatis.in.EvaluateVo;
import com.spring.mpvue.springbootmpvue.mybatis.mapper.SysApiConfigMapper;
import com.spring.mpvue.springbootmpvue.mybatis.mapper.SysLogMapper;
import com.spring.mpvue.springbootmpvue.mybatis.mapper.SysUserMapper;
import com.spring.mpvue.springbootmpvue.mybatis.po.SysApiConfig;
import com.spring.mpvue.springbootmpvue.mybatis.po.SysApiConfigExample;
import com.spring.mpvue.springbootmpvue.mybatis.po.SysLogWithBLOBs;
import com.spring.mpvue.springbootmpvue.mybatis.po.SysUser;
import com.spring.mpvue.springbootmpvue.util.HttpUtil;
import com.spring.mpvue.springbootmpvue.util.JavaWebToken;
import com.spring.mpvue.springbootmpvue.util.SysLogUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName HouseApiController
 * @Description 房价接口封装
 * @Author binggleW
 * @Date 2020-06-02 11:02
 * @Version 1.0
 **/
@RestController
@RequestMapping("house")
public class HouseApiController {
    /**
     * 小区接口
     */
    @Value("${fj.xqRequestUrl}")
    private String xqRequestUrl;

    /**
     * 评估接口
     */
    @Value("${fj.evaluateUrl}")
    private String evaluateUrl;

    @Autowired
    private SysLogMapper sysLogMapper;

    @Autowired
    private SysApiConfigMapper sysApiConfigMapper;

    @Autowired
    private SysUserMapper sysUserMapper;


    /**
     * 小区搜索
     * @return
     */
    @PostMapping("/cellSearch")
    public Map<String, Object> cellSearch(
            @RequestBody @Valid final CellSearchIn cellSearchIn
    ){
        System.out.println("小区参数："+JSONObject.toJSONString(cellSearchIn));
        long start = System.currentTimeMillis();
        // 房价token
        Map<String,Object> tokenMap = JavaWebToken.parserJavaWebToken(cellSearchIn.getToken());
        if(tokenMap == null){
            Map<String, Object> resultTokenEmpty = new HashMap<>();
            resultTokenEmpty.put("code",401);
            resultTokenEmpty.put("message","登录失败，请重新登录");
            resultTokenEmpty.put("data",null);
            return resultTokenEmpty;
        }
        String token = (tokenMap.get("token")).toString();
        token = token.substring(1,token.length()-1);
        String loginId = (tokenMap.get("loginId")).toString();
        String name = (tokenMap.get("name")).toString();
        if(StringUtils.isBlank(token) || StringUtils.isBlank(loginId) ){
            Map<String, Object> resultTokenEmpty = new HashMap<>();
            resultTokenEmpty.put("code",401);
            resultTokenEmpty.put("message","登录失败，请重新登录");
            resultTokenEmpty.put("data",null);
            return resultTokenEmpty;
        }

        Integer userId = Integer.parseInt(loginId);
        //判断用户是否禁用和次数
        boolean isPower = isUserApiPower(userId,1);
        if(!isPower){
            Map<String, Object> resultPower= new HashMap<>();
            resultPower.put("code",400);
            resultPower.put("message","用户被禁用或次数已经用完，请联系管理员");
            resultPower.put("data",null);
            return resultPower;
        }

        StringBuilder url = new StringBuilder();
        url.append(this.xqRequestUrl);
        url.append("?city=" + cellSearchIn.getCity());
        url.append("&keyword=" + cellSearchIn.getKeyword());
        if(StringUtils.isNotBlank(cellSearchIn.getDistrict())){
            url.append("&district="+cellSearchIn.getDistrict());
        }
        url.append("&block="+cellSearchIn.getBlock());
        url.append("&lat="+cellSearchIn.getLat());
        url.append("&lng="+cellSearchIn.getLng());
        url.append("&from="+cellSearchIn.getFrom());
        url.append("&radius="+cellSearchIn.getRadius());
        url.append("&max="+cellSearchIn.getMax());
        url.append("&token=" + token);
        // 发送请求
        String data = HttpUtil.get(url.toString());
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = new HashMap<>();
        JsonNode json = null;
        try {
            json = mapper.readTree(data);
            result = mapper.readValue(data, Map.class);
            System.out.println("小区搜索返回结果:"+result);
            if(Integer.parseInt(json.get("code").toString()) - 200 == 0){
                // 扣减api次数
                dedcutionUserApiCount(userId,1);
            }
        }catch (Exception e){
            e.printStackTrace();
            Map<String, Object> resultPower= new HashMap<>();
            resultPower.put("code",500);
            resultPower.put("message","查询失败");
            resultPower.put("data",null);
            return resultPower;
        }

        // 插入日志
        SysLogWithBLOBs sysLogWithBLOBs = SysLogUtils.getSysLog();
        //执行结果
        sysLogWithBLOBs.setException(data);
        sysLogWithBLOBs.setServiceId("小区搜索接口");
        sysLogWithBLOBs.setType("1");
        sysLogWithBLOBs.setTitle("小区搜索接口调用记录");
        long end = System.currentTimeMillis();
        sysLogWithBLOBs.setTime((end - start) + "");
        sysLogWithBLOBs.setParams(JSONObject.toJSONString(cellSearchIn));
        sysLogWithBLOBs.setCreateTime(new Date());
        sysLogWithBLOBs.setUpdateTime(new Date());
        sysLogWithBLOBs.setCreateBy(name);
        sysLogMapper.insert(sysLogWithBLOBs);

        return result;
    }


    /**
     * 房价评估接口
     * @return
     */
    @PostMapping("/evaluate")
    public Map<String,Object> evaluate(
            @RequestBody @Valid final EvaluateVo evaluateVo
    ){
        System.out.println("评估参数："+JSONObject.toJSONString(evaluateVo));
        long start = System.currentTimeMillis();
        // 房价token
        Map<String,Object> tokenMap = JavaWebToken.parserJavaWebToken(evaluateVo.getToken());
        if(tokenMap == null){
            Map<String, Object> resultTokenEmpty = new HashMap<>();
            resultTokenEmpty.put("code",401);
            resultTokenEmpty.put("message","登录失败，请重新登录");
            resultTokenEmpty.put("data",null);
            return resultTokenEmpty;
        }
        String token = (tokenMap.get("token")).toString();
        token = token.substring(1,token.length()-1);
        String loginId = (tokenMap.get("loginId")).toString();
        String name = (tokenMap.get("name")).toString();
        if(StringUtils.isBlank(token) || StringUtils.isBlank(loginId)){
            Map<String, Object> resultTokenEmpty = new HashMap<>();
            resultTokenEmpty.put("code",401);
            resultTokenEmpty.put("message","登录失败，请重新登录");
            resultTokenEmpty.put("data",null);
            return resultTokenEmpty;
        }



        Integer userId = Integer.parseInt(loginId);
        //判断用户是否禁用和次数
        boolean isPower = isUserApiPower(userId,2);
        if(!isPower){
            Map<String, Object> resultPower= new HashMap<>();
            resultPower.put("code",400);
            resultPower.put("message","用户被禁用或次数已经用完，请联系管理员");
            resultPower.put("data",null);
            return resultPower;
        }

        String url = this.evaluateUrl +
                "?city=" + evaluateVo.getCity() +
                "&name=" + evaluateVo.getName() +
                "&buildingNumber=" + evaluateVo.getBuildingNumber() +
                "&unitNumber=" + evaluateVo.getUnitNumber() +
                "&size=" + evaluateVo.getSize() +
                "&floor=" + evaluateVo.getFloor() +
                "&maxFloor=" + evaluateVo.getMaxFloor() +
                "&calDate=" + evaluateVo.getCalDate() +
                "&propertyType=" + evaluateVo.getPropertyType() +
                "&room=" + evaluateVo.getRoom()+
                "&hall=" + evaluateVo.getHall()+
                "&toilet=" + evaluateVo.getToilet()+
                "&direction=" + evaluateVo.getDirection()+
                "&view=" + evaluateVo.getView()+
                "&decoration=" + evaluateVo.getDecoration()+
                "&decorationCost="+evaluateVo.getDecorationCost()+
                "&decorationAge="+evaluateVo.getDecorationAge()+
                "&noise="+evaluateVo.getNoise()+
                "&gardenSize="+evaluateVo.getGardenSize()+
                "&basementSize="+evaluateVo.getBasementSize()+
                "&negativeFactor="+evaluateVo.getNegativeFactor()+
                "&carportPrice="+evaluateVo.getCarportPrice()+
                "&facilities="+evaluateVo.getFacilities()+
                "&insidePosition="+evaluateVo.getInsidePosition()+
                "&token=" + token;
        if(StringUtils.isNotBlank(evaluateVo.getDistrict())){
            url +=  "&district=" + evaluateVo.getDistrict();
        }
        // 发送请求
        String data = HttpUtil.get(url);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = new HashMap<>();
        JsonNode json = null;
        try {
            json = mapper.readTree(data);
            result = mapper.readValue(data, Map.class);
            System.out.println("评估返回结果:"+result);
            if(Integer.parseInt(json.get("code").toString()) - 200 == 0){
               // 扣减api次数
                dedcutionUserApiCount(userId,2);
            }
        }catch (Exception e){
            e.printStackTrace();
            Map<String, Object> resultPower= new HashMap<>();
            resultPower.put("code",500);
            resultPower.put("message","查询失败");
            resultPower.put("data",null);
            return resultPower;
        }

        // 插入日志
        SysLogWithBLOBs sysLogWithBLOBs = SysLogUtils.getSysLog();
        //执行结果
        sysLogWithBLOBs.setException(data);
        sysLogWithBLOBs.setServiceId("评估接口");
        sysLogWithBLOBs.setType("2");
        sysLogWithBLOBs.setTitle("房价评估接口调用记录");
        long end = System.currentTimeMillis();
        sysLogWithBLOBs.setTime((end - start) + "");
        sysLogWithBLOBs.setParams(JSONObject.toJSONString(evaluateVo));
        sysLogWithBLOBs.setCreateTime(new Date());
        sysLogWithBLOBs.setUpdateTime(new Date());
        sysLogWithBLOBs.setCreateBy(name);
        sysLogMapper.insert(sysLogWithBLOBs);

        return result;
    }


    /**
     * 扣减用户api次数
     * @param userId
     * @param type
     */
    public void dedcutionUserApiCount(Integer userId,Integer type){
        SysApiConfigExample sysApiConfigExample = new SysApiConfigExample();
        SysApiConfigExample.Criteria criteria = sysApiConfigExample.createCriteria();
        criteria.andSysUserIdEqualTo(userId);
        criteria.andConfitTypeEqualTo(type);
        List<SysApiConfig> sysApiConfigs = sysApiConfigMapper.selectByExample(sysApiConfigExample);
        if(!CollectionUtils.isEmpty(sysApiConfigs)){
            SysApiConfig sysApiConfigUpdate =  new SysApiConfig();
            sysApiConfigUpdate.setId(sysApiConfigs.get(0).getId());
            sysApiConfigUpdate.setUsedCount(sysApiConfigs.get(0).getUsedCount() + 1);
            sysApiConfigMapper.updateByPrimaryKeySelective(sysApiConfigUpdate);
        }
    }

    /**
     * 判断用户是否具有权限
     * @param userId
     * @param type
     * @return
     */
    public boolean isUserApiPower(Integer userId,Integer type){
        SysApiConfigExample sysApiConfigExample = new SysApiConfigExample();
        SysApiConfigExample.Criteria criteria = sysApiConfigExample.createCriteria();
        criteria.andSysUserIdEqualTo(userId);
        criteria.andConfitTypeEqualTo(type);
        List<SysApiConfig> sysApiConfigs = sysApiConfigMapper.selectByExample(sysApiConfigExample);
        boolean result = false;
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        if(sysUser == null){
            return false;
        }
        if(!sysUser.getExisted()){
            return false;
        }
        if(!CollectionUtils.isEmpty(sysApiConfigs)){
            SysApiConfig sysApiConfig =  sysApiConfigs.get(0);
            if(sysApiConfig.getTotalCount() - sysApiConfig.getUsedCount() > 0){
                result = true;
            }
        }
        return result;
    }
}

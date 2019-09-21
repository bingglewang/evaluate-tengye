package com.spring.mpvue.springbootmpvue.util;

import com.spring.mpvue.springbootmpvue.mybatis.mapper.ParamconfigMapper;
import com.spring.mpvue.springbootmpvue.mybatis.po.Paramconfig;
import com.spring.mpvue.springbootmpvue.mybatis.po.ParamconfigExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取评估系数（100/100+案例的等级值-评估对象的等级值）
 */
@Component
public class ParamConfig {

    @Autowired
    private ParamconfigMapper paramconfigMapper;

    private static ParamconfigMapper paramconfigMapperDoubble;

    @PostConstruct
    public void init() {
        paramconfigMapperDoubble = paramconfigMapper;
    }

    /**
     * 获取户型系数(简单处理，只处理用户的)
     */
    public static Double getPMParm(String houseCount) {
        if (StringUtils.isBlank(houseCount)) {
            houseCount = "";
        }
        double result = 0;
        int fenzhi = 100;
        float fenmu = 100 + (getPmDegree(houseCount));
        result = fenzhi * 1.0 / fenmu;
        return result;
    }

    /**
     * 获取楼层系数
     */
    public static Double getFloorParam(String sourceLc, String sourceZcs, String targetLc, String targetZcs) {
        if (StringUtils.isBlank(sourceLc)) {
            sourceLc = "";
        }
        if (StringUtils.isBlank(sourceZcs)) {
            sourceZcs = "";
        }
        if (StringUtils.isBlank(targetLc)) {
            targetLc = "";
        }
        if (StringUtils.isBlank(targetZcs)) {
            targetZcs = "";
        }
        System.out.println("我的sqlserver楼层：" + sourceLc + "," + sourceZcs);
        System.out.println("我的mysql楼层：" + targetLc + "," + targetZcs);
        double result = 0;
        int fenzhi = 100;
        float fenmu = 100 + (getPcgDegree(sourceLc,sourceZcs) - getPcgDegree(targetLc,targetZcs));
        result = fenzhi * 1.0 / fenmu;
        return result;
    }

    /**
     * 获取朝向系数
     */
    public static Double getOrientationParam(String source, String target) {
        if (StringUtils.isBlank(source)) {
            source = "";
        }
        if (StringUtils.isBlank(target)) {
            target = "";
        }
        double result = 0;
        int fenzhi = 100;
        float fenmu = 100 + (getChaoxiangDegree(source) - getChaoxiangDegree(target));
        result = fenzhi * 1.0 / fenmu;
        return result;
    }

    /**
     * 获取面积系数
     */
    public static Double getAreaParam(String souce, String target) {
        if (StringUtils.isBlank(souce)) {
            souce = "0";
        }
        if (StringUtils.isBlank(target)) {
            target = "0";
        }
        double result = 0;
        int fenzi = 100;
        float fenmu = 100 + (getAreaDegree(souce) - getAreaDegree(target));
        result = fenzi * 1.0 / fenmu;
        return result;
    }

    /**
     * 获取装修程度系数(简单处理)
     */
    public static Double getDecorationDegreeParam(String souce) {
        if (StringUtils.isBlank(souce)) {
            souce = "";
        }
        double result = 0;
        int fenzi = 100;
        float fenmu = 100 + (getDecorationDegree(souce));
        result = fenmu * 1.0 / fenzi;
        return result;
    }

    /**
     * 获取朝向等级值(1)
     */
    public static float getChaoxiangDegree(String cx) {
        //从参数表查询朝向等级值
        if(cx.indexOf("向") > 0 ){
            cx = cx.substring(0,cx.length()-1);
        }
        ParamconfigExample paramconfigExample = new ParamconfigExample();
        ParamconfigExample.Criteria criteria = paramconfigExample.createCriteria();
        criteria.andParam1EqualTo("CX");
        float degree = 0;
        criteria.andParam2EqualTo(cx);
        List<Paramconfig> paramconfigList = paramconfigMapperDoubble.selectByExample(paramconfigExample);
        if(paramconfigList.size() > 0 && paramconfigList != null){
            try {
                degree = Float.valueOf(paramconfigList.get(0).getDegree());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return degree;
    }

    /**
     * 获取面积等级值(2)
     */
    public static Float getAreaDegree(String area) {
        //从数据库获取面积等级值
        ParamconfigExample paramconfigExample = new ParamconfigExample();
        ParamconfigExample.Criteria criteria = paramconfigExample.createCriteria();
        criteria.andParam1EqualTo("MJ");
        String myType = "";
        float degree = 0;
        double areaInt = 0;
        try {
            areaInt = Double.parseDouble(area);
            if (areaInt > 144) {
                myType = "mj3";
            } else if (areaInt >= 90 && areaInt <= 144) {
                myType = "mj2";
            } else if (areaInt < 90) {
                myType = "mj1";
            } else {
                myType = "";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        criteria.andParam2EqualTo(myType);
        List<Paramconfig> paramconfigList = paramconfigMapperDoubble.selectByExample(paramconfigExample);
        if(paramconfigList.size() > 0 && paramconfigList != null){
            try {
                degree = Float.valueOf(paramconfigList.get(0).getDegree());
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return degree;
    }

    /**
     * 获取装修程度系数(3)
     */
    public static Float getDecorationDegree(String decoration) {
        //从参数表查询等级值
        ParamconfigExample paramconfigExample = new ParamconfigExample();
        ParamconfigExample.Criteria criteria = paramconfigExample.createCriteria();
        criteria.andParam1EqualTo("ZX");
        float degree = 0;
        criteria.andParam2EqualTo(decoration);
        List<Paramconfig> paramconfigList = paramconfigMapperDoubble.selectByExample(paramconfigExample);
        if(paramconfigList.size() > 0 && paramconfigList != null){
            try {
                degree = Float.valueOf(paramconfigList.get(0).getDegree());
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return degree;
    }

    /**
     * 获取户型等级值(4)
     */
    public static Float getPmDegree(String houseCount) {
        //从数据库获取户型等级值
        ParamconfigExample paramconfigExample = new ParamconfigExample();
        ParamconfigExample.Criteria criteria = paramconfigExample.createCriteria();
        criteria.andParam1EqualTo("HX");
        float degree = 0;
        criteria.andParam2EqualTo(houseCount);   //hx1,hx2,hx3,hx4,hx5
        List<Paramconfig> paramconfigList = paramconfigMapperDoubble.selectByExample(paramconfigExample);
        if(paramconfigList.size() > 0 && paramconfigList != null){
            try {
                degree = Float.valueOf(paramconfigList.get(0).getDegree());
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return degree;
    }

    /**
     * 获取所在层数等级制（5）
     */
    public static Float getPcgDegree(String pcg,String zcs){
        //从数据库中获取楼层系数
        ParamconfigExample paramconfigExample = new ParamconfigExample();
        ParamconfigExample.Criteria criteria = paramconfigExample.createCriteria();
        criteria.andParam1EqualTo("LC");
        float degree = 0;
        criteria.andParam2EqualTo(pcg);   //所在楼层
        criteria.andParam3EqualTo(zcs);   //总层数
        List<Paramconfig> paramconfigList = paramconfigMapperDoubble.selectByExample(paramconfigExample);
        if(paramconfigList.size() > 0 && paramconfigList != null){
            try {
                degree = Float.valueOf(paramconfigList.get(0).getDegree());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return degree;
    }

    /**
     * 获取用户默认查询次数
     * @return
     */
    public static Float getDefaultCount(){
        //从数据库中获取楼层系数
        ParamconfigExample paramconfigExample = new ParamconfigExample();
        ParamconfigExample.Criteria criteria = paramconfigExample.createCriteria();
        criteria.andParam1EqualTo("DEFAULT_COUNT");
        List<Paramconfig> paramconfigList = paramconfigMapperDoubble.selectByExample(paramconfigExample);
        float degree = 0;
        if(paramconfigList.size() > 0 && paramconfigList != null){
            try {
                degree = Float.valueOf(paramconfigList.get(0).getDegree());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return degree;
    }

    /**
     * 获取默认查询时间段
     * @return
     */
    public static Float getDefaultDate(){
        ParamconfigExample paramconfigExample = new ParamconfigExample();
        ParamconfigExample.Criteria criteria = paramconfigExample.createCriteria();
        criteria.andParam1EqualTo("DEFAULT_DATE");
        List<Paramconfig> paramconfigList = paramconfigMapperDoubble.selectByExample(paramconfigExample);
        float degree = 0;
        if(paramconfigList.size() > 0 && paramconfigList != null){
            try {
                degree = Float.valueOf(paramconfigList.get(0).getDegree());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return degree;
    }

    public static Object getselectDataBy(String companyName){
        ParamconfigExample paramconfigExample = new ParamconfigExample();
        ParamconfigExample.Criteria criteria = paramconfigExample.createCriteria();
        criteria.andParam1EqualTo(companyName);
        List<String> result = new ArrayList<>();
        List<Paramconfig>  paramconfigList = paramconfigMapperDoubble.selectByExample(paramconfigExample);
        for(Paramconfig paramConfig : paramconfigList){
            result.add(paramConfig.getParam4());
        }
        return  result;
    }

    /**
     * 获取下拉选项值
     * @return
     */
    public static Object getSelectData(){
        Map<String,Object> result = new HashMap<>();
        ParamconfigExample paramconfigExample = new ParamconfigExample();
        ParamconfigExample.Criteria criteria = paramconfigExample.createCriteria();
        criteria.andParam2EqualTo(".NET");
        List<Paramconfig> paramconfigList = paramconfigMapperDoubble.selectByExample(paramconfigExample);
        List<String> baojia = new ArrayList<>();
        List<String> weituo = new ArrayList<>();
        List<String> company = new ArrayList<>();
        for(Paramconfig paramconfig : paramconfigList){
            if(YewuEnum.BAOGAO_TYPE.getValue().equals(paramconfig.getParam1())){
                baojia.add(paramconfig.getParam4());
            }else if(YewuEnum.WEITUO_TYPE.getValue().equals(paramconfig.getParam1())){
                weituo.add(paramconfig.getParam4());
            }else if(YewuEnum.WEITUO_COMPANY.getValue().equals(paramconfig.getParam1())){
                company.add(paramconfig.getParam4());
            }
        }
        result.put("baojia",baojia);
        result.put("weituo",weituo);
        result.put("company",company);
        return result;
    }
}

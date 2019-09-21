package com.spring.mpvue.springbootmpvue.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.mpvue.springbootmpvue.mybatis.in.CellSearchIn;
import com.spring.mpvue.springbootmpvue.mybatis.mapper.RecordMapper;
import com.spring.mpvue.springbootmpvue.mybatis.mapper.UserMapper;
import com.spring.mpvue.springbootmpvue.mybatis.po.Record;
import com.spring.mpvue.springbootmpvue.mybatis.po.Referencecase;
import com.spring.mpvue.springbootmpvue.mybatis.po.User;
import com.spring.mpvue.springbootmpvue.mybatis.po.UserExample;
import com.spring.mpvue.springbootmpvue.util.HttpUtil;
import com.spring.mpvue.springbootmpvue.util.JavaWebToken;
import com.spring.mpvue.springbootmpvue.util.ParamConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("fj")
@Api(value = "EvaluateController",tags = {"房价接口"})
public class EvaluateController {
    @Value("${fj.username}")
    private String username;

    @Value("${fj.password}")
    private String password;

    @Value("${fj.appKey}")
    private String appKey;

    @Value("${fj.requestUrl}")
    private String requestUrl;

    @Value("${fj.xqRequestUrl}")
    private String xqRequestUrl;

    @Value("${fj.evaluateUrl}")
    private String evaluateUrl;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RecordMapper recordMapper;

    /**
     * 获取房价接口token
     * @return
     */
    @RequestMapping("/getToken")
    @ResponseBody
    @ApiOperation(value = "获取房价接口token",httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取房价接口token"),
            @ApiResponse(code = 500, message = "500(系统错误)", response = String.class)
    })
    public Map<String, Object> getToken(){
        String url = this.requestUrl + "?username=" + username + "&password=" + password + "&appKey=" + appKey;
        // 发送请求
        String data = HttpUtil.get(url);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = new HashMap<>();
        try {
            JsonNode jsonNode = mapper.readTree(data);
            Map<String,Object> m = new HashMap<String,Object>();
            if(Integer.parseInt(jsonNode.get("code").toString()) == 200){
                String token = (jsonNode.get("result").get("token")).toString();
                m.put("token",token.substring(1,token.length()-1));
                result.put("token",JavaWebToken.createJavaWebToken(m));
            }else{
                result.put("token","");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 小区搜索
     * @return
     */
    @RequestMapping("/cellSearch")
    @ResponseBody
    @ApiOperation(value = "小区搜索",httpMethod = "POST",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "小区搜索"),
            @ApiResponse(code = 500, message = "500(系统错误)", response = String.class)
    })
    public Map<String, Object> cellSearch(
            @RequestBody final CellSearchIn cellSearchIn
            ){
        String token = (JavaWebToken.parserJavaWebToken(cellSearchIn.getToken()).get("token")).toString(); // 房价token
        String url = this.xqRequestUrl + "?city=" + cellSearchIn.getCity()+ "&keyword=" + cellSearchIn.getKeyword()+"&token="+token+"&district="+cellSearchIn.getDistrict();
        // 发送请求
        String data = HttpUtil.get(url);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = new HashMap<>();
        try {
            result = mapper.readValue(data, Map.class);
            System.out.println("返回结果:"+result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 房价评估接口
     * @return
     */
    @RequestMapping("/evaluate")
    @ResponseBody
    @ApiOperation(value = "房价评估接口",httpMethod = "POST", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "房价评估接口"),
            @ApiResponse(code = 500, message = "500(系统错误)", response = String.class)
    })
    public Map<String, Object> evaluate(
            @RequestBody final Record record
    ){
        Map<String, Object> result = new HashMap<String, Object>();
        String token = (JavaWebToken.parserJavaWebToken(record.getBack5()).get("token")).toString(); // 房价token
        String city = record.getBack4(); //城市，例：上海
        String district = record.getBack3(); //区域，例：长宁
        String name = record.getDetailAddress(); //小区名
        String buildingNumber = record.getOwnStatus(); //楼号
        String unitNumber = record.getIndoorStructure(); //室号
        //楼层系数
        String floor = ""; //所在楼层
        String maxFloor = ""; //总楼层
        if (StringUtils.isNotBlank(record.getFloor())) {
            String targetLc = record.getFloor().split("/")[0];
            String targetZcs = record.getFloor().split("/")[1];
            floor = targetLc.substring(0, targetLc.length() - 1);
            maxFloor = targetZcs.substring(0, targetZcs.length() - 1);
        }
        String size = record.getArea(); //面积
        String room = "";
        String hall = "";
        String toilet = "";
        if(StringUtils.isNotBlank(record.getHouseType())) {
            room = record.getHouseType().split("室")[0]; //室数量
            hall = ((record.getHouseType().split("室")[1]).split("厅")[0]).split("/")[1]; // 厅数量
            toilet = ((record.getHouseType().split("厅")[1]).split("卫")[0]).split("/")[1]; // 卫数量
        }
        String direction = record.getOrientation(); //朝向
        String decoration = record.getDegreeDecoration(); //装修情况
        String buildType = record.getBuildType(); //外墙装饰
        String url = this.evaluateUrl +
                "?city=" + city +
                "&district=" + district +
                "&name=" + name +
                "&buildingNumber=" + buildingNumber +
                "&unitNumber=" + unitNumber +
                "&size=" + size +
                "&floor=" + floor +
                "&maxFloor=" + maxFloor +
                "&room=" + room+
                "&hall=" + hall+
                "&toilet=" + toilet+
                "&direction=" + direction+
                "&view=" + buildType+
                "&token=" + token+
                "&decoration=" + decoration;
        //向record表中插入数据
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andOpenIdEqualTo(record.getOpenId());
        //用户是否被禁用
        criteria.andDelFlagEqualTo("N");
        List<User> userList = userMapper.selectByExample(userExample);
        if (userList.size() > 0) {
            // 发送请求
            String data = HttpUtil.get(url);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = null;
            try {
                json = mapper.readTree(data);
                System.out.println("返回结果:" + json);
                if(Integer.parseInt(json.get("code").toString()) != 200){
                    result.put("status", -2);
                    result.put("msg", json.get("msg").toString());
                    result.put("data", "");
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //插入用户姓名
            record.setBack1(userList.get(0).getUserName());
            //查询结果
            //修正装修系数
            Integer avgPriceInt =  Integer.parseInt((json.get("result")).get("avgPrice").toString());
            avgPriceInt = new Long((Math.round((avgPriceInt * ParamConfig.getDecorationDegreeParam(decoration))))).intValue();
            record.setQueryResult(avgPriceInt.toString());
            Integer totalPriceInt = Math.round((avgPriceInt * (Float.parseFloat(size)))*1.0f/10000);
            //想back5中存总价格
           // String totalResult = Math.round((Float.parseFloat((json.get("result")).get("totalPrice").toString()) / 10000)) + "";
            record.setBack5(totalPriceInt.toString());
            //建筑年代（查询的案例中的众数或者用户输入的年代）
            int i = recordMapper.insertSelective(record);
            if (i > 0) {
                //每查询一次更改用户可查询次数
                UserExample userExample1 = new UserExample();
                UserExample.Criteria criteria1 = userExample1.createCriteria();
                if (StringUtils.isNotBlank(record.getOpenId())) {
                    criteria1.andOpenIdEqualTo(record.getOpenId());
                }
                List<User> userList1 = userMapper.selectByExample(userExample1);
                User saveUser = new User();
                if (userList1 != null && userList1.size() > 0) {
                    //记录查询次数
                    saveUser.setBack1((Integer.parseInt(userList1.get(0).getBack1())+1)+"");
                    //如果在查询时间段内，则不修改查询次数，否则修改
                    if ("N".equals(userList1.get(0).getBack4())) {
                        saveUser.setEnableCount(userList1.get(0).getEnableCount() - 1);
                        userMapper.updateByExampleSelective(saveUser, userExample1);
                    }
                }
            }
            result.put("status", 0);
            result.put("msg", "success");
            result.put("data", avgPriceInt);
            result.put("recordId", record.getId());
        }else{
            result.put("status", -3);
            result.put("msg", "用户被禁用");
            result.put("data", "");
        }
        return result;
    }
}

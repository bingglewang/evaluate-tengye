package com.spring.mpvue.springbootmpvue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.mpvue.springbootmpvue.mybatis.mapper.UserMapper;
import com.spring.mpvue.springbootmpvue.mybatis.po.User;
import com.spring.mpvue.springbootmpvue.mybatis.po.UserExample;
import com.spring.mpvue.springbootmpvue.util.HttpUtil;
import com.spring.mpvue.springbootmpvue.util.JavaWebToken;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/wx")
@Api(value = "WxController",tags = {"获取用户open_id和session"})
public class WxController {
    private static Logger log = LoggerFactory.getLogger(WxController.class);

    @Autowired
    private UserMapper userMapper;

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;

    @Value("${wx.grantType}")
    private String grantType;
    // wx.grantType=authorization_code

    @Value("${wx.requestUrl}")
    private String requestUrl;
    // wx.requestUrl=https://api.weixin.qq.com/sns/jscode2session

    /**
     * 获取用户open_id并生成token返回给客户端
     * @param code
     * @return
     */
    @RequestMapping("/session")
    @ResponseBody
    @ApiOperation(value = "获取用户open_id",httpMethod = "POST",produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取用户登录id"),
            @ApiResponse(code = 500, message = "500(系统错误)", response = String.class)
    })
    public Map<String, Object> getSession(
            @ApiParam(value = "登录code",required = true)
            @RequestParam(required = true) String code
    ) {
        return this.getSessionByCode(code);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getSessionByCode(String code) {
        String url = this.requestUrl + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type="
                + grantType;
        // 发送请求
        String data = HttpUtil.get(url);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> json = null;
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            json = mapper.readValue(data, Map.class);
            Map<String,Object> m = new HashMap<String,Object>();
            if(json.get("openid") != null){
                m.put("openid",json.get("openid"));
                String token = JavaWebToken.createJavaWebToken(m);
                result.put("token",token);
            }else{
                result.put("token","");
            }
            /*JSONObject json1 =  mapper.readValue(data,JSONObject.class);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 形如{"session_key":"6w7Br3JsRQzBiGZwvlZAiA==","openid":"oQO565cXXXXXEvc4Q_YChUE8PqB60Y"}的字符串
        return result;
    }
}

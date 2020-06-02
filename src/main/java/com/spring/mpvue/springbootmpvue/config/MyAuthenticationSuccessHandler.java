package com.spring.mpvue.springbootmpvue.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.mpvue.springbootmpvue.mybatis.po.SysUser;
import com.spring.mpvue.springbootmpvue.util.HttpUtil;
import com.spring.mpvue.springbootmpvue.util.JavaWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xh on 2018/12/20 11:01.
 *
 * @author wy
 */
@Component("MyAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${fj.username}")
    private String username;

    @Value("${fj.password}")
    private String password;

    @Value("${fj.appKey}")
    private String appKey;

    @Value("${fj.requestUrl}")
    private String requestUrl;

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功");
        resp.setContentType("application/json;charset=UTF-8");
        //生成token
        SecurityUser sysUser = (SecurityUser)authentication.getPrincipal();
        String username = sysUser.getUsername();
        Map<String, Object> m = new HashMap<String, Object>();
        String tokenHouse = getToken();
        m.put("loginId",sysUser.getUserId());
        m.put("username",username);
        m.put("name",sysUser.getName());
        m.put("token",tokenHouse);
        String token = JavaWebToken.createJavaWebToken(m);
        ResponseData rd = new ResponseData(200, "登陆成功", token);
        resp.getWriter().write(objectMapper.writeValueAsString(rd));
    }

    /**
     * 获取房价接口token
     * @return
     */
    public String getToken(){
        String url = this.requestUrl + "?username=" + username + "&password=" + password + "&appKey=" + appKey;
        // 发送请求
        String data = HttpUtil.get(url);
        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        try {
            JsonNode jsonNode = mapper.readTree(data);
            if(Integer.parseInt(jsonNode.get("code").toString()) == 200){
                result = (jsonNode.get("result").get("token")).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}

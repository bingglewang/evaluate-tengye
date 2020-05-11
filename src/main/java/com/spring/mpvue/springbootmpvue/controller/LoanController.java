package com.spring.mpvue.springbootmpvue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.mpvue.springbootmpvue.util.HttpUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LoanController
 * @Description TODO
 * @Author binggleW
 * @Date 2020-05-11 17:11
 * @Version 1.0
 **/
@RestController
@RequestMapping("loan")
public class LoanController {
    /**
     *  推荐产品
     * @return
     */
    @PostMapping("/preferProduct")
    public Map<String, Object> preferProduct(){
        String url = "https://srt1314.com/srt_gzh/loanProductServlet";
        Map param = new HashMap();
        param.put("action","queryAiRecommendProducts");
        String data = HttpUtil.post(url,param);
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
     *  列表产品
     * @return
     */
    @PostMapping("/listProduct")
    public Map<String, Object> listProduct(String prod_fl){
        String url = "https://srt1314.com/srt_gzh/srtCenterServlet";
        Map param = new HashMap();
        param.put("action","queryProductsCategory");
        param.put("xzqhDm","");
        param.put("prod_fl",prod_fl);
        String data = HttpUtil.post(url,param);
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
     *  详情
     * @return
     */
    @PostMapping("/detail")
    public Map<String, Object> detail(String prodUuid){
        String url = "https://srt1314.com/srt_gzh/loanProductServlet";
        Map param = new HashMap();
        param.put("action","queryProdDetails");
        param.put("prodUuid",prodUuid);
        String data = HttpUtil.post(url,param);
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

}

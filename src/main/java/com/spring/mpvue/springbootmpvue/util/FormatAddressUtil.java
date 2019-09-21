package com.spring.mpvue.springbootmpvue.util;

import com.alibaba.fastjson.JSON;

/**
 * 利用高德web服务将地址格式化
 * #高德地图地址解析
 * amp:
 *   apiUrl: https://restapi.amap.com/v3/geocode/geo
 *   apiKey: 1441ec32b0c5bb5923d5d5c6c1f37064
 */
public class FormatAddressUtil {

    private  static  String apiUrl = "https://restapi.amap.com/v3/geocode/geo";

    private  static String apiKey = "2fdb15fef74a26bfdd9d3f5d9691276b";

    private  static String city = "清远市";

    public  static String formatAddress(String oldAddress){
        String url = FormatAddressUtil.apiUrl + "?key=" + apiKey + "&address="+oldAddress+"&city="+city;
        // 发送请求
        String data = HttpUtil.get(url);
        System.out.println("请求数据："+data);
        String result = "默认没有数据";
        if(JSON.parseObject(data).getJSONArray("geocodes").toString().length() > 2) {
            result = JSON.parseObject(data).getJSONArray("geocodes").getJSONObject(0).getString("district");
            System.out.println("所属区域：" + result);
        }
        return result;
    }

   /* public static void main(String args[]){
        formatAddress("恒丰");
    }
*/
}

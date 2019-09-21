package com.spring.mpvue.springbootmpvue.util;

import com.alibaba.fastjson.JSONObject;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class WebUtil {
  /*  public static void main(String[] args) {
        Entrust entrust = new Entrust();
        entrust.setCklxr("2");
        entrust.setDelegateType("1");
        entrust.setDkyh("2");
        entrust.setFromUserName("999");
        entrust.setKeepField1("2");
        entrust.setKeepField2("7");
        entrust.setMail("you");
        entrust.setPhone("1235666");
        entrust.setProjectName("xiangmu");
        entrust.setRemark("haha");
        entrust.setReportOrPreviews(1);
        entrust.setReportType("3");
        entrust.setSalesman("王冰柜:13227355241");
        entrust.setToUserName("hah");
        entrust.setUserName("jjjj");
        entrust.setWxCompany("haha");

        String jsonStr =  JSONObject.toJSONString(entrust);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("json_data", jsonStr);
        int resultCode =  parseXML1(creatPostAndTransData1(dataMap));
        System.out.println("结果:"+resultCode);
       *//* Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("keyParam", "");
        List<ZtsmDzUser> result =  parseXML(creatPostAndTransData(dataMap));
        System.out.println("结果："+result);*//*

    }*/

    public static String creatPostAndTransData(Map<String, Object> dataMap) {
        String urlStr = "http://170i6n7387.imwork.net:81/Hy/SubModule/ZtsmService.asmx";
        String line = "";
        StringBuffer resultSting = new StringBuffer();
        try {
            //urlStr,写在了配置文件中,直接进行获取
            //声明URL
            URL url = new URL(urlStr);
            //1.创建链接
            URLConnection con = url.openConnection();
            //2.封装报文传输进行传输
            //调用getXmlInfo(Map<String, Object> dataMap)进行报文的封装
            String xmlInfo = getXmlInfo(dataMap);
            byte[] xmlData = xmlInfo.getBytes();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");
            con.setRequestProperty("Content-length", String.valueOf(xmlData.length));
            OutputStreamWriter out = new OutputStreamWriter(
                    con.getOutputStream());
            System.out.println(">>>>>>>>>>接口地址为urlStr=" + urlStr);
            System.out.println(">>>>>>>>>>传入的报文xmlInfo=" + xmlInfo);
            out.write(new String(xmlInfo.getBytes()));
            out.flush();
            out.close();
            //3.获取返回报文
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    con.getInputStream(),"UTF-8"));
            //对返回值报文进行打印
            for (line = br.readLine(); line != null; line = br.readLine()) {
                //对返回的报文进行结果判断<RspCode>0000</RspCode>
                System.out.println(">>>>>>>>>>>>>>>>>>>返回的结果报文内容为:---------" + line);
                //对返回的报文进行拼接,然后返回给业务层,在业务层进行判断
                resultSting.append(line);
            }
            return resultSting.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultSting.toString();
    }

    public static String creatPostAndTransData1(Map<String, Object> dataMap) {
        String urlStr = "http://www.changemind.cn/wx/EntrustService.asmx";
        String line = "";
        StringBuffer resultSting = new StringBuffer();
        try {
            //urlStr,写在了配置文件中,直接进行获取
            //声明URL
            URL url = new URL(urlStr);
            //1.创建链接
            URLConnection con = url.openConnection();
            //2.封装报文传输进行传输
            //调用getXmlInfo(Map<String, Object> dataMap)进行报文的封装
            String xmlInfo = getXmlInfo2(dataMap);
            byte[] xmlData = xmlInfo.getBytes();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Accept-Charset", "UTF-8");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");
            con.setRequestProperty("Content-length", String.valueOf(xmlData.length));
            OutputStreamWriter out = new OutputStreamWriter(
                    con.getOutputStream());
            System.out.println(">>>>>>>>>>接口地址为urlStr=" + urlStr);
            System.out.println(">>>>>>>>>>传入的报文xmlInfo=" + xmlInfo);
            out.write(new String(xmlInfo.getBytes()));
            out.flush();
            out.close();
            //3.获取返回报文
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    con.getInputStream(),"UTF-8"));
            //对返回值报文进行打印
            for (line = br.readLine(); line != null; line = br.readLine()) {
                //对返回的报文进行结果判断<RspCode>0000</RspCode>
                System.out.println(">>>>>>>>>>>>>>>>>>>返回的结果报文内容为:---------" + line);
                //对返回的报文进行拼接,然后返回给业务层,在业务层进行判断
                resultSting.append(line);
            }
            return resultSting.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultSting.toString();
    }

    public static String getXmlInfo(Map<String, Object> dataMap) {
        String key = dataMap.get("keyParam").toString();
        // TODO 动态传入参数
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        sb.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        sb.append("<soap:Body>");
        sb.append(" <GetZtsmList xmlns=\"http://tempuri.org/\">");
        sb.append("<xmdhOnCpbh>");
        sb.append(key);
        sb.append("</xmdhOnCpbh>");
        sb.append(" </GetZtsmList>");
        sb.append("</soap:Body>");
        sb.append("</soap:Envelope>");
        return sb.toString();
    }

    public static String getXmlInfo2(Map<String, Object> dataMap) {
        String json_data = dataMap.get("json_data").toString();
        try {
            json_data = Base64.encode(json_data.getBytes("utf-8"));
        }catch (Exception e){
        }

        System.out.println("转换后："+json_data);
        // TODO 动态传入参数
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        sb.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        sb.append("<soap:Body>");
        sb.append(" <AddEntrust xmlns=\"http://tempuri.org/\">");
        sb.append("<json_data>");
        sb.append(json_data);
        sb.append("</json_data>");
        sb.append("<isSMS>");
        sb.append(1);
        sb.append("</isSMS>");
        sb.append("<isNodejs>");
        sb.append(1);
        sb.append("</isNodejs>");
        sb.append(" </AddEntrust>");
        sb.append("</soap:Body>");
        sb.append("</soap:Envelope>");
        return sb.toString();
    }

    public static List<ZtsmDzUser> parseXML(String Data) {
        JSONObject jsonObject = null;
        List<ZtsmDzUser> ztsmDzUserList = null;
        ZtsmDzUser ztsmDzUser = null;
        try {
            org.dom4j.Document document = DocumentHelper.parseText(Data);
            Element bookstore = document.getRootElement();
            Iterator storeit = bookstore.elementIterator();
            ztsmDzUserList = new ArrayList<ZtsmDzUser>();
            if (storeit.hasNext()) {
                Element bookElement = (Element) storeit.next();
                //遍历bookElement的属性
                Iterator bookit = bookElement.elementIterator();
                if(bookit.hasNext()){
                    Element child1 = (Element) bookit.next();
                    Iterator iterator = child1.elementIterator();
                    if(iterator.hasNext()){
                        Element child2 = (Element) iterator.next();
                        List<Element> jdjd11 = child2.elements();
                        for(Element child3 : jdjd11){
                            ztsmDzUser = new ZtsmDzUser();
                            List<Element> jdjd = child3.elements();
                            for(Element child : jdjd){
                                String nodeName = child.getName();
                                if(nodeName.equals("Address")){
                                    String Address = child.getStringValue();
                                    ztsmDzUser.setAddress(Address);
                                }else if(nodeName.equals("UserName")){
                                    String UserName = child.getStringValue();
                                    ztsmDzUser.setUserName(UserName);
                                }else if(nodeName.equals("ztsmName")){
                                    String ztsmName = child.getStringValue();
                                    ztsmDzUser.setZtsmName(ztsmName);
                                }else if(nodeName.equals("tjsjDatetime")){
                                    String tjsjDatetime = child.getStringValue();
                                    ztsmDzUser.setTjsjDatetime(dealDateFormat(tjsjDatetime));
                                }
                            }
                            ztsmDzUserList.add(ztsmDzUser);
                            ztsmDzUser = null;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return ztsmDzUserList;
    }


    public static int parseXML1(String Data) {
        JSONObject jsonObject = null;
        try {
            org.dom4j.Document document = DocumentHelper.parseText(Data);
            Element bookstore = document.getRootElement();
            Iterator storeit = bookstore.elementIterator();
            if (storeit.hasNext()) {
                Element bookElement = (Element) storeit.next();
                //遍历bookElement的属性
                Iterator bookit = bookElement.elementIterator();
                if(bookit.hasNext()){
                    Element child1 = (Element) bookit.next();
                    Iterator iterator = child1.elementIterator();
                    if(iterator.hasNext()){
                        Element child2 = (Element) iterator.next();
                        String result = child2.getStringValue();
                        result = result.substring(0,2);
                        if("ok".equals(result)){
                            return 1;
                        }else{
                            return -1;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return -2;
    }



    public static Date dealDateFormat(String oldDateStr) throws ParseException {
        //此格式只有  jdk 1.7才支持  yyyy-MM-dd'T'HH:mm:ss.SSSXXX
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date  date = df.parse(oldDateStr);
        SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
        Date date1 =  df1.parse(date.toString());
        // DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return date1;
    }
}


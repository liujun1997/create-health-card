package com.liujun.createhealthcard.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * HttpUtils class
 *
 * @author LiuJun
 * @date 2020-07-05
 */
@Log4j2
public class HttpUtils {
    private static CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    private static String authTokenString = "";
    private static String url = "http://sc.ftqq.com/SCU69521T80bc11fbc828b0b16793f14e2f5129c55dfa0ea8a9741.send";

    public static String sendMessage(String text,String desp){
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append("text="+ URLEncoder.encode(text,"utf-8"));
            stringBuffer.append("&desp="+URLEncoder.encode(desp,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String getResponse = createGetResponse(url, stringBuffer, null);
        return getResponse;

    }

    /**
     * 发送get请求
     * @param url
     * @return 响应的字符串
     */
    public static  String createGetResponse(String url, StringBuffer parm,String authToken) {
        //创建httpclinet对象
        HttpGet httpGet = null;
        if (parm != null){
            httpGet = new HttpGet(url+ "?" + parm);
        }else {
             httpGet = new HttpGet(url);
        }
        httpGet.setHeader("Content-type","application/x-www-form-urlencoded");
        return getResponseString(httpGet,  authToken);
    }

    /**
     *
     * @param url
     * @param parm
     * @return
     */
    public static  <T> String createPostResponse(String url, StringBuilder parm, T t,String authToken) {
        HttpPost httpPost = null;
        //设置响应对象
        if (parm != null){
            httpPost = new HttpPost(url+ "?" + parm);
            log.debug(url+ "?" + parm);
        }else {
            httpPost = new HttpPost(url);
        }

        //给对象设置参数
        if (t != null){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String obj = objectMapper.writeValueAsString(t);
                StringEntity stringEntity = new StringEntity(obj, "utf-8");
                System.out.println(stringEntity);
                stringEntity.setContentEncoding("UTF-8");
                stringEntity.setContentType("application/json");
                httpPost.setEntity(stringEntity);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        //设置设置时间
        return getResponseString(httpPost, authToken);
    }

    /**
     * 通用的方法
     * @param httpRponse 继承对象
     * @param <T> HttpGet HttpGet
     * @return 响应的字符串
     */
    public static <T extends HttpRequestBase> String getResponseString(T httpRponse ,String authToken){
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();
        httpRponse.setConfig(requestConfig);
        httpRponse.setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 10; Redmi Note 7 Build/QKQ1.190910.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/81.0.4044.138 Mobile Safari/537.36");
        httpRponse.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        if (authToken != null ){
            httpRponse.setHeader("X-Auth-Token",authToken);
        }
        if (!"".equals(authTokenString)){
            httpRponse.setHeader("X-Auth-Token",authTokenString);
        }
        CloseableHttpResponse response = null;
        try {
            //发送请求
            response = httpClient.execute(httpRponse);
            if (response.getStatusLine().getStatusCode() == 200) {
                if (response.getEntity() == null) {
                    throw new RuntimeException("响应为空");
                }
                Header authCode = response.getFirstHeader("X-Auth-Token");
                if (authCode != null){
                    authTokenString = authCode.getValue();
                    System.out.println(authTokenString);
                }else {
                    authTokenString = "";
                }
                return EntityUtils.toString(response.getEntity(), "utf-8");
            }
            throw new RuntimeException("请求不正确");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }



}

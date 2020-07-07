package com.liujun.createhealthcard.service;

import com.liujun.createhealthcard.entity.User;
import com.liujun.createhealthcard.utils.HttpUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * CreateHealthCardService class
 *
 * @author LiuJun
 * @date 2020-07-05
 */
public class CreateHealthCardService {
    public void createHealthCardService(User user){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("username="+ URLEncoder.encode(user.getUsername(),"utf-8"))
                    .append("&password="+URLEncoder.encode(user.getPassword(),"utf-8"));

            String postResponse = HttpUtils.createPostResponse("http://218.104.196.10:8848/api/auth/login", stringBuilder, null,null);
            System.out.println(postResponse);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

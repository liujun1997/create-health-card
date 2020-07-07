package com.liujun.createhealthcard.controller;

import com.liujun.createhealthcard.entity.User;
import com.liujun.createhealthcard.service.TaskServer;
import com.liujun.createhealthcard.utils.HttpUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * newUserController class
 *
 * @author LiuJun
 * @date 2020-07-06
 */
@RestController
public class newUserController {

    @GetMapping("/addUser")
    public HashMap<String,String> addUser (User user){
        HashMap<String, String> map = new HashMap<>();
        for (User user1 : TaskServer.userList) {
            if (user1.getUsername().equals(user.getUsername())){
                map.put("data","已经存在");
                return map;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("username="+ URLEncoder.encode(user.getUsername(),"utf-8"))
                    .append("&password="+URLEncoder.encode(user.getPassword(),"utf-8"));
            String postResponse = HttpUtils.createPostResponse("http://218.104.196.10:8848/api/auth/login", stringBuilder, null,null);
            System.out.println(postResponse);
            if (postResponse == null){
                map.put("data","登录信息错误");
            }else {
                map.put("data","ok");
            }
        } catch (UnsupportedEncodingException e) {
            map.put("data","登录信息错误");
            e.printStackTrace();
        }
        return map;
    }

    @GetMapping("queryResult")
    public String queryResult(String username){
        HashMap<String, String> userMap = TaskServer.userMap;
        String result = userMap.get(username);
        return result;
    }
}

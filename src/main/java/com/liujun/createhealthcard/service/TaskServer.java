package com.liujun.createhealthcard.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liujun.createhealthcard.entity.Header;
import com.liujun.createhealthcard.entity.HeathCard;
import com.liujun.createhealthcard.entity.User;
import com.liujun.createhealthcard.utils.HttpUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * TaskServer class
 *
 * @author LiuJun
 * @date 2020-07-05
 */
@Component
public class TaskServer {

    public static List<User> userList = new ArrayList<>();
    public static HashMap<String ,String> userMap = new HashMap<>();

    static {
        String[] userNameList = {"赵立彤","刘军","梁超升","吴康佳","沙洲","张宇新","毛骏龙","何亮","郝英利","张碧媛","何亮","郭超"};
        addUser(userNameList);
    }

    @Scheduled(cron = "0 0 9 * * ? ")
    public void taskCreateCard(){
        StringBuilder successStringBuilder = new StringBuilder();
        successStringBuilder.append("成功打卡人员名单: ");
        StringBuilder failureStringBuilder = new StringBuilder();
        failureStringBuilder.append("打卡失败人员名单: ");

        userList.forEach(user -> {
            CreateHealthCardService cardService = new CreateHealthCardService();
            cardService.createHealthCardService(user);

            //生成温度随机数
            Random random = new Random();
            String temp = "36." + (1 + random.nextInt(5));
            HeathCard heathCard = new HeathCard(temp);
            String postResponse = HttpUtils.createPostResponse("http://218.104.196.10:8848/api/wa/create/health", null, heathCard, null);

            Header header = null;
            try {
                header = new ObjectMapper().readValue(postResponse, Header.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            System.out.println(header);
            if (header != null && "200".equals(header.getCode())){
                successStringBuilder.append(user.getUsername()+ ":" + temp + " ");
            }else {
                failureStringBuilder.append(user.getUsername()+" ");
            }
        });

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String time = sdf.format(date);
        successStringBuilder.append(" ").append(failureStringBuilder);
        HttpUtils.sendMessage( "打卡名单 " + time,successStringBuilder.toString());
    }

    private static void addUser(String[] userNameList){
        for (String userName : userNameList) {
            User user = new User();
            user.setUsername(userName);
            userList.add(user);
        }
    }

}

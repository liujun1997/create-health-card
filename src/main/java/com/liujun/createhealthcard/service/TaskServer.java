package com.liujun.createhealthcard.service;

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
        User user = new User();
        user.setUsername("刘军");
        user.setPassword("123");
        userList.add(user);
        User lUser = new User();
        lUser.setUsername("梁超升");
        lUser.setPassword("123");
        userList.add(lUser);
        User wUser = new User();
        lUser.setUsername("吴康佳");
        lUser.setPassword("123");
        userList.add(wUser);
    }
    @Scheduled(cron = "0 0 8 * * ? ")
    public void taskCreateCard(){
        userList.forEach(user -> {
            CreateHealthCardService cardService = new CreateHealthCardService();
            cardService.createHealthCardService(user);

            //生成温度随机数
            Random random = new Random();
            String temp = "36." + (1 + random.nextInt(5));
            HeathCard heathCard = new HeathCard(temp);
            String postResponse = HttpUtils.createPostResponse("http://218.104.196.10:8848/api/wa/create/health", null, heathCard, null);
            System.out.println(postResponse);
            Date date = new Date();
            int day = date.getDate();
            int mouth = date.getMonth() + 1;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            String time = sdf.format(date);
            HttpUtils.sendMessage(user.getUsername() + "打卡成功" + time + " " + temp + " "+ postResponse);
            userMap.put(user.getUsername() + mouth + day,postResponse);
        });

    }

}

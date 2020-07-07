package com.liujun.createhealthcard;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


class CreateHealthCardApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    public void test(){
        Date date = new Date();
        int day = date.getDate();
        int mouth = date.getMonth() + 1;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String time = sdf.format(date);
        System.out.println("time = " + time);
    }
}
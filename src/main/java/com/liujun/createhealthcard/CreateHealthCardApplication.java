package com.liujun.createhealthcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CreateHealthCardApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreateHealthCardApplication.class, args);
    }

}

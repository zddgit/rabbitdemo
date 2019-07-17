package com.zdd.rabbitdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RabbitdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitdemoApplication.class, args);
    }

}

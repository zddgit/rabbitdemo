package com.zdd.rabbitdemo.springamqp;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Tut1Receiver {

    @RabbitListener(queues = "hello")
    @RabbitHandler
    public void recv(String in){
        System.out.println(" [x] Received '" + in + "'");
    }
}

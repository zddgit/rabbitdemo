package com.zdd.rabbitdemo.springamqp;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//@Profile("test")
@Configuration
public class Tut1Config {
    @Bean
    public Queue hello(){
        return new Queue("hello");
    }
//    @Bean
//    public Queue test(){
//        return new Queue("test");
//    }

    @Bean
    public Tut1Receiver receiver() {
        return new Tut1Receiver();
    }

    @Bean
    public Tut1Sender sender() {
        return new Tut1Sender();
    }
}

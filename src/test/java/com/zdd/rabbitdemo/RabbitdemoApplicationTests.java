package com.zdd.rabbitdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitdemoApplicationTests {

    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testRabbitAdmin(){
        rabbitAdmin.declareExchange(new DirectExchange("test.direct.exchange",false,false));
        rabbitAdmin.declareExchange(new TopicExchange("test.topic.exchange",false,false));
        // 删除如果返回true，则说明创建成功
        //Assert.assertTrue(rabbitAdmin.deleteExchange("test.direct"));
        //Assert.assertTrue(rabbitAdmin.deleteExchange("test.topic"));
        rabbitAdmin.declareQueue(new Queue("test.direct.queue",false));
        rabbitAdmin.declareQueue(new Queue("test.topic.queue",false));


        rabbitAdmin.declareBinding(new Binding(
                "test.direct.queue",  //要绑定的队列
                Binding.DestinationType.QUEUE,   //绑定的类型
                "test.direct.exchange",//绑定的交换机
                "directKey",          //配置路由key
                new HashMap<>()));

        //另外一种方式声明
        rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("test.topic.queue",false)) //可以在这里直接创建队列(上面的队列申明可以不要)
                .to(new TopicExchange("test.topic.exchange",false,false)) //可以在这里创建Exchange
                .with("user.#")); //指定路由键

        //清空队列
//        rabbitAdmin.purgeQueue("test.direct.queue",false);

    }

    @Test
    public void testSendMessage(){
        //创建消息
        MessageProperties messageProperties = new MessageProperties();
        //添加两个自定义属性
        messageProperties.getHeaders().put("desc","信息描述");
        messageProperties.getHeaders().put("type","自定义消息类型");
        Message message = new Message("Hello RabbitMQ".getBytes(),messageProperties);
        //发送消息
        rabbitTemplate.convertAndSend("test.topic.exchange","user.test",message,new MessagePostProcessor(){

            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                System.out.println("添加额外设置");
                message.getMessageProperties().getHeaders().put("test","自定义新值");
                return message;
            }
        });

    }

}

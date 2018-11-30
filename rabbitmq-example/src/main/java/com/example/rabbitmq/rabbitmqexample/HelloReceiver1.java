package com.example.rabbitmq.rabbitmqexample;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "neo")
public class HelloReceiver1 {

    @RabbitHandler
    public void process(String neomsg) {
        System.out.println("Receiver1 : " + neomsg);
    }
}

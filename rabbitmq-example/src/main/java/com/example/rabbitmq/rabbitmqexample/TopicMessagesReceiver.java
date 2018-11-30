package com.example.rabbitmq.rabbitmqexample;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.messages")
public class TopicMessagesReceiver {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver messages: " + hello);
    }
}

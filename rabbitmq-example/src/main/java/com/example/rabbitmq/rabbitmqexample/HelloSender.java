package com.example.rabbitmq.rabbitmqexample;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HelloSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send () {
        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        rabbitTemplate.convertAndSend("hello", context);
    }
}

package com.example.rabbitmq.rabbitmqexample;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send () {
        User user = new User();
        user.setName("liangwang");
        user.setNum(1);

        rabbitTemplate.convertAndSend("object", user);
    }
}

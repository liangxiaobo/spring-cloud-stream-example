package com.example.rabbitmq.rabbitmqexample;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NeoSender2 {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send (int i) {
        String context = "spring boot neo queue ********" + i;
//        System.out.println("neoSender2 : " + context);
        rabbitTemplate.convertAndSend("neo", context);
    }
}

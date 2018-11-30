package com.example.rabbitmq.rabbitmqexample;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send1 () {
       String context = "hi , i am message 1";

        rabbitTemplate.convertAndSend("exchange", "topic.message", context);
    }

    public void send2 () {
        String context = "hi , i am messages 2";

        rabbitTemplate.convertAndSend("exchange", "topic.messages", context);
    }
}

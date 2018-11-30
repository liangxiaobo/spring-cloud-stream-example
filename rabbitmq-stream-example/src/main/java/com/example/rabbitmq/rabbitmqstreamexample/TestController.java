package com.example.rabbitmq.rabbitmqstreamexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    Source source;

    @RequestMapping("/send")
    public String send(String name) {
        source.output().send(MessageBuilder.withPayload("send to : " + name).build());

        return "发送成功 " + name;
    }
}

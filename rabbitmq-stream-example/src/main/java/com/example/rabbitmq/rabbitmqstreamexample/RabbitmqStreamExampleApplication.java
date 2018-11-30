package com.example.rabbitmq.rabbitmqstreamexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.handler.annotation.SendTo;

@SpringBootApplication
@EnableBinding(Source.class)
public class RabbitmqStreamExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqStreamExampleApplication.class, args);
    }

}

package com.example.rabbitmq.rabbitmqexample.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitmqConfig {

    @Bean
    public Queue queue() {
        return new Queue("hello");
    }

    @Bean
    public Queue neoQueue(){
        return new Queue("neo");
    }

    @Bean
    public Queue objectQueue() {
        return new Queue("object");
    }
}

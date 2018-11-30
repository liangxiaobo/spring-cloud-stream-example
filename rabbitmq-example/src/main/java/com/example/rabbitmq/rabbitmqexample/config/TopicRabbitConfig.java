package com.example.rabbitmq.rabbitmqexample.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicRabbitConfig {
    final static String message = "topic.message";
    final static String messages = "topic.messages";

    @Bean
    public Queue messageQueue(){
        return new Queue(TopicRabbitConfig.message);
    }

    @Bean
    public Queue messagesQueue(){
        return new Queue(TopicRabbitConfig.messages);
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange("exchange");
    }

    @Bean
    Binding bindingExchangeMessage(Queue messageQueue, TopicExchange exchange) {
        return BindingBuilder.bind(messageQueue).to(exchange).with("topic.message");
    }

    @Bean
    Binding bindingExchangeMessages(Queue messagesQueue, TopicExchange exchange) {
        return BindingBuilder.bind(messagesQueue).to(exchange).with("topic.#");
    }
}

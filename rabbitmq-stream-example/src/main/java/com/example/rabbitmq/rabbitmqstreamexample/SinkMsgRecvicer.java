package com.example.rabbitmq.rabbitmqstreamexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class SinkMsgRecvicer {

    private static Logger logger = LoggerFactory.getLogger(SinkMsgRecvicer.class);

    @StreamListener(Sink.INPUT)
    public void msg(String value) {
        logger.info("Recvicer : {}", value);
    }
}

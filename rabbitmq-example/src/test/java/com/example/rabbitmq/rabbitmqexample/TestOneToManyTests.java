package com.example.rabbitmq.rabbitmqexample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestOneToManyTests {

    @Autowired
    private NeoSender oneSender;
    @Autowired
    private NeoSender2 neoSender2;


    @Test
    public void oneToMany() throws Exception {
        for (int i=0; i<100; i++) {
//            Thread.sleep(1000);
            oneSender.send(i);
            neoSender2.send(i);
        }
    }
}

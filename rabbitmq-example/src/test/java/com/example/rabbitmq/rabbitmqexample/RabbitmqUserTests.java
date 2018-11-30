package com.example.rabbitmq.rabbitmqexample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqUserTests {

    @Autowired
    private UserSender userSender;

    @Test
    public void hello() throws Exception{
        System.out.println("testing start ....... ");

        userSender.send();
    }


}

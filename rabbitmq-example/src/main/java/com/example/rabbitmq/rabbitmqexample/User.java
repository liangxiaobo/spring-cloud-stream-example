package com.example.rabbitmq.rabbitmqexample;

import java.io.Serializable;

public class User implements Serializable {



    private String name;
    private int num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", num=" + num +
                '}';
    }
}

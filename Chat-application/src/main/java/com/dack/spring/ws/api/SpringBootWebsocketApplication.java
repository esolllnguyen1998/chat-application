package com.dack.spring.ws.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootWebsocketApplication {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        var temp = new ChatServer(9000);
        temp.start();
        SpringApplication.run(SpringBootWebsocketApplication.class, args);
    }

}

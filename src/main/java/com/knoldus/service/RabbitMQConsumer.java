package com.knoldus.service;

import com.knoldus.entity.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {
    @RabbitListener(queues = "name-queue")
    public User recievedMessage(User user) {
        System.out.println("Recieved Message From RabbitMQ: " + user);
        return user;
    }
}

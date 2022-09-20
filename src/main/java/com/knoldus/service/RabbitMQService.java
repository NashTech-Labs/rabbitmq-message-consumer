package com.knoldus.service;

import com.knoldus.entity.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RabbitMQService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    private String EXCHANGE="name-exchange";

    private String ROUTING_KEY="names";

    public void sendMessage(User user) {
            amqpTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, user);
    }
}

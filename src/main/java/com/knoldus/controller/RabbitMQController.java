package com.knoldus.controller;

import com.knoldus.entity.User;
import com.knoldus.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQController {
   @Autowired
    private RabbitMQService rabbitMQService;

    @PostMapping("/users")
    public ResponseEntity<String> postMessage(@RequestBody User user){
        rabbitMQService.sendMessage(user);
        return new ResponseEntity<String>("Item pushed to RabbitMQ", HttpStatus.CREATED);
    }
}

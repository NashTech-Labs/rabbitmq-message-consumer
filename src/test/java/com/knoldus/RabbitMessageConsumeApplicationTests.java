package com.knoldus;

import com.knoldus.entity.User;
import com.knoldus.service.RabbitMQConsumer;
import com.knoldus.service.RabbitMQService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.amqp.core.Binding;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class RabbitMessageConsumeApplicationTests {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RabbitAdmin rabbitAdmin;
    @InjectMocks
    private RabbitMQService rabbitMQService;
    @Autowired
    private RabbitMQConsumer rabbitMQConsumer;

    @Autowired
    private AmqpTemplate amqpTemplate;
    private String EXCHANGE = "name-exchange";
    private String QUEUE = "name-queue";
    private Binding queueBinding;

    public void sendMessage() {
        User user = new User();
        user.setName("mohan");
        amqpTemplate.convertAndSend("name-exchange", "names", user);
    }

    @BeforeAll
    public void setup() {
        Queue rabbitQueue = new Queue("name-queue", true);
        rabbitAdmin.declareQueue(rabbitQueue);
        bindToQueue();
    }

    @AfterEach
    public void cleanQueue() {
        rabbitAdmin.purgeQueue("name-queue");
        rabbitAdmin.removeBinding(queueBinding);
    }

    private void bindToQueue() {
        queueBinding = new Binding("name-queue", Binding.DestinationType.QUEUE, "name-exchange", "names", null);
        rabbitAdmin.declareBinding(queueBinding);
    }


    @AfterAll
    public void cleanup() {
        rabbitAdmin.deleteQueue("name-queue");
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testRabbitMessage() {
        User user = new User();
        user.setName("mohan");
        sendMessage();
        User user2 = rabbitMQConsumer.recievedMessage(user);
        Assertions.assertEquals(user.getName(), user2.getName());
    }
}

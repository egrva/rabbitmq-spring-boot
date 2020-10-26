package ru.aegorova.rabbitmqspringboot.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.aegorova.rabbitmqspringboot.models.User;

@RestController
public class RabbitMQController {

    // keys for topic exchange
    private final static String CONFIRMED_TAXES_ROUTING_KEY = "account.confirmed";
    private final static String NOT_CONFIRMED_TAXES_ROUTING_KEY = "account.not_confirmed";

    @Autowired
    private RabbitTemplate template;
    @Autowired
    private ObjectMapper objectMapper;

    // post user's data in fanout exchange
    @PostMapping("/fanout")
    public void sendMessage(@RequestBody User user){
        try {
            template.setExchange("ex_fan");
            template.convertAndSend(objectMapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException();
        }
    }

    // post user's data in direct exchange
    @PostMapping("/direct")
    public void sendConfirmation(@RequestBody User user){
        try {
            template.setExchange("ex_dir");
            template.convertAndSend("confirm", objectMapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException();
        }
    }

    // post user's data in topic exchange
    @PostMapping("/topic")
    public void checkedOrder(@RequestBody User user){
        try {
            template.setExchange("ex_topic");
            String routingKey;
            if (user.getStatus().equals("confirmed")) {
                routingKey = CONFIRMED_TAXES_ROUTING_KEY;
            } else {
                routingKey = NOT_CONFIRMED_TAXES_ROUTING_KEY;
            }
            template.convertAndSend(routingKey, objectMapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException();
        }
    }
}

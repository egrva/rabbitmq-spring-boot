package ru.aegorova.rabbitmqspringboot.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aegorova.rabbitmqspringboot.models.User;
import ru.aegorova.rabbitmqspringboot.services.CreatingEmailService;

@Component
public class RabbitMQDirectListener {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CreatingEmailService creatingEmailService;

    // it listens email_queue by direct exchange
    @RabbitListener(queues = "email_queue")
    public void sendEmail(String message) {
        try {
            User user = objectMapper.readValue(message, User.class);
            creatingEmailService.createEmail(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}

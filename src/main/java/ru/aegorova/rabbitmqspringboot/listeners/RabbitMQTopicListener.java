package ru.aegorova.rabbitmqspringboot.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aegorova.rabbitmqspringboot.models.Key;
import ru.aegorova.rabbitmqspringboot.models.User;
import ru.aegorova.rabbitmqspringboot.services.PDFCreatorService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class RabbitMQTopicListener {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PDFCreatorService pdfCreatorService;

    private final static String TEMPLATE_PATH = "src/main/resources/templates/order.html";
    private final static String PATH_ORDERS_CONFIRMED = "documents/orders/confirmed/";
    private final static String PATH_ORDERS_NOT_CONFIRMED = "documents/orders/not_confirmed/";

    // it listens confirmed_queue by topic exchange
    @RabbitListener(queues = "confirmed_queue")
    public void confirmedOrder(String message) {
        try {
            User user = objectMapper.readValue(message, User.class);
            String fileName = PATH_ORDERS_CONFIRMED + user.getPassport() + "_" + "order_" + UUID.randomUUID() + ".pdf";
            List<Key> keys = new ArrayList<>();
            keys.add(new Key("${status}", user.getStatus()));
            keys.add(new Key("${message}", "Congratulations"));
            pdfCreatorService.formPdf(TEMPLATE_PATH, fileName, keys);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // it listens not_confirmed_queue by topic exchange
    @RabbitListener(queues = "not_confirmed_queue")
    public void notConfirmedOrder(String message) {
        try {
            User user = objectMapper.readValue(message, User.class);
            String fileName = PATH_ORDERS_NOT_CONFIRMED + user.getPassport() + "_" + "order_" + UUID.randomUUID() + ".pdf";
            List<Key> keys = new ArrayList<>();
            keys.add(new Key("${status}", user.getStatus()));
            keys.add(new Key("${message}", "Soory"));
            pdfCreatorService.formPdf(TEMPLATE_PATH, fileName, keys);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

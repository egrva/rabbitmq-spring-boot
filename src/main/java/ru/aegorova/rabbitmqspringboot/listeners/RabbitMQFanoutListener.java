package ru.aegorova.rabbitmqspringboot.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.aegorova.rabbitmqspringboot.models.Key;
import ru.aegorova.rabbitmqspringboot.models.User;
import ru.aegorova.rabbitmqspringboot.services.PDFCreatorService;
import ru.aegorova.rabbitmqspringboot.services.PhotoSaveService;
import ru.aegorova.rabbitmqspringboot.services.StatisticService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class RabbitMQFanoutListener {

    @Autowired
    private PDFCreatorService pdfCreatorService;
    @Autowired
    private PhotoSaveService photoSaveService;
    @Autowired
    private StatisticService statisticService;
    @Autowired
    private ObjectMapper objectMapper;

    private final static String TEMPLATE_PATH = "src/main/resources/templates/report.html";
    private final static String PATH_DOCUMENTS = "documents/";
    private final static String PATH_PASSPORTS = "passports/";
    private final static String PATH_STATISTIC = "statistic/Statistic.xls";

    // it listens report_queue by fanout exchange
    @RabbitListener(queues = "report_queue")
    public void report(String message) {
        try {
            User user = objectMapper.readValue(message, User.class);
            String fileName = PATH_DOCUMENTS + user.getPassport() + "_" + "report_" + UUID.randomUUID() + ".pdf";
            List<Key> keys = new ArrayList<>();
            keys.add(new Key("${name}", user.getName()));
            keys.add(new Key("${surname}", user.getSurname()));
            keys.add(new Key("${passport}", user.getPassport()));
            keys.add(new Key("${age}", user.getAge()));
            keys.add(new Key("${passport_date}", user.getPassport_date()));
            pdfCreatorService.formPdf(TEMPLATE_PATH, fileName, keys);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException();
        }
    }

    // it listens fanout_jpg_download_queue by fanout exchange
    @RabbitListener(queues = "fanout_jpg_download_queue")
    public void jpgDownloader(String message) {
        try {
            User user = objectMapper.readValue(message, User.class);
            photoSaveService.saveImage(user.getPhoto_passport(), PATH_PASSPORTS);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // it listens statistic_queue by fanout exchange
    @RabbitListener(queues = "statistic_queue")
    public void statistic(String message) {
        try {
            User user = objectMapper.readValue(message, User.class);
            statisticService.writeIntoFile(PATH_STATISTIC,user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}

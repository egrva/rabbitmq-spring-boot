package ru.aegorova.rabbitmqspringboot.services;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ru.aegorova.rabbitmqspringboot.models.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Component
public class CreatingEmailServiceImpl implements CreatingEmailService {

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private ExecutorService threadPool;

    @Autowired
    @Qualifier("getFreeMarkerConfiguration")
    private Configuration configuration;

    // we get .txt template and replace key-words with real user's data via freemarker
    // after that we send email in another thread
    @Override
    public void createEmail(User user) {
        Map<String, Object> model = new HashMap<>();
        model.put("confirm", "YOU HAVE TO CONFIRM YOUR ACCOUNT");
        StringBuilder stringBuilder = new StringBuilder();
        try {
            System.out.println("Im here");
            stringBuilder.append(FreeMarkerTemplateUtils
                    .processTemplateIntoString(configuration.getTemplate("confirm.txt"), model));
            System.out.println(stringBuilder.toString());
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }

        threadPool.submit(() -> {
            emailSenderService.sendEmail("Confirmation"
                    , stringBuilder.toString()
                    , user.getEmail());
        });
    }
}

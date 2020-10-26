package ru.aegorova.rabbitmqspringboot;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.aegorova.rabbitmqspringboot.models.Key;
import ru.aegorova.rabbitmqspringboot.models.User;
import ru.aegorova.rabbitmqspringboot.services.PDFCreatorServiceItext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class PdfCreatorTest {
    private final static String TEMPLATE_PATH = "src/main/resources/report.html";
    private final static String TO_PATH = "documents/";

    @Test
    public void createPdf() {
        User user = User.builder()
                .name("Anastasiia")
                .surname("Egorova")
                .passport("1234 123456")
                .age("20")
                .passport_date("16.01.2020")
                .build();
        PDFCreatorServiceItext pdfCreatorServiceItext = new PDFCreatorServiceItext();
        String fileName = TO_PATH + user.getPassport() + "_" + "report_" + UUID.randomUUID() + ".pdf";
        List<Key> keys = new ArrayList<>();
        keys.add(new Key("${name}", user.getName()));
        keys.add(new Key("${surname}", user.getSurname()));
        keys.add(new Key("${passport}", user.getPassport()));
        keys.add(new Key("${age}", user.getAge()));
        keys.add(new Key("${passport_date}", user.getPassport_date()));
        pdfCreatorServiceItext.formPdf(TEMPLATE_PATH, fileName, keys);
    }
}

package ru.aegorova.rabbitmqspringboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String userName;

    // sending email to user's email from email taken from properties-file
    @Override
    public void sendEmail(String subject, String text, String email) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            System.out.println("Im here");
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(text, true);
            messageHelper.setFrom(userName);
        } catch (javax.mail.MessagingException e) {
            throw new IllegalArgumentException(e);
        }
        javaMailSender.send(message);
    }
}

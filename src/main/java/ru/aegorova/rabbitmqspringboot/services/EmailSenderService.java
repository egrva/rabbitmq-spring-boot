package ru.aegorova.rabbitmqspringboot.services;

public interface EmailSenderService {
    public void sendEmail(String subject, String text, String email);
}

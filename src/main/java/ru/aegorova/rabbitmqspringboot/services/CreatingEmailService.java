package ru.aegorova.rabbitmqspringboot.services;

import ru.aegorova.rabbitmqspringboot.models.User;

public interface CreatingEmailService {
    public void createEmail(User user);
}

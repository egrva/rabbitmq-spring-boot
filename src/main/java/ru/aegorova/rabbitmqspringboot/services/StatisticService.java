package ru.aegorova.rabbitmqspringboot.services;

import ru.aegorova.rabbitmqspringboot.models.User;

public interface StatisticService {
    public void writeIntoFile(String path, User user);
}

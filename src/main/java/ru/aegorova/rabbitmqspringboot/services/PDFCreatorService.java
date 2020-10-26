package ru.aegorova.rabbitmqspringboot.services;

import ru.aegorova.rabbitmqspringboot.models.Key;

import java.util.List;

public interface PDFCreatorService {
    public void formPdf(String templatePath, String fileName, List<Key> keys);
}

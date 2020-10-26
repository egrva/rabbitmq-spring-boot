package ru.aegorova.rabbitmqspringboot.services;


public interface PhotoSaveService {
    void saveImage(String imageUrl, String path);
}

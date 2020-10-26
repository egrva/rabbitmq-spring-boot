package ru.aegorova.rabbitmqspringboot.services;


import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

// download image from net by url
// and save it in declared folder
@Component
public class PhotoSaveServiceJpg implements PhotoSaveService {

    public void saveImage(String imageUrl, String path) {
        System.out.println("Start  load image " + imageUrl);
        try {
            // creating url to img file
            URL url = new URL(imageUrl);
            // downloading image
            String fileName = url.getFile();
            BufferedImage image = ImageIO.read(url);
            File output = new File(path + UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf(".")));
            ImageIO.write(image, "jpg", output);
            System.out.println("Finish load image " + imageUrl);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}



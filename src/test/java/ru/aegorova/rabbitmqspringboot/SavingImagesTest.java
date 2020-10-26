package ru.aegorova.rabbitmqspringboot;


import org.junit.jupiter.api.Test;
import ru.aegorova.rabbitmqspringboot.services.PhotoSaveServiceJpg;

public class SavingImagesTest {
    @Test
    public void saveImage() {
        PhotoSaveServiceJpg photoSaveServiceJpg = new PhotoSaveServiceJpg();
        photoSaveServiceJpg.saveImage("https://bosch-club.ru/upload/medialibrary/4aa/4aa68659217441c028c40eada46bbaad.jpg"
                    , "passports/");

    }
}

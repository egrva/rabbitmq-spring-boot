package ru.aegorova.rabbitmqspringboot.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String name;
    private String surname;
    private String passport;
    private String age;
    private String passport_date;
    private String photo_passport;
    private String status;
    private String email;
}

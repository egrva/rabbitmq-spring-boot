package ru.aegorova.rabbitmqspringboot.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
// this model is needed to replace templates with user's data
public class Key {
    private String key;
    private String value;
}

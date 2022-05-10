package com.dementorsun.telegrambot.client.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class RandomDogResponse {

    List<Breed> breeds;
    String url;

    @Getter
    public static class Breed {
        String name;
        String description;
    }
}
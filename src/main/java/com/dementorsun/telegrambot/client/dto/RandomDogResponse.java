package com.dementorsun.telegrambot.client.dto;

import lombok.Getter;

import java.util.List;

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
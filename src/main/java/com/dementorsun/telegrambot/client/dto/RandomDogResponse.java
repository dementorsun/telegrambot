package com.dementorsun.telegrambot.client.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RandomDogResponse {

    private List<Breed> breeds;
    private String url;

    @Getter
    public static class Breed {
        private String name;
        private String description;
    }
}
package com.dementorsun.telegrambot.client.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RandomCatResponse {

    private List<Breed> breeds;
    private String url;

    @Getter
    public static class Breed {
        private String name;
        private String description;
    }
}
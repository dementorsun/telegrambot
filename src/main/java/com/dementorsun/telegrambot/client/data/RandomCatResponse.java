package com.dementorsun.telegrambot.client.data;

import lombok.Getter;

import java.util.List;

@Getter
public class RandomCatResponse {

    List<Breed> breeds;
    String url;

    @Getter
    public static class Breed {
        String name;
        String description;
    }
}
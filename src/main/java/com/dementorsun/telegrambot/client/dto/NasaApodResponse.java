package com.dementorsun.telegrambot.client.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class NasaApodResponse {

    String explanation;
    String title;
    String url;
}
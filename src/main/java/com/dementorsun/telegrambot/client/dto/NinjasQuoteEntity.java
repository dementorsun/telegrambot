package com.dementorsun.telegrambot.client.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NinjasQuoteEntity {

    String quote;
    String author;
}
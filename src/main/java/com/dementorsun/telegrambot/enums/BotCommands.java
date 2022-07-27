package com.dementorsun.telegrambot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BotCommands {

    START("/start"),
    CHANGE_TOPICS("/change_topics"),
    CHANGE_TIME("/change_time"),
    SILENCE_MODE("/stop_spam");

    private final String command;
}
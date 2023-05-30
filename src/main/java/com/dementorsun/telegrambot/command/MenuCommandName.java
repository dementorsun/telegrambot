package com.dementorsun.telegrambot.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration for {@link MenuCommand}'s.
 */

@RequiredArgsConstructor
@Getter
public enum MenuCommandName {

    START_COMMAND("/start"),
    CHANGE_TOPICS_COMMAND("/change_topics"),
    CHANGE_TIME_COMMAND("/change_time"),
    SILENCE_MODE_COMMAND("/stop_spam");

    private final String commandName;
}
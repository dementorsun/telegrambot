package com.dementorsun.telegrambot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BottomButtonsDict {

    CHANGE_TOPICS_BUTTON("Змінити свої топіки \uD83E\uDDF3"),
    CHANGE_TIME_BUTTON("Змінити час ⏳"),
    SILENCE_MODE_BUTTON("Зупинити цей спам ❌");

    private final String buttonText;
}
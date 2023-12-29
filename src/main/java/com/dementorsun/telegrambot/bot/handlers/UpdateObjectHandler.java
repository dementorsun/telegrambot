package com.dementorsun.telegrambot.bot.handlers;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.Update;

@UtilityClass
public class UpdateObjectHandler {

    public static long getChatIdFromUpdate(Update update) {
        return update.getMessage().getChatId();
    }
}
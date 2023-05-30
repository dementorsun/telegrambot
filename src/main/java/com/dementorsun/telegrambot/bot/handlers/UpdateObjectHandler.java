package com.dementorsun.telegrambot.bot.handlers;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.Update;

@UtilityClass
public class UpdateObjectHandler {

    public static long getUserIdFromUpdate(Update update) {
        return update.getMessage().getFrom().getId();
    }

    public static long getChatIdFromUpdate(Update update) {
        return update.getMessage().getChatId();
    }

    public static String getMessageTextFromUpdate(Update update) {
        return update.getMessage().getText().trim();
    }

    public static String getCallBackDataFromUpdate(Update update) {
        return update.getCallbackQuery().getData();
    }

    public static long getCallBackUserIdFromUpdate(Update update) {
        return update.getCallbackQuery().getFrom().getId();
    }

    public static long getCallBackChatIdFromUpdate(Update update) {
        return update.getCallbackQuery().getMessage().getChatId();
    }

    public static int getCallBackMessageIdFromUpdate(Update update) {
        return update.getCallbackQuery().getMessage().getMessageId();
    }

    public static String getCallBackInlineMessageIdFromUpdate(Update update) {
        return update.getCallbackQuery().getInlineMessageId();
    }

    public static String getCallBackQueryIdFromUpdate(Update update) {
        return update.getCallbackQuery().getId();
    }
}
package com.dementorsun.telegrambot.bot.handlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class UpdateHandler {

    private final UserActionsHandler userActionsHandler;

    public SendMessage handleUnexpectedAction(Update update) {
        return userActionsHandler.handleUnexpectedAction(update);
    }
}
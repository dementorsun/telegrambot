package com.dementorsun.telegrambot.bot.handlers;

import com.dementorsun.telegrambot.enums.BotMessages;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
@AllArgsConstructor
public class UserActionsHandler {

    private final MessageHandler messageHandler;

    public SendMessage handleUnexpectedAction(Update update) {
        long chatId = UpdateObjectHandler.getChatIdFromUpdate(update);

        return messageHandler.setNewMessageToUser(chatId, BotMessages.UNEXPECTED_ACTION_MESSAGE.getMessage());
    }
}
package com.dementorsun.telegrambot.bot.handlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class UpdateHandler {

    private final UserActionsHandler userActionsHandler;

    public SendMessage handleMessageUpdate(Update update) {
        return userActionsHandler.handleReceivedMessage(update);
    }

    public EditMessageReplyMarkup handleCallBackDataUponTopicButtonClick(Update update) {
        return userActionsHandler.handleTopicButtonClick(update);
    }

    public SendMessage handleCallBackDataUponDoneOrUnexpectedButtonClick(Update update) {
        return userActionsHandler.handleTopicsDoneOrUnexpectedButtonClick(update);
    }
}
package com.dementorsun.telegrambot.bot.handlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class UpdateHandler {

    private final UserActionsHandler userActionsHandler;

    public EditMessageReplyMarkup handleCallBackDataUponTopicButtonClick(Update update) {
        return userActionsHandler.handleTopicButtonClick(update);
    }

    public SendMessage handleCallBackDataUponDoneButtonClick(Update update) {
        return userActionsHandler.handleTopicsDoneButtonClick(update);
    }

    public SendMessage handleUnexpectedAction(Update update) {
        return userActionsHandler.handleUnexpectedAction(update);
    }

    public AnswerCallbackQuery generateAnswerCallBackQuery(Update update) {
        return new AnswerCallbackQuery(UpdateObjectHandler.getCallBackQueryIdFromUpdate(update));
    }
}
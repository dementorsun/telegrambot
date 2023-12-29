package com.dementorsun.telegrambot.topic.button;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Interface for handling bot action after user clicked on topic button.
 */
public interface TopicButton {

    /**
     * Method which return {@link EditMessageReplyMarkup} object to handle specified topic button click.
     * @param userId provides user id for getting topics data from Aerospike.
     * @param isMarked provides identifier about mark state of button.
     * @param update provides general data about topics message.
     * @return {@link EditMessageReplyMarkup} object to send it to TelegramBot API for handling button click.
     */
    EditMessageReplyMarkup handleTopicButtonClick(long userId, boolean isMarked, Update update);
}
package com.dementorsun.telegrambot.topic.button;

import com.dementorsun.telegrambot.db.UserDataHandler;
import com.dementorsun.telegrambot.topic.TopicButtonHandler;
import com.dementorsun.telegrambot.topic.enums.TopicsDict;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Implementation of {@link TopicButton} interface for handling {@link TopicsDict} Nature button.
 */
@Component
@RequiredArgsConstructor
public class NatureTopicButton implements TopicButton{

    private final UserDataHandler userDataHandler;
    private final TopicButtonHandler topicButtonHandler;

    /**
     * Method which return {@link EditMessageReplyMarkup} object to handle {@link TopicsDict} Nature topic button click.
     * @param userId provides user id for getting topics data from Aerospike.
     * @param isMarked provides identifier about mark state of Nature button.
     * @param update provides general data about topics message.
     * @return {@link EditMessageReplyMarkup} object to send it to TelegramBot API for handling Nature button click.
     */
    @Override
    public EditMessageReplyMarkup handleTopicButtonClick(long userId, boolean isMarked, Update update) {
        userDataHandler.setNatureTopicDataForUser(userId, isMarked);

        return topicButtonHandler.editTopicsButtons(userId, update);
    }
}
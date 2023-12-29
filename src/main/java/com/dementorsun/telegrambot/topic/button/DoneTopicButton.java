package com.dementorsun.telegrambot.topic.button;

import com.dementorsun.telegrambot.db.UserDataHandler;
import com.dementorsun.telegrambot.topic.TopicButtonHandler;
import com.dementorsun.telegrambot.topic.enums.TopicsDict;
import com.dementorsun.telegrambot.utilities.SendMessageGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.dementorsun.telegrambot.enums.BotMessages.FINISH_TOPICS_TUTORIAL_MESSAGE;

/**
 * Implementation of {@link TopicButton} interface for handling {@link TopicsDict} Done button.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DoneTopicButton implements TopicButton {

    private final UserDataHandler userDataHandler;
    private final TopicButtonHandler topicButtonHandler;
    private final SendMessageGenerator sendMessageGenerator;

    /**
     * Method handles {@link TopicsDict} Done topic button click.
     * @param userId provides user id for getting topics data from Aerospike.
     * @param isMarked provides identifier about mark state of Done button.
     * @param update provides general data about clicked Done button.
     * @return {@link EditMessageReplyMarkup} object to send it to TelegramBot API for handling Done button click.
     */
    @Override
    public EditMessageReplyMarkup handleTopicButtonClick(long userId, boolean isMarked, Update update) {
        userDataHandler.setDoneButtonClickedDataForUser(userId, isMarked);

        return topicButtonHandler.editTopicsButtons(userId, update);
    }

    /**
     * Method handles Done button click and proceed to next tutorial step.
     * @param update provides general data about clicked Done button.
     * @param userId provides user id for updating data Aerospike.
     * @return {@link SendMessage} object to send it to TelegramBot API for handling tutorial Done button click.
     */
    public SendMessage handleTutorialDoneButtonClick(Update update, long userId) {
        userDataHandler.setDoneButtonClickedDataForUser(userId, true);
        userDataHandler.setTimeEnterModeDataForUser(userId, true);
        userDataHandler.setIsNewUserForUser(userId, false);

        log.info("Time entering tutorial is started for user with id = '{}'", userId);

        return sendMessageGenerator.createSendMessageFromCallBack(update, FINISH_TOPICS_TUTORIAL_MESSAGE.getMessage());
    }
}
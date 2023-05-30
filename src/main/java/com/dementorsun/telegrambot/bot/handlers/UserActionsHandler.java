package com.dementorsun.telegrambot.bot.handlers;

import com.dementorsun.telegrambot.bot.buttons.MessageButtons;
import com.dementorsun.telegrambot.bot.dto.TopicButtonCallBackData;
import com.dementorsun.telegrambot.enums.BotMessages;
import com.dementorsun.telegrambot.db.UserDataHandler;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
@AllArgsConstructor
public class UserActionsHandler {

    private final UserDataHandler userDataHandler;
    private final MessageHandler messageHandler;
    private final MessageButtons messageButtons;
    private final Gson gson;

    public EditMessageReplyMarkup handleTopicButtonClick(Update update) {
        long userId = UpdateObjectHandler.getCallBackUserIdFromUpdate(update);
        TopicButtonCallBackData callBackData = gson.fromJson(UpdateObjectHandler.getCallBackDataFromUpdate(update), TopicButtonCallBackData.class);
        boolean isMarked = !callBackData.getIsMarked();
        EditMessageReplyMarkup editedReplyMessage = generateDefaultEditMessageReplyMarkup(update);

        switch (callBackData.getTopic()) {
            case NASA_TOPIC:
                userDataHandler.setNasaTopicDataForUser(userId, isMarked);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case NATURE_TOPIC:
                userDataHandler.setNatureTopicDataForUser(userId, isMarked);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case ANIMALS_TOPIC:
                userDataHandler.setAnimalsTopicDataForUser(userId, isMarked);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case FOREST_TOPIC:
                userDataHandler.setForestTopicDataForUser(userId, isMarked);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case CAT_TOPIC:
                userDataHandler.setCatTopicDataForUser(userId, isMarked);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case DOG_TOPIC:
                userDataHandler.setDogTopicDataForUser(userId, isMarked);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case POKEMON_TOPIC:
                userDataHandler.setPokemonTopicDataForUser(userId, isMarked);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case MOVIE_TOPIC:
                userDataHandler.setMovieTopicDataForUser(userId, isMarked);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case TV_SHOW_TOPIC:
                userDataHandler.setTvShowTopicDataForUser(userId, isMarked);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case ANIME_TOPIC:
                userDataHandler.setAnimeTopicDataForUser(userId, isMarked);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case QUOTE_TOPIC:
                userDataHandler.setQuoteTopicDataForUser(userId, isMarked);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            default:
                log.info("Incompatible topic from call back data during topic button click for user with id = {}", userId);
        }

        log.info("User with id = {} is clicked on '{}' topic button", userId, callBackData.getTopic().name());

        return editedReplyMessage;
    }

    public SendMessage handleTopicsDoneButtonClick(Update update) {
        long userId = UpdateObjectHandler.getCallBackUserIdFromUpdate(update);
        long chatId = UpdateObjectHandler.getCallBackChatIdFromUpdate(update);
        boolean isNoneTopicsAreChosen = userDataHandler.checkIsUserDoNotHaveActiveTopics(userId);
        boolean isDoneButtonClicked = userDataHandler.getIsDoneButtonClickedForUser(userId);

        SendMessage replyMessage = messageHandler.createDefaultMessageFromUpdateCallBack(chatId);

        if (isNoneTopicsAreChosen) {
            replyMessage = messageHandler.setReplyMessageToUser(replyMessage, BotMessages.TOPIC_DONE_NO_CHOSEN_TOPIC_CLICK.getMessage());

            log.info("User with id = '{}' is clicked on Done topics button without choose any topic", userId);
        } else if (isDoneButtonClicked) {
            replyMessage = messageHandler.setReplyMessageToUser(replyMessage, BotMessages.TOPIC_DONE_REPEAT_CLICK.getMessage());

            log.info("User with id = '{}' is clicked on already clicked Done topics button", userId);
        } else {
            replyMessage = createSendMessageUponProperClickTopicsDoneButton(userId, replyMessage);
        }

        return replyMessage;
    }

    public SendMessage handleUnexpectedAction(Update update) {
        long chatId = UpdateObjectHandler.getChatIdFromUpdate(update);

        return messageHandler.setNewMessageToUser(chatId, BotMessages.UNEXPECTED_ACTION_MESSAGE.getMessage());
    }

    private SendMessage createSendMessageUponProperClickTopicsDoneButton(long userId, SendMessage defaultMessage) {
        SendMessage replyMessage;

        if (userDataHandler.checkTimeIsPresent(userId)) {
            replyMessage = messageHandler.setReplyMessageToUser(defaultMessage, BotMessages.TOPICS_ARE_CHANGED_MESSAGE.getMessage());
            userDataHandler.setDoneButtonClickedDataForUser(userId, true);

            log.info("User with id = '{}' is clicked on Done topics button and topics successfully changed", userId);
        } else {
            userDataHandler.setTimeEnterModeDataForUser(userId, true);
            replyMessage = messageHandler.setReplyMessageToUser(defaultMessage, BotMessages.FINISH_TOPICS_TUTORIAL_MESSAGE.getMessage());
            userDataHandler.setDoneButtonClickedDataForUser(userId, true);
            userDataHandler.setIsNewUserForUser(userId, false);

            log.info("Time entering tutorial is started for user with id = '{}'", userId);
        }

        return replyMessage;
    }

    private EditMessageReplyMarkup generateDefaultEditMessageReplyMarkup(Update update) {
        String inlineMessageId = UpdateObjectHandler.getCallBackInlineMessageIdFromUpdate(update);
        int messageId = UpdateObjectHandler.getCallBackMessageIdFromUpdate(update);
        long chatId = UpdateObjectHandler.getCallBackChatIdFromUpdate(update);

        return EditMessageReplyMarkup.builder()
                .chatId(String.valueOf(chatId))
                .messageId(messageId)
                .inlineMessageId(inlineMessageId)
                .build();
    }
}
package com.dementorsun.telegrambot.bot.handlers;

import com.dementorsun.telegrambot.bot.buttons.BottomButtons;
import com.dementorsun.telegrambot.bot.buttons.MessageButtons;
import com.dementorsun.telegrambot.bot.dto.TopicButtonCallBackData;
import com.dementorsun.telegrambot.enums.BotMessages;
import com.dementorsun.telegrambot.enums.BottomButtonsDict;
import com.dementorsun.telegrambot.db.UserDataHandler;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
@Slf4j
@AllArgsConstructor
class UserActionsHandler {

    private static final String START_MESSAGE = "/start";

    private final UserDataHandler userDataHandler;
    private final MessageHandler messageHandler;
    private final MessageButtons messageButtons;
    private final BottomButtons bottomButtons;
    private final Gson gson;

    public SendMessage handleReceivedMessage(Update update) {
        long userId = UpdateObjectHandler.getUserIdFromUpdate(update);
        long chatId = UpdateObjectHandler.getChatIdFromUpdate(update);
        String message = UpdateObjectHandler.getMessageTextFromUpdate(update);
        User user = UpdateObjectHandler.getMessageUserObjectFromUpdate(update);

        SendMessage replyMessage = messageHandler.createDefaultMessageFromUpdateMessage(chatId);
        log.info("'{}' message is received from user with id = '{}'", message, userId);

        if (START_MESSAGE.equals(message)) {
            replyMessage = createSendMessageForStartTopicsTutorial(userId, chatId, user, replyMessage);
        } else if (userDataHandler.getIsTimeEnterMode(userId)) {
            replyMessage = messageHandler.checkTimeFormatIsCorrect(message) ?
                    createSendMessageForCompleteTimeEntering(userId, message, replyMessage) :
                    messageHandler.setReplyMessageToUser(replyMessage, BotMessages.FAIL_TIME_FORMAT_MESSAGE.getMessage());
        } else if (BottomButtonsDict.CHANGE_TOPICS_BUTTON.getButtonText().equals(message)) {
            boolean isNewUser = userDataHandler.checkIsNewUser(userId);
            replyMessage = createSendMessageForShowTopicsButtons(userId, replyMessage, BotMessages.CHANGE_TOPICS_MESSAGE, isNewUser);
        } else if (BottomButtonsDict.CHANGE_TIME_BUTTON.getButtonText().equals(message)) {
            replyMessage = createSendMessageForChangeUserTime(replyMessage, userId);
        } else if (BottomButtonsDict.SILENCE_MODE_BUTTON.getButtonText().equals(message)) {
            replyMessage = userDataHandler.checkIsSilenceModeActiveForUser(userId) ?
                    messageHandler.setReplyMessageToUser(replyMessage, BotMessages.SILENCE_MODE_IS_ALREADY_ACTIVE_MESSAGE.getMessage()) :
                    createSendMessageForActivateSilenceMode(replyMessage, userId);
        }

        return replyMessage;
    }

    public EditMessageReplyMarkup handleTopicButtonClick(Update update) {
        long userId = UpdateObjectHandler.getCallBackUserIdFromUpdate(update);
        TopicButtonCallBackData callBackData = gson.fromJson(UpdateObjectHandler.getCallBackDataFromUpdate(update), TopicButtonCallBackData.class);
        boolean isMarked = !callBackData.getIsMarked();
        boolean isNewUser = userDataHandler.checkIsNewUser(userId);

        EditMessageReplyMarkup editedReplyMessage = generateDefaultEditMessageReplyMarkup(update);

        switch (callBackData.getTopic()) {
            case NASA_TOPIC:
                userDataHandler.setNasaTopicDataForUser(userId, isMarked);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId, isNewUser);
                break;
            case CAT_TOPIC:
                userDataHandler.setCatTopicDataForUser(userId, isMarked);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId, isNewUser);
                break;
            case DOG_TOPIC:
                userDataHandler.setDogTopicDataForUser(userId, isMarked);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId, isNewUser);
                break;
            case POKEMON_TOPIC:
                userDataHandler.setPokemonTopicDataForUser(userId, isMarked);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId, isNewUser);
                break;
            case MOVIE_TOPIC:
                userDataHandler.setMovieTopicDataForUser(userId, isMarked);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId, isNewUser);
                break;
            case TV_SHOW_TOPIC:
                userDataHandler.setTvShowTopicDataForUser(userId, isMarked);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId, isNewUser);
                break;
            case ANIME_TOPIC:
                userDataHandler.setAnimeTopicDataForUser(userId, isMarked);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId, isNewUser);
                break;
            case QUOTE_TOPIC:
                userDataHandler.setQuoteTopicDataForUser(userId, isMarked);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId, isNewUser);
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

    private SendMessage createSendMessageForStartTopicsTutorial(long userId, long chatId, User user, SendMessage defaultMessage) {
        SendMessage replyMessage;

        if (!userDataHandler.checkIsDataPresentForUser(userId)) {
            userDataHandler.saveNewUserData(user, chatId);
            boolean isNewUser = userDataHandler.checkIsNewUser(userId);
            replyMessage = createSendMessageForShowTopicsButtons(userId, defaultMessage, BotMessages.WELCOME_MESSAGE, isNewUser);

            log.info("Topics buttons are shown during tutorial for user with id = '{}'", userId);
        } else if(userDataHandler.checkIsSilenceModeActiveForUser(userId)) {
            String welcomeBackMessage = String.format(BotMessages.WELCOME_BACK_WITH_SILENCE_MODE_MESSAGE.getMessage(), user.getFirstName());
            replyMessage = messageHandler.setReplyMessageToUser(defaultMessage, welcomeBackMessage);
            bottomButtons.setBottomButtons(replyMessage);

            log.info("Welcome back tutorial with active silence mode is finished for user with id = '{}'", userId);
        } else {
            String welcomeBackMessage = String.format(BotMessages.WELCOME_BACK_MESSAGE.getMessage(), user.getFirstName());
            replyMessage = messageHandler.setReplyMessageToUser(defaultMessage, welcomeBackMessage);
            bottomButtons.setBottomButtons(replyMessage);

            log.info("Welcome back tutorial is finished for user with id = '{}'", userId);
        }

        return replyMessage;
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

    private SendMessage createSendMessageForCompleteTimeEntering(long userId, String message, SendMessage defaultMessage) {
        SendMessage replyMessage;
        userDataHandler.setTimeEnterModeDataForUser(userId, false);

        if (userDataHandler.checkTimeIsPresent(userId)) {
            userDataHandler.setTimeDataForUser(userId, message);
            replyMessage = messageHandler.setReplyMessageToUser(defaultMessage, BotMessages.TIME_ARE_CHANGED_MESSAGE.getMessage());

            log.info("Time changing flow is completed for user with id = '{}'", userId);
        } else {
            userDataHandler.setTimeDataForUser(userId, message);
            bottomButtons.setBottomButtons(defaultMessage);
            replyMessage = messageHandler.setReplyMessageToUser(defaultMessage, BotMessages.COMPLETE_TUTORIAL_MESSAGE.getMessage());

            log.info("Tutorial is completed for user with id = '{}'", userId);
        }

        return replyMessage;
    }

    private SendMessage createSendMessageForShowTopicsButtons(long userId, SendMessage defaultMessage, BotMessages message, boolean isNewUser) {
        SendMessage replyMessage;

        if (userDataHandler.checkIsSilenceModeActiveForUser(userId)) {
            replyMessage = messageHandler.setReplyMessageToUser(defaultMessage, BotMessages.TOPICS_WITH_SILENCE_MODE_MESSAGE.getMessage());

            log.info("Silence mode explanation is shown after click on topics button by user with id = '{}'", userId);
        } else {
            replyMessage = messageHandler.setReplyMessageToUser(defaultMessage, message.getMessage());

            InlineKeyboardMarkup messageButtonsMarkup = messageButtons.setTopicsButtons(userId, isNewUser);
            replyMessage.setReplyMarkup(messageButtonsMarkup);

            userDataHandler.setDoneButtonClickedDataForUser(userId, false);

            log.info("Topics buttons is shown for user with id = '{}'", userId);
        }

        return replyMessage;
    }

    private SendMessage createSendMessageForChangeUserTime(SendMessage defaultMessage, long userId) {
        SendMessage replyMessage;
        userDataHandler.setTimeEnterModeDataForUser(userId, true);

        if (userDataHandler.checkIsSilenceModeActiveForUser(userId)) {
            replyMessage = messageHandler.setReplyMessageToUser(defaultMessage, BotMessages.TIME_CHANGING_WITH_SILENCE_MODE.getMessage());

            log.info("Time changing process with active silence mode is started for user with id = '{}'", userId);
        } else {
            String time = userDataHandler.getUserTime(userId);
            replyMessage = messageHandler.setReplyMessageToUser(defaultMessage, String.format(BotMessages.TIME_CHANGING_MESSAGE.getMessage(), time));

            log.info("Time changing process is started for user with id = '{}'", userId);
        }

        return replyMessage;
    }

    private SendMessage createSendMessageForActivateSilenceMode(SendMessage replyMessage, long userId) {
        userDataHandler.setSilenceModeForUser(userId);

        log.info("Silence mode is activated for user with id = '{}'", userId);

        return messageHandler.setReplyMessageToUser(replyMessage, BotMessages.ACTIVATE_SILENCE_MODE_MESSAGE.getMessage());
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
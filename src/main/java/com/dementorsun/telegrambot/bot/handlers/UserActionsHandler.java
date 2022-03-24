package com.dementorsun.telegrambot.bot.handlers;

import com.dementorsun.telegrambot.bot.buttons.BottomButtons;
import com.dementorsun.telegrambot.bot.buttons.MessageButtons;
import com.dementorsun.telegrambot.bot.data.BotButtons;
import com.dementorsun.telegrambot.bot.data.BotMessages;
import com.dementorsun.telegrambot.db.UserDataHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static com.dementorsun.telegrambot.bot.data.BotButtons.TOPICS_DONE;

@Component
@Slf4j
@AllArgsConstructor
public class UserActionsHandler {

    private final UserDataHandler userDataHandler;
    private final MessageHandler messageHandler;
    private final MessageButtons messageButtons;
    private final BottomButtons bottomButtons;

    public SendMessage handleReceivedMessage(Update update) {
        SendMessage replyMessage = messageHandler.createDefaultMessageFromUpdateMessage(update);
        String message = update.getMessage().getText();
        long userId = update.getMessage().getFrom().getId();

        log.info("'{}' message is received from user with id = '{}'", message, userId);

        if (message.equals("/start")) {
            replyMessage = createSendMessageForStartTopicsTutorial(update, replyMessage);
        } else if (userDataHandler.checkIsTimeEnterMode(userId)) {
            replyMessage = messageHandler.checkTimeFormatIsCorrect(message) ?
                    createSendMessageForCompleteTimeEntering(userId, message, replyMessage) :
                    messageHandler.setMessageToUser(replyMessage, BotMessages.FAIL_TIME_FORMAT_MESSAGE.getMessage(), userId);
        } else if (message.equals("Змінити свої топіки \uD83E\uDDF3")) {
            replyMessage = createSendMessageForShowTopicsButtons(userId, replyMessage, BotMessages.CHANGE_TOPICS_MESSAGE);
        } else if (message.equals("Змінити час ⏳")) {
            replyMessage = changeUserTime(replyMessage, userId);
        }

        return replyMessage;
    }

    public EditMessageReplyMarkup handleTopicButtonClick(Update update) {
        String callBackData = update.getCallbackQuery().getData();
        String inlineMessageId = update.getCallbackQuery().getInlineMessageId();
        long userId = update.getCallbackQuery().getFrom().getId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        EditMessageReplyMarkup editedReplyMessage = EditMessageReplyMarkup.builder()
                                                                          .chatId(String.valueOf(chatId))
                                                                          .messageId((int) messageId)
                                                                          .inlineMessageId(inlineMessageId)
                                                                          .build();

        switch (callBackData) {
            case "NASA_TOPIC":
                userDataHandler.updateNasaTopicData(userId, true);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case "NASA_TOPIC_MARKED":
                userDataHandler.updateNasaTopicData(userId, false);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case "CAT_TOPIC":
                userDataHandler.updateCatTopicData(userId, true);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case "CAT_TOPIC_MARKED":
                userDataHandler.updateCatTopicData(userId, false);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case "DOG_TOPIC":
                userDataHandler.updateDogTopicData(userId, true);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case "DOG_TOPIC_MARKED":
                userDataHandler.updateDogTopicData(userId, false);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case "QUOTE_TOPIC":
                userDataHandler.updateQuoteTopicData(userId, true);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case "QUOTE_TOPIC_MARKED":
                userDataHandler.updateQuoteTopicData(userId, false);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case "MOVIE_TOPIC":
                userDataHandler.updateMovieTopicData(userId, true);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case "MOVIE_TOPIC_MARKED":
                userDataHandler.updateMovieTopicData(userId, false);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case "TV_SHOW_TOPIC":
                userDataHandler.updateTvShowTopicData(userId, true);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
            case "TV_SHOW_TOPIC_MARKED":
                userDataHandler.updateTvShowTopicData(userId, false);
                editedReplyMessage = messageButtons.editTopicsButtons(editedReplyMessage, userId);
                break;
        }

        log.info("User with id = {} is clicked on '{}' topic button", userId, callBackData);

        return editedReplyMessage;
    }


    public SendMessage handleTopicsDoneOrUnexpectedButtonClick(Update update) {
        SendMessage replyMessage = messageHandler.createDefaultMessageFromUpdateCallBack(update);
        String callBackData = update.getCallbackQuery().getData();
        BotButtons commonButton = BotButtons.valueOf(callBackData);

        if (commonButton == TOPICS_DONE) {
            replyMessage = createSendMessageUponClickTopicsDoneButton(update, replyMessage);
        }

        return replyMessage;
    }

    private SendMessage createSendMessageForStartTopicsTutorial(Update update, SendMessage defaultMessage) {
        long userId = update.getMessage().getFrom().getId();
        User user = update.getMessage().getFrom();
        SendMessage replyMessage;

        if (userDataHandler.checkIsUserNew(userId)) {
            userDataHandler.saveNewUserData(user, update);
            replyMessage = createSendMessageForShowTopicsButtons(userId, defaultMessage, BotMessages.WELCOME_MESSAGE);

            log.info("Topics buttons are shown during tutorial for user with id = '{}'", userId);
        } else {
            String welcomeBackMessage = String.format(BotMessages.WELCOME_BACK_MESSAGE.getMessage(), user.getFirstName());
            replyMessage = messageHandler.setMessageToUser(defaultMessage, welcomeBackMessage, userId);
            bottomButtons.setBottomButtons(replyMessage);

            log.info("Welcome back tutorial is finished for user with id = '{}'", userId);
        }

        return replyMessage;
    }

    private SendMessage createSendMessageUponClickTopicsDoneButton(Update update, SendMessage defaultMessage) {
        long userId = update.getCallbackQuery().getFrom().getId();
        SendMessage replyMessage;

        if (userDataHandler.checkIsDoneButtonClickedForUser(userId)) {
            replyMessage = messageHandler.setMessageToUser(defaultMessage, BotMessages.TOPIC_DONE_REPEAT_CLICK.getMessage(), userId);

            log.info("User with id = '{}' is clicked on already clicked Done topics button", userId);
        }
        else if (userDataHandler.checkIsUserDoNotHaveActiveTopics(userId)) {
            replyMessage = messageHandler.setMessageToUser(defaultMessage, BotMessages.TOPIC_DONE_NO_CHOSEN_CLICK.getMessage(), userId);

            log.info("User with id = '{}' is clicked on Done topics button without choose any topic during tutorial", userId);

        }
        else {
            replyMessage = createSendMessageUponProperClickTopicsDoneButton(userId, defaultMessage);
        }

        return replyMessage;
    }

    private SendMessage createSendMessageUponProperClickTopicsDoneButton(long userId, SendMessage defaultMessage) {
        SendMessage replyMessage;

        if (userDataHandler.checkTimeIsPresent(userId)) {
            replyMessage = messageHandler.setMessageToUser(defaultMessage, BotMessages.TOPICS_ARE_CHANGED_MESSAGE.getMessage(), userId);
            userDataHandler.updateDoneButtonClickedData(userId, true);

            log.info("User with id = '{}' is clicked on Done topics button and topic successfully changed", userId);
        } else {
            userDataHandler.updateTimeEnterModeData(userId, true);
            replyMessage = messageHandler.setMessageToUser(defaultMessage, BotMessages.FINISH_TOPICS_TUTORIAL_MESSAGE.getMessage(), userId);
            userDataHandler.updateDoneButtonClickedData(userId, true);

            log.info("Time tutorial is started for user with id = '{}'", userId);
        }

        return replyMessage;
    }

    private SendMessage createSendMessageForCompleteTimeEntering(long userId, String message, SendMessage defaultMessage) {
        SendMessage replyMessage;
        userDataHandler.updateTimeEnterModeData(userId, false);

        if (userDataHandler.checkTimeIsPresent(userId)) {
            userDataHandler.updateTimeData(userId, message);
            replyMessage = messageHandler.setMessageToUser(defaultMessage, BotMessages.TIME_ARE_CHANGED_MESSAGE.getMessage(), userId);

            log.info("Time changing is completed for user with id = '{}'", userId);
        } else {
            userDataHandler.updateTimeData(userId, message);
            bottomButtons.setBottomButtons(defaultMessage);
            replyMessage = messageHandler.setMessageToUser(defaultMessage, BotMessages.COMPLETE_TUTORIAL_MESSAGE.getMessage(), userId);

            log.info("Tutorial is completed for user with id = '{}'", userId);
        }

        return replyMessage;
    }

    private SendMessage createSendMessageForShowTopicsButtons(long userId, SendMessage defaultMessage, BotMessages message) {
        SendMessage replyMessage;
        replyMessage = messageHandler.setMessageToUser(defaultMessage, message.getMessage(), userId);

        InlineKeyboardMarkup messageButtonsMarkup = messageButtons.setTopicsButtons(userId);
        replyMessage.setReplyMarkup(messageButtonsMarkup);

        userDataHandler.updateDoneButtonClickedData(userId, false);

        log.info("Topics button is shown for user with id = '{}'", userId);

        return replyMessage;
    }

    private SendMessage changeUserTime(SendMessage defaultMessage, long userId) {
        userDataHandler.updateTimeEnterModeData(userId, true);
        String time = userDataHandler.getUserTime(userId);

        SendMessage replyMessage = messageHandler.setMessageToUser(defaultMessage, String.format(BotMessages.TIME_CHANGING_MESSAGE.getMessage(), time), userId);

        log.info("Time changing process is started for user with id = '{}'", userId);

        return replyMessage;
    }
}
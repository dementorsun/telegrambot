package com.dementorsun.telegrambot.command;

import com.dementorsun.telegrambot.db.UserDataHandler;
import com.dementorsun.telegrambot.topic.TopicButtonHandler;
import com.dementorsun.telegrambot.utilities.SendMessageGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static com.dementorsun.telegrambot.enums.BotMessages.*;

/**
 * Implementation of Start {@link MenuCommand}.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class StartMenuCommand implements MenuCommand {

    private final UserDataHandler userDataHandler;
    private final SendMessageGenerator sendMessageGenerator;
    private final TopicButtonHandler topicButtonHandler;

    /**
     * Method return {@link SendMessage} object for further execution /start menu command logic.
     * @param update provides {@link Update} object with all needed data for execute menu command logic.
     * @return {@link SendMessage} object to send it to TelegramBot API.
     */
    @Override
    public SendMessage handleMenuCommand(Update update) {
        SendMessage sendMessage;
        long userId = update.getMessage().getFrom().getId();
        User user = update.getMessage().getFrom();
        String firstName = user.getFirstName();

        //If user doesn't have any data in DB(first time login), then save user to DB and start tutorial flow.
        if (!userDataHandler.checkIsDataPresentForUser(userId)) {
            userDataHandler.saveNewUserData(user, update);
            sendMessage = sendMessageGenerator.createSendMessageFromMessage(update, WELCOME_MESSAGE.getMessage());

            InlineKeyboardMarkup messageButtonsMarkup = topicButtonHandler.setTopicsButtons(userId);
            sendMessage.setReplyMarkup(messageButtonsMarkup);

            log.info("SendMessage with topics buttons during tutorial is generated for user with id = '{}'", userId);

            //If silent mode is active for user, then send info message.
        } else if(userDataHandler.checkIsSilenceModeActiveForUser(userId)) {
            sendMessage = sendMessageGenerator.createSendMessageFromMessage(update,
                    String.format(WELCOME_BACK_WITH_SILENCE_MODE_MESSAGE.getMessage(), firstName));

            log.info("SendMessage with welcome back tutorial during active silence mode is generated for user with id = '{}'", userId);

            //In other cases just send info message.
        } else {
            sendMessage = sendMessageGenerator.createSendMessageFromMessage(update,
                    String.format(WELCOME_BACK_MESSAGE.getMessage(), firstName));

            log.info("SendMessage with finished welcome back tutorial is generated for user with id = '{}'", userId);
        }

        return sendMessage;
    }
}
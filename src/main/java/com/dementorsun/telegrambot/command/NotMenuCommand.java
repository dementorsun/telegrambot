package com.dementorsun.telegrambot.command;

import com.dementorsun.telegrambot.db.UserDataHandler;
import com.dementorsun.telegrambot.utilities.BotMessageGenerator;
import com.dementorsun.telegrambot.utilities.SendMessageGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import static com.dementorsun.telegrambot.enums.BotMessages.*;

/**
 * Implementation of Not Command {@link MenuCommand}.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class NotMenuCommand implements MenuCommand {

    private final UserDataHandler userDataHandler;
    private final SendMessageGenerator sendMessageGenerator;
    private final BotMessageGenerator botMessageGenerator;

    /**
     * Method return {@link SendMessage} object for further bot logic execution when user entered not a command at all.
     * @param update provides {@link Update} object with all needed data for execute menu command logic.
     * @return {@link SendMessage} object to send it to TelegramBot API.
     */
    @Override
    public SendMessage handleMenuCommand(Update update) {
        SendMessage sendMessage ;
        long userId = update.getMessage().getFrom().getId();

        //If time entering mode is active, then check entered message for correct time format
        if (userDataHandler.getIsTimeEnterMode(userId)) {
            String message = update.getMessage().getText().trim();
            //If time format is correct, then complete time entering mode
            if (checkTimeFormatIsCorrect(message)) {
                userDataHandler.setTimeEnterModeDataForUser(userId, false);

                //If time value is already present for user, then set new time and send info message
                if (userDataHandler.checkTimeIsPresent(userId)) {
                    userDataHandler.setTimeDataForUser(userId, message);

                    sendMessage = sendMessageGenerator.createSendMessageFromMessage(update,
                                                                                     TIME_ARE_CHANGED_MESSAGE.getMessage());

                    log.info("SendMessage with completed time changing flow is generated for user with id = '{}'", userId);

                    //In other case set time and send info message for user
                } else {
                    userDataHandler.setTimeDataForUser(userId, message);

                    sendMessage = sendMessageGenerator.createSendMessageFromMessage(update,
                                                                                     COMPLETE_TUTORIAL_MESSAGE.getMessage());

                    log.info("SendMessage with completed tutorial is generated for user with id = '{}'", userId);
                }

                //In other case send info message for user
            } else {
                sendMessage = sendMessageGenerator.createSendMessageFromMessage(update,
                                                                                 FAIL_TIME_FORMAT_MESSAGE.getMessage());

                log.info("SendMessage with 'Wrong time format' message is generated for user with id = '{}'.", userId);
            }

            //If time entering mode is not active, then send 'random message' for user
        } else {
            sendMessage = sendMessageGenerator.createSendMessageFromMessage(update,
                                                                             botMessageGenerator.generateRandomBotMessage());

            log.info("SendMessage with random message is generated for user with id = '{}'.", userId);
        }

        return sendMessage;
    }

    private boolean checkTimeFormatIsCorrect(String message) {
        boolean timeFormatIsCorrect;
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalTime.parse(message, timeFormat);
            timeFormatIsCorrect = true;
        } catch (DateTimeParseException | NullPointerException e) {
            timeFormatIsCorrect = false;
        }

        return timeFormatIsCorrect;
    }
}
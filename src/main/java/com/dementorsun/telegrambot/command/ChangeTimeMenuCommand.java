package com.dementorsun.telegrambot.command;

import com.dementorsun.telegrambot.db.UserDataHandler;
import com.dementorsun.telegrambot.utilities.SendMessageObjectGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.dementorsun.telegrambot.enums.BotMessages.*;

/**
 * Implementation of Change Time {@link MenuCommand}.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ChangeTimeMenuCommand implements MenuCommand {

    private final UserDataHandler userDataHandler;
    private final SendMessageObjectGenerator sendMessageObjectGenerator;

    /**
     * Method return {@link SendMessage} object for further execution /change_time menu command logic.
     *
     * @param update provides {@link Update} object with all needed data for execute menu command logic.
     * @return {@link SendMessage} object to send it to TelegramBot API.
     */
    @Override
    public SendMessage handleMenuCommand(Update update) {
        SendMessage sendMessage;
        long userId = update.getMessage().getFrom().getId();
        boolean isSilenceModeActive = userDataHandler.checkIsSilenceModeActiveForUser(userId);

        //Set up timer entering mode for user and wait till user enter new time in correct format.
        userDataHandler.setTimeEnterModeDataForUser(userId, true);

        //If silent mode is active for user, then send info message.
        if (isSilenceModeActive) {
            sendMessage = sendMessageObjectGenerator.createSendMessageObject(update,
                                                                             TIME_CHANGING_WITH_SILENCE_MODE.getMessage());

            log.info("Send message with time changing process during active silence mode is generated for user with id = '{}'", userId);

            //In other case send info message for user with current time from DB.
        } else {
            String time = userDataHandler.getUserTime(userId);
            sendMessage = sendMessageObjectGenerator.createSendMessageObject(update,
                                                                             String.format(TIME_CHANGING_MESSAGE.getMessage(), time));

            log.info("SendMessage with started time changing process is generated for user with id = '{}'", userId);
        }

        return sendMessage;
    }
}
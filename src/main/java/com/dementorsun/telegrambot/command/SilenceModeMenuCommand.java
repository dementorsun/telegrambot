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
 * Implementation of Silence Mode {@link MenuCommand}.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class SilenceModeMenuCommand implements MenuCommand {

    private final UserDataHandler userDataHandler;
    private final SendMessageObjectGenerator sendMessageObjectGenerator;

    /**
     * Method return {@link SendMessage} object for further execution /stop_spam menu command logic.
     * @param update provides {@link Update} object with all needed data for execute menu command logic.
     * @return {@link SendMessage} object to send it to TelegramBot API.
     */
    @Override
    public SendMessage handleMenuCommand(Update update) {
        SendMessage sendMessage;
        long userId = update.getMessage().getFrom().getId();

        //If silent mode is active for user, then send info message.
        if (userDataHandler.checkIsSilenceModeActiveForUser(userId)) {
            sendMessage = sendMessageObjectGenerator.createSendMessageObject(update,
                                                                             SILENCE_MODE_IS_ALREADY_ACTIVE_MESSAGE.getMessage());

            log.info("SendMessage with already active silence mode is generated for user with id = '{}'", userId);

            //In other case set up silence mode for user in DB and send info message.
        } else {
            userDataHandler.setSilenceModeForUser(userId);

            sendMessage = sendMessageObjectGenerator.createSendMessageObject(update,
                                                                             ACTIVATE_SILENCE_MODE_MESSAGE.getMessage());

            log.info("SendMessage with activated silence mode is generated for user with id = '{}'", userId);
        }

        return sendMessage;
    }
}
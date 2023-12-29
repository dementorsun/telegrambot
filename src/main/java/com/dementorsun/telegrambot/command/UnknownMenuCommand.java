package com.dementorsun.telegrambot.command;

import com.dementorsun.telegrambot.utilities.SendMessageGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.dementorsun.telegrambot.enums.BotMessages.UNKNOWN_COMMAND_MESSAGE;

/**
 * Implementation of {@link MenuCommand} for cases when command is absent.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class UnknownMenuCommand implements MenuCommand {

    private final SendMessageGenerator sendMessageGenerator;

    /**
     * Method return {@link SendMessage} object for further bot logic execution when user entered
     * command(starts from "/" prefix), but it absent in {@link MenuCommand} constants.
     * @param update provides {@link Update} object with all needed data for execute menu command logic.
     * @return {@link SendMessage} object to send it to TelegramBot API.
     */
    @Override
    public SendMessage handleMenuCommand(Update update) {
        long userId = update.getMessage().getFrom().getId();
        SendMessage sendMessage = sendMessageGenerator.createSendMessageFromMessage(update,
                                                                                     UNKNOWN_COMMAND_MESSAGE.getMessage());

        log.info("SendMessage with entered unknown command is generated for user with id = '{}'.", userId);

        return sendMessage;
    }
}
package com.dementorsun.telegrambot.command;

import com.dementorsun.telegrambot.bot.buttons.MessageButtons;
import com.dementorsun.telegrambot.db.UserDataHandler;
import com.dementorsun.telegrambot.utilities.SendMessageObjectGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static com.dementorsun.telegrambot.enums.BotMessages.*;

/**
 * Implementation of Change Topics {@link MenuCommand}.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ChangeTopicsMenuCommand implements MenuCommand {

    private final UserDataHandler userDataHandler;
    private final MessageButtons messageButtons;
    private final SendMessageObjectGenerator sendMessageObjectGenerator;

    /**
     * Method return {@link SendMessage} object for further execution /change_topics menu command logic.
     *
     * @param update provides {@link Update} object with all needed data for execute menu command logic.
     * @return {@link SendMessage} object to send it to TelegramBot API.
     */
    @Override
    public SendMessage handleMenuCommand(Update update) {
        SendMessage sendMessage;
        long userId = update.getMessage().getFrom().getId();

        //If silent mode is active and user wants to change topics, then send info message.
        if (userDataHandler.checkIsSilenceModeActiveForUser(userId)) {
            sendMessage = sendMessageObjectGenerator.createSendMessageObject(update,
                                                                             TOPICS_WITH_SILENCE_MODE_MESSAGE.getMessage());

            log.info("SendMessage with silence mode explanation after click on topics button is generated for user with id = '{}'", userId);

            //In other cases show topics buttons for user.
        } else {
            sendMessage = sendMessageObjectGenerator.createSendMessageObject(update, CHANGE_TOPICS_MESSAGE.getMessage());
            InlineKeyboardMarkup messageButtonsMarkup = messageButtons.setTopicsButtons(userId);
            sendMessage.setReplyMarkup(messageButtonsMarkup);

            userDataHandler.setDoneButtonClickedDataForUser(userId, false);

            log.info("SendMessage with topics buttons is generated for user with id = '{}'", userId);
        }

        return sendMessage;
    }
}
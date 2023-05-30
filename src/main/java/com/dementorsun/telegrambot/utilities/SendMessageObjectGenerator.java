package com.dementorsun.telegrambot.utilities;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

/**
 * Utility class for creating {@link SendMessage} object.
 */

@Component
@NoArgsConstructor
public class SendMessageObjectGenerator {

    private static final String BOT_MESSAGE_TITLE = "*КоженДеньБот: *";

    /**
     * Method return {@link SendMessage} object.
     * @param update provides {@link Update} object for retrieve chat identifier from it for {@link SendMessage} object.
     * @param text provides text for {@link SendMessage} object.
     * @return {@link SendMessage} object with filled mandatory fields.
     */
    public SendMessage createSendMessageObject(Update update, String text) {
        long chatId = update.getMessage().getChatId();

        return SendMessage.builder().
                chatId(String.valueOf(chatId))
                .text(BOT_MESSAGE_TITLE + text)
                .parseMode(MARKDOWN)
                .build();
    }
}
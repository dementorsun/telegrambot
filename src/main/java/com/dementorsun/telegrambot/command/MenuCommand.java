package com.dementorsun.telegrambot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Interface for handling commands received from users after clicking on menu buttons.
 */
public interface MenuCommand {

    /**
     * Main method which return {@link SendMessage} object for further bot menu command logic execution.
     * @param update provides {@link Update} object with all needed data for execute menu command logic.
     * @return {@link SendMessage} object to send it to TelegramBot API.
     */
    SendMessage handleMenuCommand(Update update);
}
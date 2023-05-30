package com.dementorsun.telegrambot.command;

import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@NoArgsConstructor
class TestData {

    public static final long USER_ID = 666;
    public static final String FIRST_NAME = "Dmytro";

    public Update createUpdateInstance() {
        User user = new User();
        user.setId(USER_ID);
        user.setFirstName(FIRST_NAME);

        Message message = new Message();
        message.setFrom(user);

        Update update = new Update();
        update.setMessage(message);

        return update;
    }

    public Update createUpdateInstanceWithText(String text) {
        Update update = createUpdateInstance();
        update.getMessage().setText(text);

        return update;
    }
}
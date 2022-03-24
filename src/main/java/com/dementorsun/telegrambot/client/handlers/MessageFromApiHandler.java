package com.dementorsun.telegrambot.client.handlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

@Component
@AllArgsConstructor
public class MessageFromApiHandler {

    public SendPhoto createSendPhoto(String chatId, InputFile photo, String caption) {
        return SendPhoto.builder()
                .chatId(chatId)
                .photo(photo)
                .caption(caption)
                .parseMode("Markdown")
                .build();
    }

    public SendMessage createSendMessage(String chatId, String message) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .parseMode("Markdown")
                .build();
    }
}
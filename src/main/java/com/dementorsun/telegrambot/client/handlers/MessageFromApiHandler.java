package com.dementorsun.telegrambot.client.handlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

@Component
@AllArgsConstructor
class MessageFromApiHandler {

    public SendPhoto generateSendPhoto(long chatId, InputFile photo, String caption) {
        return SendPhoto.builder()
                .chatId(String.valueOf(chatId))
                .photo(photo)
                .caption(caption)
                .parseMode("Markdown")
                .build();
    }

    public SendMessage generateSendMessage(long chatId, String message) {
        return SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text(message)
                .parseMode("Markdown")
                .build();
    }
}
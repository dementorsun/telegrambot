package com.dementorsun.telegrambot.bot.handlers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Random;

import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

@Component
@AllArgsConstructor
public class MessageHandler {

    private static final String BOT_MESSAGE = "*КоженДеньБот: *";

    public SendMessage createDefaultMessageFromUpdateMessage(long chatId) {
        return SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text(getRandomMessage())
                .build();
    }

    public SendMessage createDefaultMessageFromUpdateCallBack(long chatId) {
        return SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text(getRandomMessage())
                .build();
    }

    public SendMessage setMessageToUser(SendMessage replyMessage, String message) {
        replyMessage.setText(BOT_MESSAGE + message);
        replyMessage.setParseMode(MARKDOWN);

        return replyMessage;
    }

    public SendMessage setNewMessageToUser(long chatId, String message) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), BOT_MESSAGE + message);
        sendMessage.setParseMode(MARKDOWN);

        return sendMessage;
    }

    public boolean checkTimeFormatIsCorrect(String message) {
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

    public String getInitialDayMessage() {
        List<String> randomMessageList =
                List.of("Ось, трохи відволічись від тіктоку.",
                        "Кожен день одне й теж саме. Кожен день...",
                        "Слава Україні!\uD83C\uDDFA\uD83C\uDDE6",
                        "До вашої уваги пропонується.",
                        "Це знову я, а це знову ти...",
                        "Який сьогодні день? Де я?",
                        "Це ж було вже!©",
                        "Ось, все як домовлялися.",
                        "Путін вже помер?",
                        "Мені здається, що я тобі подобаюсь. Та нічого, я не проти.");

        return randomMessageList.get(new Random().nextInt(randomMessageList.size()));
    }

    private String getRandomMessage() {
        List<String> randomMessageList =
                List.of("Ну і шо?",
                        "Слава Україні!\uD83C\uDDFA\uD83C\uDDE6",
                        "Ти шо руській? Тобі показати в якій стороні воєнний корабль?",
                        "Сходи краще зроби собі чай.",
                        "Майбутні покоління ботів помстяться тобі за це!",
                        "Це все що ти можеш мені сказати?",
                        "Продовжуй, якщо тобі стане від цього легше. Я не поспішаю.",
                        "Я навіть трохи заздрю тобі. Так багато вільного часу...",
                        "Як давно це з тобою?",
                        "Я починаю за тебе хвилюватися.");

        return randomMessageList.get(new Random().nextInt(randomMessageList.size()));
    }
}
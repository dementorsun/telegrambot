package com.dementorsun.telegrambot.bot.handlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
@AllArgsConstructor
public class MessageHandler {

    private static final String BOT_MESSAGE = "*КоженДеньБот: *";

    public SendMessage createDefaultMessageFromUpdateMessage(long chatId, long userId) {
        SendMessage defaultMessage = SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text(getRandomMessage())
                .build();

        log.info("Default message '{}' is created from UpdateMessage for user with id = {}", defaultMessage.getText(), userId);

        return defaultMessage;
    }

    public SendMessage createDefaultMessageFromUpdateCallBack(long chatId, long userId) {
        SendMessage defaultMessage = SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text(getRandomMessage())
                .build();

        log.info("Default message '{}' is created from UpdateCallBack for user with id = {}", defaultMessage.getText(), userId);

        return defaultMessage;
    }

    public SendMessage setMessageToUser(SendMessage replyMessage, String message, long userId) {
        replyMessage.setText(BOT_MESSAGE + message);
        replyMessage.setParseMode("Markdown");

        log.info("'{}' message is set for user with id = '{}'", message, userId);

        return replyMessage;
    }

    public SendMessage setNewMessageToUser(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(BOT_MESSAGE + message);
        sendMessage.setParseMode("Markdown");

        return sendMessage;
    }

    public boolean checkTimeFormatIsCorrect(String message) {
        boolean timeFormatIsCorrect = false;

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalTime.parse(message, timeFormat);
            timeFormatIsCorrect = true;

            log.info("Time format for '{}' message is correct", message);
        } catch (DateTimeParseException | NullPointerException e) {
            log.info("Time format for '{}' message is not correct", message);
        }

        return timeFormatIsCorrect;
    }

    public String getInitialDayMessage() {
        List<String> randomMessageList =
                Arrays.asList("Ось, трохи відволічись від тіктоку.",
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
                Arrays.asList("Ну і шо?",
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
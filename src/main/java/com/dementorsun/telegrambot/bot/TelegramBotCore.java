package com.dementorsun.telegrambot.bot;

import com.dementorsun.telegrambot.bot.dto.TopicButtonCallBackData;
import com.dementorsun.telegrambot.bot.handlers.UpdateHandler;
import com.dementorsun.telegrambot.bot.handlers.UpdateObjectHandler;
import com.dementorsun.telegrambot.sheduler.SchedulerHelper;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
class TelegramBotCore extends TelegramLongPollingBot {

    private final UpdateHandler updateHandler;
    private final SchedulerHelper schedulerHelper;
    private final Environment environment;
    private final Gson gson;

    @Override
    public String getBotUsername() {
        return environment.getProperty("bot.username");
    }

    @Override
    public String getBotToken() {
        return environment.getProperty("bot.token");
    }

    @Override
    public synchronized void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            SendMessage sendMessageToUser = updateHandler.handleMessageUpdate(update);
            long userId = UpdateObjectHandler.getUserIdFromUpdate(update);
            String messageToUser = sendMessageToUser.getText();
            try {
                execute(sendMessageToUser);
                log.info("Telegram bot sent '{}' message to user with '{}' id", messageToUser, userId);
            } catch (TelegramApiException e) {
                log.info("Exception is occurred during sending message to user with '{}' id: {}", userId, e.getMessage());
            }
        } else if (update.hasCallbackQuery()) {
            long userId = UpdateObjectHandler.getCallBackUserIdFromUpdate(update);
            TopicButtonCallBackData callBackData = gson.fromJson(UpdateObjectHandler.getCallBackDataFromUpdate(update), TopicButtonCallBackData.class);
            if (Boolean.TRUE.equals(callBackData.getIsTopicButton())) {
                try {
                    execute(updateHandler.handleCallBackDataUponTopicButtonClick(update));
                    log.info("Telegram bot handled '{}' button click by user with '{}' id", callBackData, userId);
                } catch (TelegramApiException e) {
                    log.info("Exception is occurred during handling click to topic button by user with '{}' id: {}", userId, e.getMessage());
                }
            } else if (Boolean.FALSE.equals(callBackData.getIsTopicButton())) {
                try {
                    execute(updateHandler.handleCallBackDataUponDoneButtonClick(update));
                    log.info("Telegram bot handled done topics button click by user with '{}' id", userId);

                } catch (TelegramApiException e) {
                    log.info("Exception is occurred during handling click to topics done button by user with '{}' id: {}", userId, e.getMessage());
                }
            }
            try {
                execute(updateHandler.generateAnswerCallBackQuery(update));
                log.info("Telegram bot sent answer call back query to user with '{}' id", userId);
            } catch (TelegramApiException e) {
                log.info("Exception is occurred during sending answer call back query to user with '{}' id: {}", userId, e.getMessage());
            }
        } else {
            long userId = UpdateObjectHandler.getCallBackUserIdFromUpdate(update);
            try {
                execute(updateHandler.handleUnexpectedAction(update));
                log.info("Telegram bot sent message about unexpected action to user with '{}' id", userId);
            } catch (TelegramApiException e) {
                log.info("Exception is occurred during sending message about unexpected action to user with '{}' id: {}", userId, e.getMessage());
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    public void sendScheduledTopics() {
        List<Object> photosAndMessagesToSend = schedulerHelper.getPhotosAndMessagesToSend();

        if (!photosAndMessagesToSend.isEmpty()) {
            photosAndMessagesToSend.forEach((objectToSend) -> {
                if (objectToSend instanceof SendPhoto) {
                    SendPhoto photoToSend = (SendPhoto) objectToSend;
                    String chatId = photoToSend.getChatId();
                    try {
                        execute(photoToSend);
                        log.info("Telegram bot sent scheduled photo to '{}' chat id", chatId);
                    } catch (TelegramApiException e) {
                        log.info("Exception is occurred during sending scheduled photo to '{}' chat id: {}", chatId, e.getMessage());
                    }
                } else if (objectToSend instanceof SendMessage) {
                    SendMessage messageToSend = (SendMessage) objectToSend;
                    String chatId = messageToSend.getChatId();
                    try {
                        execute(messageToSend);
                        log.info("Telegram bot sent scheduled message with '{}' text to '{}' chat id", messageToSend.getText(), chatId);
                    } catch (TelegramApiException e) {
                        log.info("Exception is occurred during sending scheduled message to '{}' chat id: {}", chatId, e.getMessage());
                    }
                } else {
                    log.info("Incompatible object type during send message/photo");
                }
            });
        }
    }
}
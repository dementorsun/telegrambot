package com.dementorsun.telegrambot.bot;

import com.dementorsun.telegrambot.bot.handlers.UpdateHandler;
import com.dementorsun.telegrambot.sheduler.SchedulerHelper;
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
            try {
                execute(updateHandler.handleMessageUpdate(update));
            } catch (TelegramApiException e) {
                log.info("Telegram bot send message exception: {}", e.getMessage());
            }
        } else if (update.hasCallbackQuery()) {
            String callBackData = update.getCallbackQuery().getData();
            if (callBackData.contains("_TOPIC")) {
                try {
                    execute(updateHandler.handleCallBackDataUponTopicButtonClick(update));
                } catch (TelegramApiException e) {
                    log.info("Telegram bot handle click topic button exception: {}", e.getMessage());
                }
            } else if (callBackData.equals("TOPICS_DONE")) {
                try {
                    execute(updateHandler.handleCallBackDataUponDoneButtonClick(update));
                } catch (TelegramApiException e) {
                    log.info("Telegram bot handle click topics done button exception: {}", e.getMessage());
                }
            }
            try {
                execute(updateHandler.generateAnswerCallBackQuery(update));
            } catch (TelegramApiException e) {
                log.info("Telegram bot send call back query answer exception: {}", e.getMessage());
            }
        } else {
            try {
                execute(updateHandler.handleUnexpectedAction(update));
            } catch (TelegramApiException e) {
                log.info("Telegram bot handle unexpected action exception: {}", e.getMessage());
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    public void sendScheduledTopics() {
        List<Object> photosAndMessagesToSend = schedulerHelper.getPhotosAndMessagesToSend();

        if (!photosAndMessagesToSend.isEmpty()) {
            photosAndMessagesToSend.forEach((objectToSend) -> {
                if (objectToSend instanceof SendPhoto) {
                    try {
                        execute((SendPhoto) objectToSend);
                    } catch (TelegramApiException e) {
                        log.info("Telegram bot scheduled send photo exception: {}", e.getMessage());
                    }
                } else if (objectToSend instanceof SendMessage) {
                    try {
                        execute((SendMessage) objectToSend);
                    } catch (TelegramApiException e) {
                        log.info("Telegram bot scheduled send photo exception: {}", e.getMessage());
                    }
                } else {
                    log.info("Incompatible object type during send message/photo");
                }
            });
        }
    }
}
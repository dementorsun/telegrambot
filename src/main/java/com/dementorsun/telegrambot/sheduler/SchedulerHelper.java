package com.dementorsun.telegrambot.sheduler;

import com.dementorsun.telegrambot.bot.data.BotButtons;
import com.dementorsun.telegrambot.bot.handlers.MessageHandler;
import com.dementorsun.telegrambot.client.handlers.ApiHandler;
import com.dementorsun.telegrambot.db.BotUser;
import com.dementorsun.telegrambot.db.UserDataHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@AllArgsConstructor
public class SchedulerHelper {

    private final UserDataHandler userDataHandler;
    private final ApiHandler apiHandler;
    private final MessageHandler messageHandler;

    private String getCurrentTime() {
        TimeZone timeZone = TimeZone.getTimeZone("Europe/Kiev");

        Date currentTime = Calendar.getInstance(timeZone).getTime();
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        formatter.setTimeZone(timeZone);

        return formatter.format(currentTime);
    }

    public List<Object> getPhotosAndMessagesToSend() {
        List<BotUser> usersByTime = userDataHandler.getUsersByTime(getCurrentTime());

        return getObjectsToSend(usersByTime);
    }

    private List<Object> getObjectsToSend(List<BotUser> usersByTime) {
        List<Object> objectsToSend = new ArrayList<>();

        usersByTime.forEach((botUser) -> {
            String chatId = userDataHandler.getUserChatId(botUser);
            long userId = userDataHandler.getUserId(botUser);
            String initialDayMessage = messageHandler.getInitialDayMessage();
            Map<BotButtons, Boolean> userTopics = userDataHandler.getUserTopics(userId);

            objectsToSend.add(messageHandler.setNewMessageToUser(chatId, initialDayMessage));

            if (userTopics.get(BotButtons.NASA_TOPIC))
                objectsToSend.add(apiHandler.createNasaApodSendPhoto(chatId));
            if (userTopics.get(BotButtons.CAT_TOPIC))
                objectsToSend.add(apiHandler.createRandomCatSendPhoto(chatId));
            if (userTopics.get(BotButtons.DOG_TOPIC))
                objectsToSend.add(apiHandler.createRandomDogSendPhoto(chatId));
            if (userTopics.get(BotButtons.MOVIE_TOPIC))
                objectsToSend.add(apiHandler.createRandomMovieSendPhoto(chatId));
            if (userTopics.get(BotButtons.TV_SHOW_TOPIC))
                objectsToSend.add(apiHandler.createRandomTvShowSendPhoto(chatId));
            if (userTopics.get(BotButtons.QUOTE_TOPIC))
                objectsToSend.add(apiHandler.createRandomQuoteSendMessage(chatId));
        });

        return objectsToSend;
    }
}
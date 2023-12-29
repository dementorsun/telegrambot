package com.dementorsun.telegrambot.sheduler;

import com.dementorsun.telegrambot.bot.handlers.MessageHandler;
import com.dementorsun.telegrambot.client.handlers.ApiHandler;
import com.dementorsun.telegrambot.db.dto.BotUser;
import com.dementorsun.telegrambot.db.UserDataHandler;
import com.dementorsun.telegrambot.topic.enums.TopicsDict;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SchedulerHelper {

    private final UserDataHandler userDataHandler;
    private final ApiHandler apiHandler;
    private final MessageHandler messageHandler;

    public List<Object> getPhotosAndMessagesToSend() {
        List<BotUser> usersByTime = userDataHandler.getUsersByTime(getCurrentTime());
        List<Object> objectsToSend = getObjectsToSend(usersByTime)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (objectsToSend.size() == 1) {
            objectsToSend.clear();
        }

        return objectsToSend;
    }

    private List<Object> getObjectsToSend(List<BotUser> usersByTime) {
        List<Object> objectsToSend = new ArrayList<>();

        usersByTime.forEach((botUser) -> {
            long chatId = botUser.getUserInfo().getUserChatId();
            long userId = botUser.getUserId();
            String initialDayMessage = messageHandler.getInitialDayMessage();
            Map<TopicsDict, Boolean> userTopics = userDataHandler.getUserTopics(userId);

            objectsToSend.add(messageHandler.setNewMessageToUser(chatId, initialDayMessage));

            if (userTopics.get(TopicsDict.NASA_TOPIC))
                objectsToSend.add(apiHandler.createNasaApodSendPhoto(chatId));
            if (userTopics.get(TopicsDict.NATURE_TOPIC))
                objectsToSend.add(apiHandler.createNatureSendPhoto(chatId));
            if (userTopics.get(TopicsDict.ANIMALS_TOPIC))
                objectsToSend.add(apiHandler.createAnimalsSendPhoto(chatId));
            if (userTopics.get(TopicsDict.FOREST_TOPIC))
                objectsToSend.add(apiHandler.createForestSendPhoto(chatId));
            if (userTopics.get(TopicsDict.CAT_TOPIC))
                objectsToSend.add(apiHandler.createRandomCatSendPhoto(chatId));
            if (userTopics.get(TopicsDict.DOG_TOPIC))
                objectsToSend.add(apiHandler.createRandomDogSendPhoto(chatId));
            if (userTopics.get(TopicsDict.POKEMON_TOPIC))
                objectsToSend.add(apiHandler.createRandomPokemonSendPhoto(chatId));
            if (userTopics.get(TopicsDict.MOVIE_TOPIC))
                objectsToSend.add(apiHandler.createRandomMovieSendPhoto(chatId));
            if (userTopics.get(TopicsDict.TV_SHOW_TOPIC))
                objectsToSend.add(apiHandler.createRandomTvShowSendPhoto(chatId));
            if (userTopics.get(TopicsDict.ANIME_TOPIC))
                objectsToSend.add(apiHandler.createRandomAnimeSendPhoto(chatId));
            if (userTopics.get(TopicsDict.QUOTE_TOPIC))
                objectsToSend.add(apiHandler.createRandomQuoteSendMessage(chatId));
        });

        return objectsToSend;
    }

    private String getCurrentTime() {
        TimeZone timeZone = TimeZone.getTimeZone("Europe/Kiev");

        Date currentTime = Calendar.getInstance(timeZone).getTime();
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        formatter.setTimeZone(timeZone);

        return formatter.format(currentTime);
    }
}
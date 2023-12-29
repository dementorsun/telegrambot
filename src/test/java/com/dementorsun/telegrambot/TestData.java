package com.dementorsun.telegrambot;

import com.dementorsun.telegrambot.topic.enums.TopicsDict;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.*;

import java.util.EnumMap;
import java.util.Map;

@NoArgsConstructor
public class TestData {

    public static final long USER_ID = 666666666;
    public static final String FIRST_NAME = "Dmytro";
    public static final long CHAT_ID = 123456789;
    public static final int MESSAGE_ID = 123456;

    public Update createUpdateMessageInstance() {
        User user = new User();
        user.setId(USER_ID);
        user.setFirstName(FIRST_NAME);

        Message message = new Message();
        message.setFrom(user);

        Update update = new Update();
        update.setMessage(message);

        return update;
    }

    public Update createUpdateMessageInstanceWithText(String text) {
        Update update = createUpdateMessageInstance();
        update.getMessage().setText(text);

        return update;
    }

    public Update createUpdateCallBackQueryInstance() {
        Chat chat = new Chat();
        chat.setId(CHAT_ID);

        Message message = new Message();
        message.setChat(chat);
        message.setMessageId(MESSAGE_ID);

        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setMessage(message);

        Update update = new Update();
        update.setCallbackQuery(callbackQuery);

        return update;
    }

    public Map<TopicsDict, Boolean> createUserTopics(boolean isMarked) {
        Map<TopicsDict,Boolean> userTopics = new EnumMap<>(TopicsDict.class);

        userTopics.put(TopicsDict.NASA_TOPIC, isMarked);
        userTopics.put(TopicsDict.NATURE_TOPIC, isMarked);
        userTopics.put(TopicsDict.ANIMALS_TOPIC, isMarked);
        userTopics.put(TopicsDict.FOREST_TOPIC, isMarked);
        userTopics.put(TopicsDict.CAT_TOPIC, isMarked);
        userTopics.put(TopicsDict.DOG_TOPIC, isMarked);
        userTopics.put(TopicsDict.POKEMON_TOPIC, isMarked);
        userTopics.put(TopicsDict.MOVIE_TOPIC, isMarked);
        userTopics.put(TopicsDict.TV_SHOW_TOPIC, isMarked);
        userTopics.put(TopicsDict.ANIME_TOPIC, isMarked);
        userTopics.put(TopicsDict.QUOTE_TOPIC, isMarked);

        return userTopics;
    }
}
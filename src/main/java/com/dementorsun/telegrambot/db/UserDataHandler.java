package com.dementorsun.telegrambot.db;

import com.dementorsun.telegrambot.enums.TopicsDict;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
@AllArgsConstructor
public class UserDataHandler {

    private static final String SILENCE_TIME = "silence";
    private static final String NONE_TIME = "none";

    private final FileHandler fileHandler;

    public boolean checkIsUserNew(long userId) {
        boolean isNewUser = false;
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        if (botUser == null) {
            isNewUser = true;
        }

        return isNewUser;
    }

    public boolean getIsDoneButtonClickedForUser(long userId) {
        return fileHandler.getUserDataFromDbFile(userId).userSettings.isDoneClicked;
    }

    public boolean getIsTimeEnterMode(long userId) {
        return fileHandler.getUserDataFromDbFile(userId).userSettings.isTimeEnterMode;
    }

    public boolean checkTimeIsPresent(long userId) {
        boolean isTimePresent = true;
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        if (botUser.userSettings.time.equals(NONE_TIME)) {
            isTimePresent = false;
        }

        return isTimePresent;
    }

    public void saveNewUserData(User user, long chatId) {
        BotUser botUser = BotUser.builder()
                .userInfo(BotUser.UserInfo.builder()
                        .userId(user.getId())
                        .username(user.getUserName())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .userChatId(chatId)
                        .build())
                .userSettings(BotUser.UserSetting.builder()
                        .isDoneClicked(false)
                        .isTimeEnterMode(false)
                        .time(NONE_TIME)
                        .build())
                .userTopics(BotUser.UserTopic.builder()
                        .isNasaChosen(false)
                        .isCatChosen(false)
                        .isDogChosen(false)
                        .isPokemonChosen(false)
                        .isQuoteChosen(false)
                        .isMovieChosen(false)
                        .isTvShowChosen(false)
                        .build())
                .build();

        fileHandler.updateUserDataInDbFile(botUser);

        log.info("New user with id = '{}' has been saved to DB file", botUser.userInfo.userId);
    }

    public void setNasaTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsNasaChosen(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Nasa topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setCatTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsCatChosen(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Cat topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setDogTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsDogChosen(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Dog topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setPokemonTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsPokemonChosen(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Pokemon topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setQuoteTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsQuoteChosen(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Quote topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setMovieTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsMovieChosen(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Movie topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setTvShowTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsTvShowChosen(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for TV Show topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setDoneButtonClickedDataForUser(long userId, boolean isClicked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userSettings.setIsDoneClicked(isClicked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Done topics button and user with id = '{}' in DB file", isClicked, userId);
    }

    public void setTimeDataForUser(long userId, String time) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userSettings.setTime(time);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for time property and user with id = '{}' in DB file", time, userId);
    }

    public void setTimeEnterModeDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userSettings.setIsTimeEnterMode(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for time enter mode property and user with id = '{}' in DB file", isMarked, userId);
    }

    public Map<TopicsDict, Boolean> getUserTopics(long userId) {
        BotUser.UserTopic botUserTopics = fileHandler.getUserDataFromDbFile(userId).userTopics;
        Map<TopicsDict,Boolean> userTopics = new HashMap<>();

        userTopics.put(TopicsDict.NASA_TOPIC, botUserTopics.isNasaChosen);
        userTopics.put(TopicsDict.CAT_TOPIC, botUserTopics.isCatChosen);
        userTopics.put(TopicsDict.DOG_TOPIC, botUserTopics.isDogChosen);
        userTopics.put(TopicsDict.POKEMON_TOPIC, botUserTopics.isPokemonChosen);
        userTopics.put(TopicsDict.QUOTE_TOPIC, botUserTopics.isQuoteChosen);
        userTopics.put(TopicsDict.MOVIE_TOPIC, botUserTopics.isMovieChosen);
        userTopics.put(TopicsDict.TV_SHOW_TOPIC, botUserTopics.isTvShowChosen);

        return userTopics;
    }

    public String getUserTime(long userId) {
        return fileHandler.getUserDataFromDbFile(userId).userSettings.time;
    }

    public List<BotUser> getUsersByTime(String time) {
        return fileHandler.getUsersFromDbFile().stream()
                .filter(botUser -> botUser.userSettings.time.equals(time))
                .collect(Collectors.toList());
    }

    public long getUserChatId(BotUser botUser) {
        return botUser.userInfo.userChatId;
    }

    public long getUserId(BotUser botUser) {
        return botUser.userInfo.userId;
    }

    public boolean checkIsUserDoNotHaveActiveTopics(long userId) {
        return getUserTopics(userId).entrySet()
                .stream()
                .noneMatch(Map.Entry::getValue);
    }

    public boolean checkIsSilenceModeActiveForUser(long userId) {
        boolean isStopModeActive = false;
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        if (botUser.userSettings.time.equals(SILENCE_TIME)) {
            isStopModeActive = true;
        }

        return isStopModeActive;
    }

    public void setSilenceModeForUser(long userId) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userSettings.setTime(SILENCE_TIME);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Silence mode has been set for user with id = '{}' in DB file", userId);
    }
}
package com.dementorsun.telegrambot.db;

import com.dementorsun.telegrambot.db.dto.BotUser;
import com.dementorsun.telegrambot.db.redis.RedisClient;
import com.dementorsun.telegrambot.enums.TopicsDict;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@AllArgsConstructor
public class UserDataHandler {

    private static final String SILENCE_TIME = "silence";
    private static final String NONE_TIME = "none";
    private final RedisClient redisClient;

    public boolean checkIsDataPresentForUser(long userId) {
        return Optional.ofNullable(redisClient.getUserData(userId)).isPresent();
    }

    public boolean checkIsNewUser(long userId) {
        return redisClient.getUserData(userId).getUserSettings().getIsNewUser();
    }

    public boolean getIsDoneButtonClickedForUser(long userId) {
        return redisClient.getUserData(userId).getUserSettings().getIsDoneClicked();
    }

    public boolean getIsTimeEnterMode(long userId) {
        return redisClient.getUserData(userId).getUserSettings().getIsTimeEnterMode();
    }

    public boolean checkTimeIsPresent(long userId) {
        boolean isTimePresent = true;
        BotUser botUser = redisClient.getUserData(userId);

        if (botUser.getUserSettings().getTime().equals(NONE_TIME)) {
            isTimePresent = false;
        }

        return isTimePresent;
    }

    public Map<TopicsDict, Boolean> getUserTopics(long userId) {
        BotUser.UserTopic botUserTopics = redisClient.getUserData(userId).getUserTopics();
        Map<TopicsDict,Boolean> userTopics = new HashMap<>();

        userTopics.put(TopicsDict.NASA_TOPIC, botUserTopics.getIsNasaChosen());
        userTopics.put(TopicsDict.SCENERY_TOPIC, botUserTopics.getIsSceneryChosen());
        userTopics.put(TopicsDict.ANIMALS_TOPIC, botUserTopics.getIsAnimalsChosen());
        userTopics.put(TopicsDict.FLOWERS_TOPIC, botUserTopics.getIsFlowersChosen());
        userTopics.put(TopicsDict.CAT_TOPIC, botUserTopics.getIsCatChosen());
        userTopics.put(TopicsDict.DOG_TOPIC, botUserTopics.getIsDogChosen());
        userTopics.put(TopicsDict.POKEMON_TOPIC, botUserTopics.getIsPokemonChosen());
        userTopics.put(TopicsDict.MOVIE_TOPIC, botUserTopics.getIsMovieChosen());
        userTopics.put(TopicsDict.TV_SHOW_TOPIC, botUserTopics.getIsTvShowChosen());
        userTopics.put(TopicsDict.ANIME_TOPIC, botUserTopics.getIsAnimeChosen());
        userTopics.put(TopicsDict.QUOTE_TOPIC, botUserTopics.getIsQuoteChosen());

        return userTopics;
    }

    public String getUserTime(long userId) {
        return redisClient.getUserData(userId).getUserSettings().getTime();
    }

    public boolean checkIsSilenceModeActiveForUser(long userId) {
        boolean isStopModeActive = false;
        BotUser botUser = redisClient.getUserData(userId);

        if (SILENCE_TIME.equals(botUser.getUserSettings().getTime())) {
            isStopModeActive = true;
        }

        return isStopModeActive;
    }

    public List<BotUser> getUsersByTime(String time) {
        return redisClient.getAllUsersData()
                .stream()
                .filter(botUser -> botUser.getUserSettings().getTime() != null)
                .filter(botUser -> time.equals(botUser.getUserSettings().getTime()))
                .collect(Collectors.toList());
    }

    public void saveNewUserData(User user, long chatId) {
        BotUser botUser = BotUser.builder()
                .userId(user.getId())
                .userInfo(BotUser.UserInfo.builder()
                        .username(user.getUserName())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .userChatId(chatId)
                        .build())
                .userSettings(BotUser.UserSetting.builder()
                        .isDoneClicked(false)
                        .isTimeEnterMode(false)
                        .isNewUser(true)
                        .time(NONE_TIME)
                        .build())
                .userTopics(BotUser.UserTopic.builder()
                        .isNasaChosen(false)
                        .isSceneryChosen(false)
                        .isAnimalsChosen(false)
                        .isFlowersChosen(false)
                        .isCatChosen(false)
                        .isDogChosen(false)
                        .isPokemonChosen(false)
                        .isMovieChosen(false)
                        .isTvShowChosen(false)
                        .isAnimeChosen(false)
                        .isQuoteChosen(false)
                        .build())
                .build();

        redisClient.saveUserData(botUser);

        log.info("New user with id = '{}' has been saved to DB file", botUser.getUserId());
    }

    public void setNasaTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = redisClient.getUserData(userId);

        botUser.getUserTopics().setIsNasaChosen(isMarked);
        redisClient.saveUserData(botUser);

        log.info("Value '{}' has been set for Nasa topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setCatTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = redisClient.getUserData(userId);

        botUser.getUserTopics().setIsCatChosen(isMarked);
        redisClient.saveUserData(botUser);

        log.info("Value '{}' has been set for Cat topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setDogTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = redisClient.getUserData(userId);

        botUser.getUserTopics().setIsDogChosen(isMarked);
        redisClient.saveUserData(botUser);

        log.info("Value '{}' has been set for Dog topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setPokemonTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = redisClient.getUserData(userId);

        botUser.getUserTopics().setIsPokemonChosen(isMarked);
        redisClient.saveUserData(botUser);

        log.info("Value '{}' has been set for Pokemon topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setQuoteTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = redisClient.getUserData(userId);

        botUser.getUserTopics().setIsQuoteChosen(isMarked);
        redisClient.saveUserData(botUser);

        log.info("Value '{}' has been set for Quote topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setMovieTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = redisClient.getUserData(userId);

        botUser.getUserTopics().setIsMovieChosen(isMarked);
        redisClient.saveUserData(botUser);

        log.info("Value '{}' has been set for Movie topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setTvShowTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = redisClient.getUserData(userId);

        botUser.getUserTopics().setIsTvShowChosen(isMarked);
        redisClient.saveUserData(botUser);

        log.info("Value '{}' has been set for TV Show topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setAnimeTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = redisClient.getUserData(userId);

        botUser.getUserTopics().setIsAnimeChosen(isMarked);
        redisClient.saveUserData(botUser);

        log.info("Value '{}' has been set for Anime topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setSceneryTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = redisClient.getUserData(userId);

        botUser.getUserTopics().setIsSceneryChosen(isMarked);
        redisClient.saveUserData(botUser);

        log.info("Value '{}' has been set for Wild landscape topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setAnimalsTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = redisClient.getUserData(userId);

        botUser.getUserTopics().setIsAnimalsChosen(isMarked);
        redisClient.saveUserData(botUser);

        log.info("Value '{}' has been set for Wild animals topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setFlowersTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = redisClient.getUserData(userId);

        botUser.getUserTopics().setIsFlowersChosen(isMarked);
        redisClient.saveUserData(botUser);

        log.info("Value '{}' has been set for Wild flowers topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setDoneButtonClickedDataForUser(long userId, boolean isClicked) {
        BotUser botUser = redisClient.getUserData(userId);

        botUser.getUserSettings().setIsDoneClicked(isClicked);
        redisClient.saveUserData(botUser);

        log.info("Value '{}' has been set for Done topics button and user with id = '{}' in DB file", isClicked, userId);
    }

    public void setTimeDataForUser(long userId, String time) {
        BotUser botUser = redisClient.getUserData(userId);

        botUser.getUserSettings().setTime(time);
        redisClient.saveUserData(botUser);

        log.info("Value '{}' has been set for time property and user with id = '{}' in DB file", time, userId);
    }

    public void setTimeEnterModeDataForUser(long userId, boolean isMarked) {
        BotUser botUser = redisClient.getUserData(userId);

        botUser.getUserSettings().setIsTimeEnterMode(isMarked);
        redisClient.saveUserData(botUser);

        log.info("Value '{}' has been set for time enter mode property and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setIsNewUserForUser(long userId, boolean isNewUser) {
        BotUser botUser = redisClient.getUserData(userId);

        botUser.getUserSettings().setIsNewUser(isNewUser);
        redisClient.saveUserData(botUser);

        log.info("Value '{}' has been set for new user property and user with id = '{}' in DB file", isNewUser, userId);
    }

    public void setSilenceModeForUser(long userId) {
        BotUser botUser = redisClient.getUserData(userId);

        botUser.getUserSettings().setTime(SILENCE_TIME);
        redisClient.saveUserData(botUser);

        log.info("Silence mode has been set for user with id = '{}' in DB file", userId);
    }

    public boolean checkIsUserDoNotHaveActiveTopics(long userId) {
        return getUserTopics(userId).entrySet()
                .stream()
                .noneMatch(Map.Entry::getValue);
    }
}
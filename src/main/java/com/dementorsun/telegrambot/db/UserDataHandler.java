package com.dementorsun.telegrambot.db;

import com.dementorsun.telegrambot.db.dto.BotUser;
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

    private final FileHandler fileHandler;

    public boolean checkIsDataPresentForUser(long userId) {
        return Optional.ofNullable(fileHandler.getUserDataFromDbFile(userId)).isPresent();
    }

    public boolean checkIsNewUser(long userId) {
        return fileHandler.getUserDataFromDbFile(userId).getUserSettings().getIsNewUser();
    }

    public boolean getIsDoneButtonClickedForUser(long userId) {
        return fileHandler.getUserDataFromDbFile(userId).getUserSettings().getIsDoneClicked();
    }

    public boolean getIsTimeEnterMode(long userId) {
        return fileHandler.getUserDataFromDbFile(userId).getUserSettings().getIsTimeEnterMode();
    }

    public boolean checkTimeIsPresent(long userId) {
        boolean isTimePresent = true;
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        if (botUser.getUserSettings().getTime().equals(NONE_TIME)) {
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
                        .isNewUser(true)
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

        log.info("New user with id = '{}' has been saved to DB file", botUser.getUserInfo().getUserId());
    }

    public void setNasaTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.getUserTopics().setIsNasaChosen(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Nasa topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setCatTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.getUserTopics().setIsCatChosen(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Cat topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setDogTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.getUserTopics().setIsDogChosen(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Dog topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setPokemonTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.getUserTopics().setIsPokemonChosen(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Pokemon topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setQuoteTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.getUserTopics().setIsQuoteChosen(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Quote topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setMovieTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.getUserTopics().setIsMovieChosen(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Movie topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setTvShowTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.getUserTopics().setIsTvShowChosen(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for TV Show topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setDoneButtonClickedDataForUser(long userId, boolean isClicked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.getUserSettings().setIsDoneClicked(isClicked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Done topics button and user with id = '{}' in DB file", isClicked, userId);
    }

    public void setTimeDataForUser(long userId, String time) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.getUserSettings().setTime(time);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for time property and user with id = '{}' in DB file", time, userId);
    }

    public void setTimeEnterModeDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.getUserSettings().setIsTimeEnterMode(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for time enter mode property and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setIsNewUserForUser(long userId, boolean isNewUser) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.getUserSettings().setIsNewUser(isNewUser);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for new user property and user with id = '{}' in DB file", isNewUser, userId);
    }

    public Map<TopicsDict, Boolean> getUserTopics(long userId) {
        BotUser.UserTopic botUserTopics = fileHandler.getUserDataFromDbFile(userId).getUserTopics();
        Map<TopicsDict,Boolean> userTopics = new HashMap<>();

        userTopics.put(TopicsDict.NASA_TOPIC, botUserTopics.getIsNasaChosen());
        userTopics.put(TopicsDict.CAT_TOPIC, botUserTopics.getIsCatChosen());
        userTopics.put(TopicsDict.DOG_TOPIC, botUserTopics.getIsDogChosen());
        userTopics.put(TopicsDict.POKEMON_TOPIC, botUserTopics.getIsPokemonChosen());
        userTopics.put(TopicsDict.QUOTE_TOPIC, botUserTopics.getIsQuoteChosen());
        userTopics.put(TopicsDict.MOVIE_TOPIC, botUserTopics.getIsMovieChosen());
        userTopics.put(TopicsDict.TV_SHOW_TOPIC, botUserTopics.getIsTvShowChosen());

        return userTopics;
    }

    public String getUserTime(long userId) {
        return fileHandler.getUserDataFromDbFile(userId).getUserSettings().getTime();
    }

    public List<BotUser> getUsersByTime(String time) {
        return fileHandler.getUsersFromDbFile()
                .stream()
                .filter(botUser -> botUser.getUserSettings().getTime() != null)
                .filter(botUser -> time.equals(botUser.getUserSettings().getTime()))
                .collect(Collectors.toList());
    }

    public boolean checkIsUserDoNotHaveActiveTopics(long userId) {
        return getUserTopics(userId).entrySet()
                .stream()
                .noneMatch(Map.Entry::getValue);
    }

    public boolean checkIsSilenceModeActiveForUser(long userId) {
        boolean isStopModeActive = false;
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        if (SILENCE_TIME.equals(botUser.getUserSettings().getTime())) {
            isStopModeActive = true;
        }

        return isStopModeActive;
    }

    public void setSilenceModeForUser(long userId) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.getUserSettings().setTime(SILENCE_TIME);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Silence mode has been set for user with id = '{}' in DB file", userId);
    }
}
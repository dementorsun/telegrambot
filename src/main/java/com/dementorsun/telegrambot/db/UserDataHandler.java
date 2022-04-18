package com.dementorsun.telegrambot.db;

import com.dementorsun.telegrambot.bot.data.BotButtons;
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
                        .isNasa(false)
                        .isCat(false)
                        .isDog(false)
                        .isPokemon(false)
                        .isQuote(false)
                        .isMovie(false)
                        .isTvShow(false)
                        .build())
                .build();

        fileHandler.updateUserDataInDbFile(botUser);

        log.info("New user with id = '{}' has been saved to DB file", botUser.userInfo.userId);
    }

    public void setNasaTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsNasa(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Nasa topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setCatTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsCat(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Cat topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setDogTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsDog(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Dog topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setPokemonTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsPokemon(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Pokemon topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setQuoteTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsQuote(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Quote topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setMovieTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsMovie(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Value '{}' has been set for Movie topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setTvShowTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsTvShow(isMarked);
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

    public Map<BotButtons, Boolean> getUserTopics(long userId) {
        BotUser.UserTopic botUserTopics = fileHandler.getUserDataFromDbFile(userId).userTopics;
        Map<BotButtons,Boolean> userTopics = new HashMap<>();

        userTopics.put(BotButtons.NASA_TOPIC, botUserTopics.isNasa);
        userTopics.put(BotButtons.CAT_TOPIC, botUserTopics.isCat);
        userTopics.put(BotButtons.DOG_TOPIC, botUserTopics.isDog);
        userTopics.put(BotButtons.POKEMON_TOPIC, botUserTopics.isPokemon);
        userTopics.put(BotButtons.QUOTE_TOPIC, botUserTopics.isQuote);
        userTopics.put(BotButtons.MOVIE_TOPIC, botUserTopics.isMovie);
        userTopics.put(BotButtons.TV_SHOW_TOPIC, botUserTopics.isTvShow);

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
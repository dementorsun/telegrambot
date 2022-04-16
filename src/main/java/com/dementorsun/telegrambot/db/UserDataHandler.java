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

    private static final String SILENCE_MODE = "silence";

    private final FileHandler fileHandler;

    public boolean checkIsUserNew(long userId) {
        boolean isNewUser = false;
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        if (botUser == null) {
            isNewUser = true;
        }

        return isNewUser;
    }

    public boolean checkIsDoneButtonClickedForUser(long userId) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        return botUser.userSettings.isDoneClicked;
    }

    public boolean checkIsTimeEnterMode(long userId) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        return botUser.userSettings.isTimeEnterMode;
    }

    public boolean checkTimeIsPresent(long userId) {
        boolean isTimePresent = true;
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        if (botUser.userSettings.time.equals("none")) {
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
                        .time("none")
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

        log.info("New user with id = '{}' is saved to DB file", botUser.userInfo.userId);
    }

    public void setNasaTopicData(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsNasa(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Nasa topic is marked '{}' for user with id = '{}' in DB file", isMarked, userId);
    }

    public void setCatTopicData(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsCat(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Cat topic is marked '{}' for user with id = '{}' in DB file", isMarked, userId);
    }

    public void setDogTopicData(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsDog(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Dog topic is marked '{}' for user with id = '{}' in DB file", isMarked, userId);
    }

    public void setPokemonTopicData(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsPokemon(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Pokemon topic is marked '{}' for user with id = '{}' in DB file", isMarked, userId);
    }

    public void setQuoteTopicData(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsQuote(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Quote topic is marked '{}' for user with id = '{}' in DB file", isMarked, userId);
    }

    public void setMovieTopicData(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsMovie(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Movie topic is marked '{}' for user with id = '{}' in DB file", isMarked, userId);
    }

    public void setTvShowTopicData(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsTvShow(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Tv show topic is marked '{}' for user with id = '{}' in DB file", isMarked, userId);
    }

    public void setDoneButtonClickedData(long userId, boolean isClicked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userSettings.setIsDoneClicked(isClicked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Done topics button is marked '{}' for user with id = '{}' in DB file", isClicked, userId);
    }

    public void setTimeData(long userId, String time) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userSettings.setTime(time);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("'{}' time is updated for user with id = '{}' in DB file", time, userId);
    }

    public void setTimeEnterModeData(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userSettings.setIsTimeEnterMode(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Time enter mode is marked '{}' for user with id = '{}' in DB file", isMarked, userId);
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
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        return botUser.userSettings.time;
    }

    public List<BotUser> getUsersByTime(String time) {
        List<BotUser> userList = fileHandler.getAllUsersFromDbFile();

        userList = userList.stream()
                .filter(botUser -> botUser.userSettings.time.equals(time))
                .collect(Collectors.toList());

        return userList;
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

    public boolean checkIsSilenceModeActive(long userId) {
        boolean isStopModeActive = false;
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        if (botUser.userSettings.time.equals(SILENCE_MODE)) {
            isStopModeActive = true;
        }

        return isStopModeActive;
    }

    public void setSilenceModeForUser(long userId) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userSettings.setTime(SILENCE_MODE);
        fileHandler.updateUserDataInDbFile(botUser);
    }
}
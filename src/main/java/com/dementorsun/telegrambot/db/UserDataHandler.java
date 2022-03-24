package com.dementorsun.telegrambot.db;

import com.dementorsun.telegrambot.bot.data.BotButtons;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
@AllArgsConstructor
public class UserDataHandler {

    private final FileHandler fileHandler;

    public boolean checkIsUserNew(long userId) {
        boolean isNewUser = false;
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        if (botUser == null) {
            isNewUser = true;
        }

        log.info("User '{}' has DB parameter isNewUser = '{}'", userId, isNewUser);

        return isNewUser;
    }

    public boolean checkIsDoneButtonClickedForUser(long userId) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);
        boolean isDoneClicked = botUser.userSettings.isDoneClicked;

        log.info("User '{}' has DB parameter isDoneClicked = '{}'", userId, isDoneClicked);

        return isDoneClicked;
    }

    public boolean checkIsTimeEnterMode(long userId) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);
        boolean isTimeEnterMode = botUser.userSettings.isTimeEnterMode;

        log.info("User '{}' has DB parameter isTimeEnterMode = '{}'", userId, isTimeEnterMode);

        return isTimeEnterMode;
    }

    public boolean checkTimeIsPresent(long userId) {
        boolean isTimePresent = true;
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        if (botUser.userSettings.time.equals("none")) {
            isTimePresent = false;
        }

        log.info("User '{}' has isTimePresent = '{}'", userId, isTimePresent);

        return isTimePresent;
    }

    public void saveNewUserData(User user, Update update) {
        BotUser botUser = BotUser.builder()
                .userInfo(BotUser.UserInfo.builder()
                        .userId(user.getId())
                        .username(user.getUserName())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .userChatId(update.getMessage().getChatId().toString())
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
                        .isQuote(false)
                        .isMovie(false)
                        .isTvShow(false)
                        .build())
                .build();

        fileHandler.updateUserDataInDbFile(botUser);

        log.info("New user with id = '{}' is saved to DB file", botUser.userInfo.userId);
    }

    public void updateNasaTopicData(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsNasa(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Nasa topic is marked '{}' for user with id = '{}' in DB file", isMarked, userId);
    }

    public void updateCatTopicData(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsCat(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Cat topic is marked '{}' for user with id = '{}' in DB file", isMarked, userId);
    }

    public void updateDogTopicData(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsDog(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Dog topic is marked '{}' for user with id = '{}' in DB file", isMarked, userId);
    }

    public void updateQuoteTopicData(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsQuote(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Quote topic is marked '{}' for user with id = '{}' in DB file", isMarked, userId);
    }

    public void updateMovieTopicData(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsMovie(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Movie topic is marked '{}' for user with id = '{}' in DB file", isMarked, userId);
    }

    public void updateTvShowTopicData(long userId, boolean isMarked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userTopics.setIsTvShow(isMarked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Tv show topic is marked '{}' for user with id = '{}' in DB file", isMarked, userId);
    }

    public void updateDoneButtonClickedData(long userId, boolean isClicked) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userSettings.setIsDoneClicked(isClicked);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("Done topics button is marked '{}' for user with id = '{}' in DB file", isClicked, userId);
    }

    public void updateTimeData(long userId, String time) {
        BotUser botUser = fileHandler.getUserDataFromDbFile(userId);

        botUser.userSettings.setTime(time);
        fileHandler.updateUserDataInDbFile(botUser);

        log.info("'{}' time is updated for user with id = '{}' in DB file", time, userId);
    }

    public void updateTimeEnterModeData(long userId, boolean isMarked) {
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
        List<BotUser> userList = fileHandler.getAllUsers();

        userList = userList.stream()
                .filter(botUser -> botUser.userSettings.time.equals(time))
                .collect(Collectors.toList());

        return userList;
    }

    public String getUserChatId(BotUser botUser) {
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
}
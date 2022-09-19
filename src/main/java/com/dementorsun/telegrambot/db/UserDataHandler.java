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

        userTopics.put(TopicsDict.NASA_TOPIC, getNasaTopicDataForUser(botUserTopics, userId));
        userTopics.put(TopicsDict.NATURE_TOPIC, getNatureTopicDataForUser(botUserTopics, userId));
        userTopics.put(TopicsDict.ANIMALS_TOPIC, getAnimalsTopicDataForUser(botUserTopics, userId));
        userTopics.put(TopicsDict.FOREST_TOPIC, getForestTopicDataForUser(botUserTopics, userId));
        userTopics.put(TopicsDict.CAT_TOPIC, getCatTopicDataForUser(botUserTopics, userId));
        userTopics.put(TopicsDict.DOG_TOPIC, getDogTopicDataForUser(botUserTopics, userId));
        userTopics.put(TopicsDict.POKEMON_TOPIC, getPokemonTopicDataForUser(botUserTopics, userId));
        userTopics.put(TopicsDict.MOVIE_TOPIC, getMovieTopicDataForUser(botUserTopics, userId));
        userTopics.put(TopicsDict.TV_SHOW_TOPIC, getTvShowTopicDataForUser(botUserTopics, userId));
        userTopics.put(TopicsDict.ANIME_TOPIC, getAnimeTopicDataForUser(botUserTopics, userId));
        userTopics.put(TopicsDict.QUOTE_TOPIC, getQuoteTopicDataForUser(botUserTopics, userId));

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
                        .isNatureChosen(false)
                        .isAnimalsChosen(false)
                        .isForestChosen(false)
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

    public void setNatureTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = redisClient.getUserData(userId);

        botUser.getUserTopics().setIsNatureChosen(isMarked);
        redisClient.saveUserData(botUser);

        log.info("Value '{}' has been set for Nature topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setAnimalsTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = redisClient.getUserData(userId);

        botUser.getUserTopics().setIsAnimalsChosen(isMarked);
        redisClient.saveUserData(botUser);

        log.info("Value '{}' has been set for Wild animals topic and user with id = '{}' in DB file", isMarked, userId);
    }

    public void setForestTopicDataForUser(long userId, boolean isMarked) {
        BotUser botUser = redisClient.getUserData(userId);

        botUser.getUserTopics().setIsForestChosen(isMarked);
        redisClient.saveUserData(botUser);

        log.info("Value '{}' has been set for Forest topic and user with id = '{}' in DB file", isMarked, userId);
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

    private boolean getNasaTopicDataForUser(BotUser.UserTopic botUserTopics, long userId) {
        try {
            return botUserTopics.getIsNasaChosen();
        } catch (NullPointerException exception) {
            setNasaTopicDataForUser(userId, false);
            log.info("{} exception is occurred during get Nasa topic data, false is written", exception.toString());

            return false;
        }
    }

    private boolean getNatureTopicDataForUser(BotUser.UserTopic botUserTopics, long userId) {
        try {
            return botUserTopics.getIsNatureChosen();
        } catch (NullPointerException exception) {
            setNatureTopicDataForUser(userId, false);
            log.info("{} exception is occurred during get Nature topic data, false is written", exception.toString());

            return false;
        }
    }

    private boolean getAnimalsTopicDataForUser(BotUser.UserTopic botUserTopics, long userId) {
        try {
            return botUserTopics.getIsAnimalsChosen();
        } catch (NullPointerException exception) {
            setAnimalsTopicDataForUser(userId, false);
            log.info("{} exception is occurred during get Animals topic data, false is written", exception.toString());

            return false;
        }
    }

    private boolean getForestTopicDataForUser(BotUser.UserTopic botUserTopics, long userId) {
        try {
            return botUserTopics.getIsForestChosen();
        } catch (NullPointerException exception) {
            setForestTopicDataForUser(userId, false);
            log.info("{} exception is occurred during get Forest topic data, false is written", exception.toString());

            return false;
        }
    }

    private boolean getCatTopicDataForUser(BotUser.UserTopic botUserTopics, long userId) {
        try {
            return botUserTopics.getIsCatChosen();
        } catch (NullPointerException exception) {
            setCatTopicDataForUser(userId, false);
            log.info("{} exception is occurred during get Cat topic data, false is written", exception.toString());

            return false;
        }
    }

    private boolean getDogTopicDataForUser(BotUser.UserTopic botUserTopics, long userId) {
        try {
            return botUserTopics.getIsDogChosen();
        } catch (NullPointerException exception) {
            setDogTopicDataForUser(userId, false);
            log.info("{} exception is occurred during get Dog topic data, false is written", exception.toString());

            return false;
        }
    }

    private boolean getPokemonTopicDataForUser(BotUser.UserTopic botUserTopics, long userId) {
        try {
            return botUserTopics.getIsPokemonChosen();
        } catch (NullPointerException exception) {
            setPokemonTopicDataForUser(userId, false);
            log.info("{} exception is occurred during get Pokemon topic data, false is written", exception.toString());

            return false;
        }
    }

    private boolean getMovieTopicDataForUser(BotUser.UserTopic botUserTopics, long userId) {
        try {
            return botUserTopics.getIsMovieChosen();
        } catch (NullPointerException exception) {
            setMovieTopicDataForUser(userId, false);
            log.info("{} exception is occurred during get Movie topic data, false is written", exception.toString());

            return false;
        }
    }

    private boolean getTvShowTopicDataForUser(BotUser.UserTopic botUserTopics, long userId) {
        try {
            return botUserTopics.getIsTvShowChosen();
        } catch (NullPointerException exception) {
            setTvShowTopicDataForUser(userId, false);
            log.info("{} exception is occurred during get Tv Show topic data, false is written", exception.toString());

            return false;
        }
    }

    private boolean getAnimeTopicDataForUser(BotUser.UserTopic botUserTopics, long userId) {
        try {
            return botUserTopics.getIsAnimeChosen();
        } catch (NullPointerException exception) {
            setAnimeTopicDataForUser(userId, false);
            log.info("{} exception is occurred during get Anime topic data, false is written", exception.toString());

            return false;
        }
    }

    private boolean getQuoteTopicDataForUser(BotUser.UserTopic botUserTopics, long userId) {
        try {
            return botUserTopics.getIsQuoteChosen();
        } catch (NullPointerException exception) {
            setQuoteTopicDataForUser(userId, false);
            log.info("{} exception is occurred during get Quote topic data, false is written", exception.toString());

            return false;
        }
    }
}
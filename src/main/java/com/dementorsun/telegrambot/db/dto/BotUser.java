package com.dementorsun.telegrambot.db.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@RedisHash("BotUser")
public class BotUser {

    @Id
    Long userId;
    UserInfo userInfo;
    UserSetting userSettings;
    UserTopic userTopics;

    @Setter
    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserInfo {
        String username;
        String firstName;
        String lastName;
        Long userChatId;
    }

    @Setter
    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserSetting {
        String time;
        Boolean isDoneClicked;
        Boolean isTimeEnterMode;
        Boolean isNewUser;
    }

    @Setter
    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserTopic {
        Boolean isNasaChosen;
        Boolean isSceneryChosen;
        Boolean isAnimalsChosen;
        Boolean isFlowersChosen;
        Boolean isCatChosen;
        Boolean isDogChosen;
        Boolean isPokemonChosen;
        Boolean isQuoteChosen;
        Boolean isMovieChosen;
        Boolean isTvShowChosen;
        Boolean isAnimeChosen;
    }
}
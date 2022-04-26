package com.dementorsun.telegrambot.db;

import lombok.Builder;
import lombok.Setter;

@Builder
public class BotUser {

    UserInfo userInfo;
    UserSetting userSettings;
    UserTopic userTopics;

    @Setter
    @Builder
    public static class UserInfo {
        Long userId;
        String username;
        String firstName;
        String lastName;
        Long userChatId;
    }

    @Setter
    @Builder
    public static class UserSetting {
        String time;
        Boolean isDoneClicked;
        Boolean isTimeEnterMode;
        Boolean isNewUser;
    }

    @Setter
    @Builder
    public static class UserTopic {
        Boolean isNasaChosen;
        Boolean isCatChosen;
        Boolean isDogChosen;
        Boolean isPokemonChosen;
        Boolean isQuoteChosen;
        Boolean isMovieChosen;
        Boolean isTvShowChosen;
    }
}
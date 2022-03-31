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
        String userChatId;
    }

    @Setter
    @Builder
    public static class UserSetting {
        String time;
        Boolean isDoneClicked;
        Boolean isTimeEnterMode;
    }

    @Setter
    @Builder
    public static class UserTopic {
        Boolean isNasa;
        Boolean isCat;
        Boolean isDog;
        Boolean isPokemon;
        Boolean isQuote;
        Boolean isMovie;
        Boolean isTvShow;
    }
}
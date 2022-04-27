package com.dementorsun.telegrambot.db.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class BotUser {

    private UserInfo userInfo;
    private UserSetting userSettings;
    private UserTopic userTopics;

    @Setter
    @Getter
    @Builder
    public static class UserInfo {
        private Long userId;
        private String username;
        private String firstName;
        private String lastName;
        private Long userChatId;
    }

    @Setter
    @Getter
    @Builder
    public static class UserSetting {
        private String time;
        private Boolean isDoneClicked;
        private Boolean isTimeEnterMode;
        private Boolean isNewUser;
    }

    @Setter
    @Getter
    @Builder
    public static class UserTopic {
        private Boolean isNasaChosen;
        private Boolean isCatChosen;
        private Boolean isDogChosen;
        private Boolean isPokemonChosen;
        private Boolean isQuoteChosen;
        private Boolean isMovieChosen;
        private Boolean isTvShowChosen;
    }
}
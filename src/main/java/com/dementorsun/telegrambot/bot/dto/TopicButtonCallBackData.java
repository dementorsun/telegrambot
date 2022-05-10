package com.dementorsun.telegrambot.bot.dto;

import com.dementorsun.telegrambot.enums.TopicsDict;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class TopicButtonCallBackData {

    Boolean isTopicButton;
    TopicsDict topic;
    Boolean isMarked;
}
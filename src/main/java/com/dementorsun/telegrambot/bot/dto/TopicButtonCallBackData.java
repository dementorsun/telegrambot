package com.dementorsun.telegrambot.bot.dto;

import com.dementorsun.telegrambot.enums.TopicsDict;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TopicButtonCallBackData {

    private Boolean isTopicButton;
    private TopicsDict topic;
    private Boolean isMarked;
}
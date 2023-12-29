package com.dementorsun.telegrambot.topic.model;

import com.dementorsun.telegrambot.topic.enums.TopicsDict;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class TopicButtonCallBackData {

    TopicsDict topic;
    Boolean isMarked;
}
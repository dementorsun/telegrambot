package com.dementorsun.telegrambot.topic;

import com.dementorsun.telegrambot.topic.enums.TopicButtonsDict;
import com.dementorsun.telegrambot.topic.enums.TopicsDict;
import com.dementorsun.telegrambot.topic.model.TopicButtonCallBackData;
import com.dementorsun.telegrambot.utilities.ObjectParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.dementorsun.telegrambot.topic.enums.TopicButtonsDict.DONE_BUTTON;

/**
 * Class for generating bot topic button parameters.
 */
@Component
@RequiredArgsConstructor
public class TopicButtonParameterHandler {

    private final ObjectParser objectParser;

    /**
     * Method generates topic button text.
     * @param userTopics provides map with all topics from {@link TopicsDict} enum and selected state marks.
     * @param botButton provides {@link TopicButtonsDict} button.
     * @return text which will be displayed on current topic button.
     */
    public String getTopicButtonText(Map<TopicsDict, Boolean> userTopics, TopicButtonsDict botButton) {
        String topicButtonText;
        if (DONE_BUTTON.equals(botButton)) {
            topicButtonText = botButton.getButtonText();
        } else {
            boolean isTopicMarked = userTopics.get(botButton.getTopic());
            topicButtonText = isTopicMarked ? botButton.getButtonText() + " ✅" : botButton.getButtonText();
        }
        return topicButtonText;
    }

    /**
     * Method generates topic button call back data.
     * @param userTopics provides map with all topics from {@link TopicsDict} enum and selected state marks.
     * @param botButton provides {@link TopicButtonsDict} button.
     * @return call back data for further sending to Telegram API.
     */
    public String getTopicButtonCallBackData(Map<TopicsDict, Boolean> userTopics, TopicButtonsDict botButton) {
        String topicButtonCallBackData;
        if (DONE_BUTTON.equals(botButton)) {
            topicButtonCallBackData = objectParser.parseFromObject(new TopicButtonCallBackData(null, false));
        } else {
            boolean isTopicMarked = userTopics.get(botButton.getTopic());
            TopicsDict topic = botButton.getTopic();
            topicButtonCallBackData = isTopicMarked ?
                    objectParser.parseFromObject(new TopicButtonCallBackData(topic, true)) :
                    objectParser.parseFromObject(new TopicButtonCallBackData(topic, false));
        }

        return topicButtonCallBackData;
    }
}
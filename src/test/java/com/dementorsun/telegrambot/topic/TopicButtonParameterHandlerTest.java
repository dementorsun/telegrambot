package com.dementorsun.telegrambot.topic;

import com.dementorsun.telegrambot.TestData;
import com.dementorsun.telegrambot.topic.enums.TopicButtonsDict;
import com.dementorsun.telegrambot.topic.enums.TopicsDict;
import com.dementorsun.telegrambot.topic.model.TopicButtonCallBackData;
import com.dementorsun.telegrambot.utilities.ObjectParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static com.dementorsun.telegrambot.topic.enums.TopicButtonsDict.DONE_BUTTON;
import static com.dementorsun.telegrambot.topic.enums.TopicButtonsDict.NASA_BUTTON;
import static com.dementorsun.telegrambot.topic.enums.TopicsDict.NASA_TOPIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit level tests for TopicButtonParameterHandler")
class TopicButtonParameterHandlerTest extends TestData {

    @InjectMocks
    private TopicButtonParameterHandler topicButtonParameterHandler;
    @Mock
    private ObjectParser objectParser;

    @Test
    @DisplayName("Verify getTopicButtonText method for marked topic button")
    void getMarkedTopicButtonText() {
        checkGetTopicButtonText(true, NASA_BUTTON, NASA_BUTTON.getMarkedButtonText());
    }

    @Test
    @DisplayName("Verify getTopicButtonText method for not marked topic button")
    void getNotMarkedTopicButtonText() {
        checkGetTopicButtonText(false, NASA_BUTTON, NASA_BUTTON.getButtonText());
    }

    @Test
    @DisplayName("Verify getTopicButtonText method for Done topic button")
    void getDoneTopicButtonText() {
        checkGetTopicButtonText(true, DONE_BUTTON, DONE_BUTTON.getButtonText());
    }

    @Test
    @DisplayName("Verify getTopicButtonCallBackData method for marked topic button")
    void getMarkedTopicButtonCallBackData() {
        checkGetTopicButtonCallBackData(true, NASA_BUTTON, NASA_TOPIC);
    }

    @Test
    @DisplayName("Verify getTopicButtonCallBackData method for not marked topic button")
    void getNotMarkedTopicButtonCallBackData() {
        checkGetTopicButtonCallBackData(false, NASA_BUTTON, NASA_TOPIC);
    }

    @Test
    @DisplayName("Verify getTopicButtonCallBackData method for Done topic button")
    void getDoneTopicButtonCallBackData() {
        checkGetTopicButtonCallBackData(false, DONE_BUTTON, null);
    }

    private void checkGetTopicButtonText(boolean topicsMark, TopicButtonsDict topicButton, String expectedButtonText) {
        Map<TopicsDict, Boolean> userTopics = createUserTopics(topicsMark);

        String buttonText = topicButtonParameterHandler.getTopicButtonText(userTopics, topicButton);

        assertThat(buttonText)
                .as("Topic button text").isEqualTo(expectedButtonText);
    }

    private void checkGetTopicButtonCallBackData(boolean topicsMark, TopicButtonsDict topicButton, TopicsDict topic) {
        Map<TopicsDict, Boolean> userTopics = createUserTopics(topicsMark);

        topicButtonParameterHandler.getTopicButtonCallBackData(userTopics, topicButton);

        verify(objectParser).parseFromObject(new TopicButtonCallBackData(topic, topicsMark));
    }
}
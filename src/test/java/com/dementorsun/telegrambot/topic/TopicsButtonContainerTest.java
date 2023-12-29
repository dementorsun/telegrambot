package com.dementorsun.telegrambot.topic;

import com.dementorsun.telegrambot.topic.button.DoneTopicButton;
import com.dementorsun.telegrambot.topic.button.TopicButton;
import com.dementorsun.telegrambot.topic.enums.TopicsDict;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit level tests for TopicsButtonContainer")
class TopicsButtonContainerTest {

    @InjectMocks
    private TopicsButtonContainer topicsButtonContainer;
    @Mock
    private DoneTopicButton doneTopicButton;

    @Test
    @DisplayName("Verify returned TopicButton instances")
    void verifyContainerReturnTopicButton() {
        Arrays.stream(TopicsDict.values()).forEach(topic -> {
            TopicButton topicButton = topicsButtonContainer.retrieveTopicButton(topic);

            assertThat(topicButton).as("Returned topic button").isNotEqualTo(doneTopicButton);
        });
    }

    @Test
    @DisplayName("Verify returned DoneTopicButton instances")
    void verifyContainerReturnDoneTopicButton() {
        TopicButton topicButton = topicsButtonContainer.retrieveTopicButton(null);

        assertThat(topicButton).as("Returned Done topic button").isEqualTo(doneTopicButton);
    }
}
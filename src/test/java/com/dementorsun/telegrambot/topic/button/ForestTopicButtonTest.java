package com.dementorsun.telegrambot.topic.button;

import com.dementorsun.telegrambot.TestData;
import com.dementorsun.telegrambot.db.UserDataHandler;
import com.dementorsun.telegrambot.topic.TopicButtonHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit level test for ForestTopicButton")
class ForestTopicButtonTest extends TestData {

    @InjectMocks
    private ForestTopicButton forestTopicButton;
    @Mock
    private UserDataHandler userDataHandler;
    @Mock
    private TopicButtonHandler topicButtonHandler;

    @Test
    @DisplayName("Verify handleTopicButtonClick method for Forest button")
    void verifyHandleForestTopicButtonClick() {
        boolean isMarked = false;
        Update update = createUpdateCallBackQueryInstance();
        doNothing().when(userDataHandler).setForestTopicDataForUser(USER_ID, isMarked);

        forestTopicButton.handleTopicButtonClick(USER_ID, isMarked, update);

        verify(topicButtonHandler).editTopicsButtons(USER_ID, update);
    }
}
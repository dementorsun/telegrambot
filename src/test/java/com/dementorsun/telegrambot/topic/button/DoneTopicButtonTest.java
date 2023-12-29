package com.dementorsun.telegrambot.topic.button;

import com.dementorsun.telegrambot.TestData;
import com.dementorsun.telegrambot.db.UserDataHandler;
import com.dementorsun.telegrambot.topic.TopicButtonHandler;
import com.dementorsun.telegrambot.utilities.SendMessageGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.dementorsun.telegrambot.enums.BotMessages.FINISH_TOPICS_TUTORIAL_MESSAGE;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit level test for DoneTopicButton")
class DoneTopicButtonTest extends TestData {

    @InjectMocks
    private DoneTopicButton doneTopicButton;
    @Mock
    private UserDataHandler userDataHandler;
    @Mock
    private SendMessageGenerator sendMessageGenerator;
    @Mock
    private TopicButtonHandler topicButtonHandler;

    @Test
    @DisplayName("Verify handleTopicButtonClick method for Done button")
    void verifyHandleDoneTopicButtonClick() {
        boolean isMarked = false;
        Update update = createUpdateCallBackQueryInstance();
        doNothing().when(userDataHandler).setDoneButtonClickedDataForUser(USER_ID, isMarked);

        doneTopicButton.handleTopicButtonClick(USER_ID, isMarked, update);

        verify(topicButtonHandler).editTopicsButtons(USER_ID, update);
    }

    @Test
    @DisplayName("Verify handleTutorialDoneButtonClick method for tutorial Done button clicked")
    void verifyHandleTutorialDoneTopicButtonClick() {
        boolean isMarked = true;
        Update update = createUpdateCallBackQueryInstance();
        doNothing().when(userDataHandler).setDoneButtonClickedDataForUser(USER_ID, isMarked);
        doNothing().when(userDataHandler).setTimeEnterModeDataForUser(USER_ID, isMarked);
        doNothing().when(userDataHandler).setIsNewUserForUser(USER_ID, false);

        doneTopicButton.handleTutorialDoneButtonClick(update, USER_ID);

        verify(sendMessageGenerator).createSendMessageFromCallBack(update, FINISH_TOPICS_TUTORIAL_MESSAGE.getMessage());
    }
}
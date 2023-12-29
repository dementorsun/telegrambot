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
@DisplayName("Unit level test for AnimeTopicButton")
class AnimeTopicButtonTest extends TestData {

    @InjectMocks
    private AnimeTopicButton animeTopicButton;
    @Mock
    private UserDataHandler userDataHandler;
    @Mock
    private TopicButtonHandler topicButtonHandler;

    @Test
    @DisplayName("Verify handleTopicButtonClick method for Anime button")
    void verifyHandleAnimeTopicButtonClick() {
        boolean isMarked = true;
        Update update = createUpdateCallBackQueryInstance();
        doNothing().when(userDataHandler).setAnimeTopicDataForUser(USER_ID, isMarked);

        animeTopicButton.handleTopicButtonClick(USER_ID, isMarked, update);

        verify(topicButtonHandler).editTopicsButtons(USER_ID, update);
    }
}
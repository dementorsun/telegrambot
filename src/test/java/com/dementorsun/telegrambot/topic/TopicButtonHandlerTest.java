package com.dementorsun.telegrambot.topic;

import com.dementorsun.telegrambot.TestData;
import com.dementorsun.telegrambot.db.UserDataHandler;
import com.dementorsun.telegrambot.topic.enums.TopicButtonsDict;
import com.dementorsun.telegrambot.topic.enums.TopicsDict;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit level tests for TopicsButtonHandler")
class TopicButtonHandlerTest extends TestData {

    private static final int TOPICS_AMOUNT = TopicsDict.values().length;

    @InjectMocks
    private TopicButtonHandler topicButtonHandler;
    @Mock
    private UserDataHandler userDataHandler;
    @Mock
    private TopicButtonParameterHandler topicButtonParameterHandler;


    @Test
    @DisplayName("Verify editTopicsButtons method")
    void verifyEditTopicButtons() {
        TopicButtonHandler spyTopicButtonHandler = spy(topicButtonHandler);
        Update update = createUpdateCallBackQueryInstance();
        doReturn(new InlineKeyboardMarkup()).when(spyTopicButtonHandler).setTopicsButtons(USER_ID);

        EditMessageReplyMarkup editMessageReplyMarkup = spyTopicButtonHandler.editTopicsButtons(USER_ID, update);

        assertThat(editMessageReplyMarkup.getChatId())
                .as("Call back query chat id").isEqualTo(String.valueOf(CHAT_ID));
        assertThat(editMessageReplyMarkup.getMessageId())
                .as("Call back query message id").isEqualTo(MESSAGE_ID);
        assertThat(editMessageReplyMarkup.getReplyMarkup())
                .as("Call back query reply markup").isExactlyInstanceOf(InlineKeyboardMarkup.class);
    }

    @Test
    @DisplayName("Verify setTopicsButtons method when no topic are active and Done button hasn't been clicked")
    void verifySetTopicsButtonsNoTopicsActiveAndDoneNotClicked() {
        verifySetTopics(false, false, TOPICS_AMOUNT);
    }

    @Test
    @DisplayName("Verify setTopicsButtons method when any topics are active and Done button hasn't been clicked")
    void verifySetTopicsButtonsAnyTopicsActiveAndDoneNotClicked() {
        verifySetTopics(false, true, TOPICS_AMOUNT + 1);
    }

    @Test
    @DisplayName("Verify setTopicsButtons method when any topics are active and Done button has been clicked")
    void verifySetTopicsButtonsAnyTopicsActiveAndDoneClicked() {
        verifySetTopics(true, true, TOPICS_AMOUNT);
    }

    @Test
    @DisplayName("Verify setTopicsButtons method when no topics are active and Done button has been clicked")
    void verifySetTopicsButtonsNoTopicsActiveAndDoneClicked() {
        verifySetTopics(true, false, TOPICS_AMOUNT);
    }

    private void verifySetTopics(boolean isDoneButtonClicked, boolean isAnyTopicActive, int buttonsAmount) {
        final String buttonText = "Topic button text";
        final String buttonCallBackData = "Topic button call back data";
        when(userDataHandler.getUserTopics(USER_ID)).thenReturn(new HashMap<>());
        when(userDataHandler.getIsDoneButtonClickedForUser(USER_ID)).thenReturn(isDoneButtonClicked);
        when(userDataHandler.getIsAnyTopicActiveForUser(USER_ID)).thenReturn(isAnyTopicActive);
        when(topicButtonParameterHandler.getTopicButtonText(anyMap(), any(TopicButtonsDict.class)))
                .thenReturn(buttonText);
        when(topicButtonParameterHandler.getTopicButtonCallBackData(anyMap(), any(TopicButtonsDict.class)))
                .thenReturn(buttonCallBackData);

        InlineKeyboardMarkup inlineKeyboardMarkup = topicButtonHandler.setTopicsButtons(USER_ID);

        assertThat(inlineKeyboardMarkup.getKeyboard())
                .as("Topics buttons amount").hasSize(buttonsAmount);
        inlineKeyboardMarkup.getKeyboard().forEach(button -> {
            assertThat(button.stream().findFirst().orElseThrow().getText())
                    .as("Button text").isEqualTo(buttonText);
            assertThat(button.stream().findFirst().orElseThrow().getCallbackData())
                    .as("Button call back data").isEqualTo(buttonCallBackData);
        });
    }
}
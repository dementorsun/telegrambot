package com.dementorsun.telegrambot.command;

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
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.dementorsun.telegrambot.enums.BotMessages.CHANGE_TOPICS_MESSAGE;
import static com.dementorsun.telegrambot.enums.BotMessages.TOPICS_WITH_SILENCE_MODE_MESSAGE;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit level tests for ChangeTopicsMenuCommand")
class ChangeTopicsMenuCommandCommandTest extends TestData {

    @InjectMocks
    private ChangeTopicsMenuCommand changeTopicsMenuCommand;
    @Mock
    private UserDataHandler userDataHandler;
    @Mock
    private SendMessageGenerator sendMessageGenerator;
    @Mock
    private TopicButtonHandler topicButtonHandler;
    @Mock
    private SendMessage sendMessage;

    @Test
    @DisplayName("Verify Change Topics menu command when silence mode is active")
    void verifyChangeTopicsCommandWhenSilenceModeIsActive() {
        Update update = createUpdateMessageInstance();
        when(userDataHandler.checkIsSilenceModeActiveForUser(USER_ID)).thenReturn(true);

        changeTopicsMenuCommand.handleMenuCommand(update);

        verify(sendMessageGenerator).createSendMessageFromMessage(update,
                                                                   TOPICS_WITH_SILENCE_MODE_MESSAGE.getMessage());
    }

    @Test
    @DisplayName("Verify Change Topics menu command when silence mode is inactive")
    void verifyChangeTopicsCommandWhenSilenceModeIsInactive() {
        Update update = createUpdateMessageInstance();
        when(userDataHandler.checkIsSilenceModeActiveForUser(USER_ID)).thenReturn(false);
        when(sendMessageGenerator.createSendMessageFromMessage(update,
                                                                CHANGE_TOPICS_MESSAGE.getMessage())).thenReturn(sendMessage);

        changeTopicsMenuCommand.handleMenuCommand(update);

        verify(sendMessageGenerator).createSendMessageFromMessage(update, CHANGE_TOPICS_MESSAGE.getMessage());
        verify(topicButtonHandler).setTopicsButtons(USER_ID);
    }
}
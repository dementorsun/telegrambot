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

import static com.dementorsun.telegrambot.enums.BotMessages.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit level tests for StartMenuCommand")
class StartMenuCommandCommandTest extends TestData {

    @InjectMocks
    private StartMenuCommand startMenuCommand;
    @Mock
    private UserDataHandler userDataHandler;
    @Mock
    private TopicButtonHandler topicButtonHandler;
    @Mock
    private SendMessageGenerator sendMessageGenerator;
    @Mock
    private SendMessage sendMessage;

    @Test
    @DisplayName("Verify start menu command when first time login for user")
    void verifyStartCommandWhenFirstTimeLogin() {
        Update update = createUpdateMessageInstance();
        when(userDataHandler.checkIsDataPresentForUser(USER_ID)).thenReturn(false);
        when(sendMessageGenerator.createSendMessageFromMessage(update, WELCOME_MESSAGE.getMessage()))
                .thenReturn(sendMessage);

        startMenuCommand.handleMenuCommand(update);

        verify(sendMessageGenerator).createSendMessageFromMessage(update, WELCOME_MESSAGE.getMessage());
        verify(topicButtonHandler).setTopicsButtons(USER_ID);
    }

    @Test
    @DisplayName("Verify start menu command when silence mode is active")
    void verifyStartCommandWhenSilenceModeIsActive() {
        Update update = createUpdateMessageInstance();
        when(userDataHandler.checkIsDataPresentForUser(USER_ID)).thenReturn(true);
        when(userDataHandler.checkIsSilenceModeActiveForUser(USER_ID)).thenReturn(true);

        startMenuCommand.handleMenuCommand(update);

        verify(sendMessageGenerator).createSendMessageFromMessage(update,
                String.format(WELCOME_BACK_WITH_SILENCE_MODE_MESSAGE.getMessage(), FIRST_NAME));
    }

    @Test
    @DisplayName("Verify start menu command when user is back")
    void verifyStartCommandWhenUserIsBack() {
        Update update = createUpdateMessageInstance();
        when(userDataHandler.checkIsDataPresentForUser(USER_ID)).thenReturn(true);
        when(userDataHandler.checkIsSilenceModeActiveForUser(USER_ID)).thenReturn(false);

        startMenuCommand.handleMenuCommand(update);

        verify(sendMessageGenerator).createSendMessageFromMessage(update,
                String.format(WELCOME_BACK_MESSAGE.getMessage(), FIRST_NAME));
    }
}
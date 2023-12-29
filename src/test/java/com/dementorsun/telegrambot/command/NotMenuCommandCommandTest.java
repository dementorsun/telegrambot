package com.dementorsun.telegrambot.command;

import com.dementorsun.telegrambot.TestData;
import com.dementorsun.telegrambot.db.UserDataHandler;
import com.dementorsun.telegrambot.utilities.BotMessageGenerator;
import com.dementorsun.telegrambot.utilities.SendMessageGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.dementorsun.telegrambot.enums.BotMessages.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit level tests for NotMenuCommand")
class NotMenuCommandCommandTest extends TestData {

    @InjectMocks
    private NotMenuCommand notMenuCommand;
    @Mock
    private UserDataHandler userDataHandler;
    @Mock
    private SendMessageGenerator sendMessageGenerator;
    @Mock
    private BotMessageGenerator botMessageGenerator;

    @Test
    @DisplayName("Verify not menu command when change time mode is active and entered time is correct")
    void verifyNotCommandWhenChangeTimeModeIsActiveAndTimeIsCorrect() {
        Update update = createUpdateMessageInstanceWithText("12:30");
        when(userDataHandler.getIsTimeEnterMode(USER_ID)).thenReturn(true);
        when(userDataHandler.checkTimeIsPresent(USER_ID)).thenReturn(true);

        notMenuCommand.handleMenuCommand(update);

        verify(sendMessageGenerator).createSendMessageFromMessage(update, TIME_ARE_CHANGED_MESSAGE.getMessage());
    }

    @Test
    @DisplayName("Verify not menu command when change time mode is active and entered time is correct during tutorial flow")
    void verifyNotCommandWhenChangeTimeModeIsActiveAndTimeIsCorrectDuringTutorial() {
        Update update = createUpdateMessageInstanceWithText("12:30");
        when(userDataHandler.getIsTimeEnterMode(USER_ID)).thenReturn(true);
        when(userDataHandler.checkTimeIsPresent(USER_ID)).thenReturn(false);

        notMenuCommand.handleMenuCommand(update);

        verify(sendMessageGenerator).createSendMessageFromMessage(update, COMPLETE_TUTORIAL_MESSAGE.getMessage());
    }

    @Test
    @DisplayName("Verify not menu command when change time mode is active and entered time is not correct")
    void verifyNotCommandWhenChangeTimeModeIsActiveAndTimeNotCorrect() {
        Update update = createUpdateMessageInstanceWithText("dead moskoviya");
        when(userDataHandler.getIsTimeEnterMode(USER_ID)).thenReturn(true);

        notMenuCommand.handleMenuCommand(update);

        verify(sendMessageGenerator).createSendMessageFromMessage(update, FAIL_TIME_FORMAT_MESSAGE.getMessage());
    }

    @Test
    @DisplayName("Verify not menu command when change time mode is not active")
    void verifyNotCommandWhenChangeTimeModeIsNotActive() {
        Update update = createUpdateMessageInstance();
        when(userDataHandler.getIsTimeEnterMode(USER_ID)).thenReturn(false);

        notMenuCommand.handleMenuCommand(update);

        verify(sendMessageGenerator).createSendMessageFromMessage(update,
                                                                   botMessageGenerator.generateRandomBotMessage());
    }
}
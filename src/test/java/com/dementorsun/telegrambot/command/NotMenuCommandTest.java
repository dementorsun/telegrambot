package com.dementorsun.telegrambot.command;

import com.dementorsun.telegrambot.db.UserDataHandler;
import com.dementorsun.telegrambot.utilities.BotMessageGenerator;
import com.dementorsun.telegrambot.utilities.SendMessageObjectGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.dementorsun.telegrambot.enums.BotMessages.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Unit level tests for NotMenuCommand")
class NotMenuCommandTest extends TestData {

    @InjectMocks
    private NotMenuCommand notMenuCommand;
    @Mock
    private UserDataHandler userDataHandler;
    @Mock
    private SendMessageObjectGenerator sendMessageObjectGenerator;
    @Mock
    private BotMessageGenerator botMessageGenerator;

    @Test
    @DisplayName("Verify not menu command when change time mode is active and entered time is correct")
    void verifyNotCommandWhenChangeTimeModeIsActiveAndTimeIsCorrect() {
        Update update = createUpdateInstanceWithText("12:30");
        when(userDataHandler.getIsTimeEnterMode(USER_ID)).thenReturn(true);
        when(userDataHandler.checkTimeIsPresent(USER_ID)).thenReturn(true);

        notMenuCommand.handleMenuCommand(update);

        verify(sendMessageObjectGenerator).createSendMessageObject(update, TIME_ARE_CHANGED_MESSAGE.getMessage());
    }

    @Test
    @DisplayName("Verify not menu command when change time mode is active and entered time is correct during tutorial flow")
    void verifyNotCommandWhenChangeTimeModeIsActiveAndTimeIsCorrectDuringTutorial() {
        Update update = createUpdateInstanceWithText("12:30");
        when(userDataHandler.getIsTimeEnterMode(USER_ID)).thenReturn(true);
        when(userDataHandler.checkTimeIsPresent(USER_ID)).thenReturn(false);

        notMenuCommand.handleMenuCommand(update);

        verify(sendMessageObjectGenerator).createSendMessageObject(update, COMPLETE_TUTORIAL_MESSAGE.getMessage());
    }

    @Test
    @DisplayName("Verify not menu command when change time mode is active and entered time is not correct")
    void verifyNotCommandWhenChangeTimeModeIsActiveAndTimeNotCorrect() {
        Update update = createUpdateInstanceWithText("dead moskoviya");
        when(userDataHandler.getIsTimeEnterMode(USER_ID)).thenReturn(true);

        notMenuCommand.handleMenuCommand(update);

        verify(sendMessageObjectGenerator).createSendMessageObject(update, FAIL_TIME_FORMAT_MESSAGE.getMessage());
    }

    @Test
    @DisplayName("Verify not menu command when change time mode is not active")
    void verifyNotCommandWhenChangeTimeModeIsNotActive() {
        Update update = createUpdateInstance();
        when(userDataHandler.getIsTimeEnterMode(USER_ID)).thenReturn(false);

        notMenuCommand.handleMenuCommand(update);

        verify(sendMessageObjectGenerator).createSendMessageObject(update,
                                                                   botMessageGenerator.generateRandomBotMessage());
    }
}
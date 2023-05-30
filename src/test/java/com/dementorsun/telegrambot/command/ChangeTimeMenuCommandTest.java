package com.dementorsun.telegrambot.command;

import com.dementorsun.telegrambot.db.UserDataHandler;
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

import static com.dementorsun.telegrambot.enums.BotMessages.TIME_CHANGING_MESSAGE;
import static com.dementorsun.telegrambot.enums.BotMessages.TIME_CHANGING_WITH_SILENCE_MODE;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Unit level tests for ChangeTimeMenuCommand")
class ChangeTimeMenuCommandTest extends TestData {

    @InjectMocks
    private ChangeTimeMenuCommand changeTimeMenuCommand;
    @Mock
    private UserDataHandler userDataHandler;
    @Mock
    private SendMessageObjectGenerator sendMessageObjectGenerator;

    @Test
    @DisplayName("Verify Change Time menu command when silence mode is active")
    void verifyChangeTimeCommandWhenSilenceModeIsActive() {
        Update update = createUpdateInstance();
        when(userDataHandler.checkIsSilenceModeActiveForUser(USER_ID)).thenReturn(true);

        changeTimeMenuCommand.handleMenuCommand(update);

        verify(sendMessageObjectGenerator).createSendMessageObject(update,
                                                                   TIME_CHANGING_WITH_SILENCE_MODE.getMessage());
    }

    @Test
    @DisplayName("Verify Change Time menu command when silence mode is inactive")
    void verifyChangeTimeCommandWhenSilenceModeIsInactive() {
        Update update = createUpdateInstance();
        String time = "12:00";
        when(userDataHandler.checkIsSilenceModeActiveForUser(USER_ID)).thenReturn(false);
        when(userDataHandler.getUserTime(USER_ID)).thenReturn(time);

        changeTimeMenuCommand.handleMenuCommand(update);

        verify(sendMessageObjectGenerator).createSendMessageObject(update,
                                                                   String.format(TIME_CHANGING_MESSAGE.getMessage(), time));
    }
}
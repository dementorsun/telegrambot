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

import static com.dementorsun.telegrambot.enums.BotMessages.ACTIVATE_SILENCE_MODE_MESSAGE;
import static com.dementorsun.telegrambot.enums.BotMessages.SILENCE_MODE_IS_ALREADY_ACTIVE_MESSAGE;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Unit level tests for SilentModeMenuCommand")
class SilenceModeMenuCommandTest extends TestData {

    @InjectMocks
    private SilenceModeMenuCommand silenceModeMenuCommand;
    @Mock
    private UserDataHandler userDataHandler;
    @Mock
    private SendMessageObjectGenerator sendMessageObjectGenerator;

    @Test
    @DisplayName("Verify Silence Mode menu command when silence mode is active")
    void verifySilenceModeCommandWhenSilenceModeIsActive() {
        Update update = createUpdateInstance();
        when(userDataHandler.checkIsSilenceModeActiveForUser(USER_ID)).thenReturn(true);

        silenceModeMenuCommand.handleMenuCommand(update);

        verify(sendMessageObjectGenerator).createSendMessageObject(update,
                                                                   SILENCE_MODE_IS_ALREADY_ACTIVE_MESSAGE.getMessage());
    }

    @Test
    @DisplayName("Verify Silence Mode menu command when silence mode is inactive")
    void verifySilenceModeCommandWhenSilenceModeIsInactive() {
        Update update = createUpdateInstance();
        when(userDataHandler.checkIsSilenceModeActiveForUser(USER_ID)).thenReturn(false);

        silenceModeMenuCommand.handleMenuCommand(update);

        verify(userDataHandler).setSilenceModeForUser(USER_ID);
        verify(sendMessageObjectGenerator).createSendMessageObject(update, ACTIVATE_SILENCE_MODE_MESSAGE.getMessage());
    }
}
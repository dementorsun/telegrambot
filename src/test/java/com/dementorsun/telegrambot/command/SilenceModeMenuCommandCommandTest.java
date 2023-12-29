package com.dementorsun.telegrambot.command;

import com.dementorsun.telegrambot.TestData;
import com.dementorsun.telegrambot.db.UserDataHandler;
import com.dementorsun.telegrambot.utilities.SendMessageGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.dementorsun.telegrambot.enums.BotMessages.ACTIVATE_SILENCE_MODE_MESSAGE;
import static com.dementorsun.telegrambot.enums.BotMessages.SILENCE_MODE_IS_ALREADY_ACTIVE_MESSAGE;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit level tests for SilentModeMenuCommand")
class SilenceModeMenuCommandCommandTest extends TestData {

    @InjectMocks
    private SilenceModeMenuCommand silenceModeMenuCommand;
    @Mock
    private UserDataHandler userDataHandler;
    @Mock
    private SendMessageGenerator sendMessageGenerator;

    @Test
    @DisplayName("Verify Silence Mode menu command when silence mode is active")
    void verifySilenceModeCommandWhenSilenceModeIsActive() {
        Update update = createUpdateMessageInstance();
        when(userDataHandler.checkIsSilenceModeActiveForUser(USER_ID)).thenReturn(true);

        silenceModeMenuCommand.handleMenuCommand(update);

        verify(sendMessageGenerator).createSendMessageFromMessage(update,
                                                                   SILENCE_MODE_IS_ALREADY_ACTIVE_MESSAGE.getMessage());
    }

    @Test
    @DisplayName("Verify Silence Mode menu command when silence mode is inactive")
    void verifySilenceModeCommandWhenSilenceModeIsInactive() {
        Update update = createUpdateMessageInstance();
        when(userDataHandler.checkIsSilenceModeActiveForUser(USER_ID)).thenReturn(false);

        silenceModeMenuCommand.handleMenuCommand(update);

        verify(userDataHandler).setSilenceModeForUser(USER_ID);
        verify(sendMessageGenerator).createSendMessageFromMessage(update, ACTIVATE_SILENCE_MODE_MESSAGE.getMessage());
    }
}
package com.dementorsun.telegrambot.command;

import com.dementorsun.telegrambot.bot.buttons.MessageButtons;
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
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.dementorsun.telegrambot.enums.BotMessages.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Unit level tests for StartMenuCommand")
class StartMenuCommandTest extends TestData {

    @InjectMocks
    private StartMenuCommand startMenuCommand;
    @Mock
    private UserDataHandler userDataHandler;
    @Mock
    private MessageButtons messageButtons;
    @Mock
    private SendMessageObjectGenerator sendMessageObjectGenerator;
    @Mock
    private SendMessage sendMessage;

    @Test
    @DisplayName("Verify start menu command when first time login for user")
    void verifyStartCommandWhenFirstTimeLogin() {
        Update update = createUpdateInstance();
        when(userDataHandler.checkIsDataPresentForUser(USER_ID)).thenReturn(false);
        when(sendMessageObjectGenerator.createSendMessageObject(update, WELCOME_MESSAGE.getMessage()))
                .thenReturn(sendMessage);

        startMenuCommand.handleMenuCommand(update);

        verify(sendMessageObjectGenerator).createSendMessageObject(update, WELCOME_MESSAGE.getMessage());
        verify(messageButtons).setTopicsButtons(USER_ID);
    }

    @Test
    @DisplayName("Verify start menu command when silence mode is active")
    void verifyStartCommandWhenSilenceModeIsActive() {
        Update update = createUpdateInstance();
        when(userDataHandler.checkIsDataPresentForUser(USER_ID)).thenReturn(true);
        when(userDataHandler.checkIsSilenceModeActiveForUser(USER_ID)).thenReturn(true);

        startMenuCommand.handleMenuCommand(update);

        verify(sendMessageObjectGenerator).createSendMessageObject(update,
                String.format(WELCOME_BACK_WITH_SILENCE_MODE_MESSAGE.getMessage(), FIRST_NAME));
    }

    @Test
    @DisplayName("Verify start menu command when user is back")
    void verifyStartCommandWhenUserIsBack() {
        Update update = createUpdateInstance();
        when(userDataHandler.checkIsDataPresentForUser(USER_ID)).thenReturn(true);
        when(userDataHandler.checkIsSilenceModeActiveForUser(USER_ID)).thenReturn(false);

        startMenuCommand.handleMenuCommand(update);

        verify(sendMessageObjectGenerator).createSendMessageObject(update,
                String.format(WELCOME_BACK_MESSAGE.getMessage(), FIRST_NAME));
    }
}
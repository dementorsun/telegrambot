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

import static com.dementorsun.telegrambot.enums.BotMessages.CHANGE_TOPICS_MESSAGE;
import static com.dementorsun.telegrambot.enums.BotMessages.TOPICS_WITH_SILENCE_MODE_MESSAGE;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Unit level tests for ChangeTopicsMenuCommand")
class ChangeTopicsMenuCommandTest extends TestData {

    @InjectMocks
    private ChangeTopicsMenuCommand changeTopicsMenuCommand;
    @Mock
    private UserDataHandler userDataHandler;
    @Mock
    private SendMessageObjectGenerator sendMessageObjectGenerator;
    @Mock
    private MessageButtons messageButtons;
    @Mock
    private SendMessage sendMessage;

    @Test
    @DisplayName("Verify Change Topics menu command when silence mode is active")
    void verifyChangeTopicsCommandWhenSilenceModeIsActive() {
        Update update = createUpdateInstance();
        when(userDataHandler.checkIsSilenceModeActiveForUser(USER_ID)).thenReturn(true);

        changeTopicsMenuCommand.handleMenuCommand(update);

        verify(sendMessageObjectGenerator).createSendMessageObject(update,
                                                                   TOPICS_WITH_SILENCE_MODE_MESSAGE.getMessage());
    }

    @Test
    @DisplayName("Verify Change Topics menu command when silence mode is inactive")
    void verifyChangeTopicsCommandWhenSilenceModeIsInactive() {
        Update update = createUpdateInstance();
        when(userDataHandler.checkIsSilenceModeActiveForUser(USER_ID)).thenReturn(false);
        when(sendMessageObjectGenerator.createSendMessageObject(update,
                                                                CHANGE_TOPICS_MESSAGE.getMessage())).thenReturn(sendMessage);

        changeTopicsMenuCommand.handleMenuCommand(update);

        verify(sendMessageObjectGenerator).createSendMessageObject(update, CHANGE_TOPICS_MESSAGE.getMessage());
        verify(messageButtons).setTopicsButtons(USER_ID);
        verify(userDataHandler).setDoneButtonClickedDataForUser(USER_ID, false);
    }
}
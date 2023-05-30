package com.dementorsun.telegrambot.command;

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

import static com.dementorsun.telegrambot.enums.BotMessages.UNKNOWN_COMMAND_MESSAGE;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Unit level tests for UnknownMenuCommand")
class UnknownMenuCommandTest extends TestData {

    @InjectMocks
    private UnknownMenuCommand unknownMenuCommand;
    @Mock
    private SendMessageObjectGenerator sendMessageObjectGenerator;

    @Test
    @DisplayName("Verify Unknown menu command")
    void verifyUnknownCommand() {
        Update update = createUpdateInstance();

        unknownMenuCommand.handleMenuCommand(update);

        verify(sendMessageObjectGenerator).createSendMessageObject(update, UNKNOWN_COMMAND_MESSAGE.getMessage());
    }
}
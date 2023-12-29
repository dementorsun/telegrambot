package com.dementorsun.telegrambot.command;

import com.dementorsun.telegrambot.TestData;
import com.dementorsun.telegrambot.utilities.SendMessageGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.dementorsun.telegrambot.enums.BotMessages.UNKNOWN_COMMAND_MESSAGE;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit level tests for UnknownMenuCommand")
class UnknownMenuCommandCommandTest extends TestData {

    @InjectMocks
    private UnknownMenuCommand unknownMenuCommand;
    @Mock
    private SendMessageGenerator sendMessageGenerator;

    @Test
    @DisplayName("Verify Unknown menu command")
    void verifyUnknownCommand() {
        Update update = createUpdateMessageInstance();

        unknownMenuCommand.handleMenuCommand(update);

        verify(sendMessageGenerator).createSendMessageFromMessage(update, UNKNOWN_COMMAND_MESSAGE.getMessage());
    }
}
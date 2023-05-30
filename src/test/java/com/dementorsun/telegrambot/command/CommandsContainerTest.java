package com.dementorsun.telegrambot.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Unit level tests for CommandContainer")
class CommandsContainerTest {

    @InjectMocks
    private CommandsContainer commandsContainer;
    @Mock
    private UnknownMenuCommand unknownMenuCommand;

    @Test
    @DisplayName("Verify returned MenuCommand instances")
    void verifyContainerReturnMenuCommands() {
        Arrays.stream(MenuCommandName.values()).forEach(commandName -> {
            MenuCommand menuCommand = commandsContainer.retrieveMenuCommand(commandName.getCommandName());

            assertThat(menuCommand).as("Returned menu command").isNotEqualTo(unknownMenuCommand);
        });
    }

    @Test
    @DisplayName("Verify returned UnknownMenuCommand instance")
    void verifyContainerReturnUnknownMenuCommand() {
        String command = "/unknown_command";
        MenuCommand menuCommand = commandsContainer.retrieveMenuCommand(command);

        assertThat(menuCommand).as("Returned Unknown menu command").isEqualTo(unknownMenuCommand);
    }
}
package com.dementorsun.telegrambot.command;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.dementorsun.telegrambot.command.MenuCommandName.*;

/**
 * Container for {@link MenuCommand} commands which is using for defying which command user entered.
 */

@Component
public class CommandsContainer {
    private final Map<String, MenuCommand> commands;
    private final UnknownMenuCommand unknownMenuCommand;

    public CommandsContainer (StartMenuCommand startMenuCommand,
                              ChangeTopicsMenuCommand changeTopicsMenuCommand,
                              ChangeTimeMenuCommand changeTimeMenuCommand,
                              SilenceModeMenuCommand silenceModeMenuCommand,
                              UnknownMenuCommand unknownMenuCommand) {
        this.unknownMenuCommand = unknownMenuCommand;

        commands = new HashMap<>();
        commands.put(START_COMMAND.getCommandName(), startMenuCommand);
        commands.put(CHANGE_TOPICS_COMMAND.getCommandName(), changeTopicsMenuCommand);
        commands.put(CHANGE_TIME_COMMAND.getCommandName(), changeTimeMenuCommand);
        commands.put(SILENCE_MODE_COMMAND.getCommandName(), silenceModeMenuCommand);
    }

    /**
     * Method return {@link MenuCommand} object for further execution menu command logic.
     * @param command provides text reflection of menu command.
     * @return {@link MenuCommand} object to execute command logic.
     */
    public MenuCommand retrieveMenuCommand(String command) {
        return commands.getOrDefault(command, unknownMenuCommand);
    }
}
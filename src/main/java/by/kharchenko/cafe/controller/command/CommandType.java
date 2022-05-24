package by.kharchenko.cafe.controller.command;

import by.kharchenko.cafe.controller.command.impl.*;

import java.util.Objects;
import java.util.Optional;

public enum CommandType {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTRATION(new RegistrationCommand()),
    REGISTRATION_PAGE(new GoRegistrationPageCommand()),
    LANGUAGE_PAGE(new GoLanguagePageCommand()),
    BACK_PAGE(new GoBackLanguagePageCommand()),
    LANGUAGE(new LanguageCommand()),
    DEFAULT_COMMAND(new DefaultCommand());
    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Optional<Command> getCommand(String name) {
        CommandType commandType;
        if (validatedCommand(name)) {
            commandType = CommandType.valueOf(name.toUpperCase());
        } else {
            commandType = CommandType.DEFAULT_COMMAND;
        }
        return Optional.ofNullable(commandType.command);
    }

    private static boolean validatedCommand(String commandStr) {
        if (commandStr != null) {
            for (CommandType command : CommandType.values()) {
                if (Objects.equals(command.toString(), commandStr.toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }
}

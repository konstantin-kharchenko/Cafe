package by.kharchenko.cafe.controller.command;

import by.kharchenko.cafe.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;
}

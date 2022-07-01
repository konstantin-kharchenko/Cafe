package by.kharchenko.cafe.controller.command;

import by.kharchenko.cafe.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static by.kharchenko.cafe.controller.RequestAttribute.*;
import static by.kharchenko.cafe.controller.RequestParameter.*;

public interface Command {
    Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}

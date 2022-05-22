package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static by.kharchenko.cafe.controller.PagePath.LANGUAGE_PAGE;

public class LanguagePageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return new Router(LANGUAGE_PAGE, Router.Type.FORWARD);
    }
}

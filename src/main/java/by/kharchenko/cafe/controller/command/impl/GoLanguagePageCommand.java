package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.kharchenko.cafe.controller.PagePath.LANGUAGE_PAGE;
import static by.kharchenko.cafe.controller.RequestAttribute.NEW_PAGE;
import static by.kharchenko.cafe.controller.RequestAttribute.OLD_PAGE;

public class GoLanguagePageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String newPage = (String) session.getValue(NEW_PAGE);
        session.setAttribute(OLD_PAGE, newPage);
        session.setAttribute(NEW_PAGE, LANGUAGE_PAGE);
        return new Router(LANGUAGE_PAGE, Router.Type.FORWARD);
    }
}

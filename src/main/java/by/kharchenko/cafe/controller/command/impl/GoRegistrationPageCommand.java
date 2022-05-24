package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.kharchenko.cafe.controller.RequestAttribute.NEW_PAGE;
import static by.kharchenko.cafe.controller.RequestAttribute.OLD_PAGE;

public class GoRegistrationPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = PagePath.REGISTRATION_PAGE;
        HttpSession session = request.getSession();
        session.setAttribute(OLD_PAGE, PagePath.LOGIN_PAGE);
        session.setAttribute(NEW_PAGE, page);
        return new Router(page, Router.Type.FORWARD);
    }
}

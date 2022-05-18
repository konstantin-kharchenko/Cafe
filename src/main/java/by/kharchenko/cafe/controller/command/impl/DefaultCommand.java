package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PageMessage;
import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.kharchenko.cafe.controller.RequestAttribute.EXCEPTION_MSG_ATTRIBUTE;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.setAttribute(EXCEPTION_MSG_ATTRIBUTE, PageMessage.DEFAULT_MESSAGE);
        return new Router(PagePath.LOGIN_PAGE, Router.Type.REDIRECT);
    }
}

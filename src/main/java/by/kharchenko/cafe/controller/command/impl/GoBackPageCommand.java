package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Objects;

import static by.kharchenko.cafe.controller.RequestAttribute.OLD_PAGE;

public class GoBackPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String oldPage = (String) session.getValue(OLD_PAGE);
        session.setAttribute(OLD_PAGE, request.getAttribute(OLD_PAGE));
        if (Objects.equals(oldPage, PagePath.LOGIN_PAGE)) {
            return new Router(oldPage, Router.Type.REDIRECT);
        } else {
            return new Router(oldPage, Router.Type.REDIRECT);
        }
    }
}

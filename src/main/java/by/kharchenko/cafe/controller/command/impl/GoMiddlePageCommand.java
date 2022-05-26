package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.kharchenko.cafe.controller.RequestAttribute.*;

public class GoMiddlePageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        User.Role role = (User.Role) session.getValue(ROLE_ATTRIBUTE);
        if (role.equals(User.Role.CLIENT)) {
            session.setAttribute(OLD_PAGE, PagePath.CLIENT_PAGE);
        }
        else if (role.equals(User.Role.ADMINISTRATOR)){
            session.setAttribute(OLD_PAGE, PagePath.ADMINISTRATOR_PAGE);
        }
        session.setAttribute(NEW_PAGE, PagePath.MIDDLE_PAGE);
        return new Router(PagePath.MIDDLE_PAGE, Router.Type.FORWARD);
    }
}

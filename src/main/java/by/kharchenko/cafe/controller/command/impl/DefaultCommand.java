package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static by.kharchenko.cafe.controller.RequestAttribute.*;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        User.Role role = (User.Role) session.getValue(ROLE_ATTRIBUTE);
        Router router;
        if (role != null) {
            String userPage = (String) session.getValue(USER_PAGE);
            router = new Router(userPage, Router.Type.REDIRECT);
        } else {
            router = new Router(PagePath.HOME_PAGE, Router.Type.REDIRECT);
        }
        return router;
    }
}

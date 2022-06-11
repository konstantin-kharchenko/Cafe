package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.User;
import by.kharchenko.cafe.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static by.kharchenko.cafe.controller.PagePath.ADMINISTRATOR_PAGE;
import static by.kharchenko.cafe.controller.PagePath.CLIENT_PAGE;
import static by.kharchenko.cafe.controller.RequestAttribute.*;

public class SignInCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String login = request.getParameter(LOGIN_ATTRIBUTE);
        String password = request.getParameter(PASSWORD_ATTRIBUTE);
        Router router;
        try {
            Optional<User.Role> optionalRole = UserServiceImpl.getInstance().authenticate(login, password);
            if (optionalRole.isPresent()) {
                HttpSession session = request.getSession();
                User.Role role = optionalRole.get();
                session.setAttribute(ROLE_ATTRIBUTE, role);
                session.setAttribute(LOGIN_ATTRIBUTE, login);
                switch (role) {
                    case CLIENT -> {
                        router = new Router(PagePath.CLIENT_PAGE);
                        session.setAttribute(USER_PAGE, CLIENT_PAGE);
                    }
                    case ADMINISTRATOR -> {
                        router = new Router(PagePath.ADMINISTRATOR_PAGE);
                        session.setAttribute(USER_PAGE, ADMINISTRATOR_PAGE);
                    }
                    default -> router = new Router(PagePath.SIGN_IN_PAGE);
                }
            } else {
                router = new Router(PagePath.SIGN_IN_PAGE);
                request.setAttribute(MSG_ATTRIBUTE, "Incorrect password or login");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}

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

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static by.kharchenko.cafe.controller.PagePath.ADMINISTRATOR_PAGE;
import static by.kharchenko.cafe.controller.PagePath.CLIENT_PAGE;
import static by.kharchenko.cafe.controller.RequestAttribute.*;
import static by.kharchenko.cafe.controller.RequestParameter.BUNDLE_NAME;

public class SignInCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String login = request.getParameter(LOGIN_ATTRIBUTE);
        String password = request.getParameter(PASSWORD_ATTRIBUTE);
        Router router;
        HttpSession session = request.getSession();
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale((String) session.getValue(LANGUAGE_ATTRIBUTE)));
            Optional<User.Role> optionalRole = UserServiceImpl.getInstance().authenticate(login, password);
            if (optionalRole.isPresent()) {
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
                request.setAttribute(MSG_ATTRIBUTE, bundle.getString(INCORRECT_LOGIN_OR_PASSWORD));
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}

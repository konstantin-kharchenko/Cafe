package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static by.kharchenko.cafe.controller.RequestAttribute.*;
import static by.kharchenko.cafe.controller.RequestAttribute.BIRTHDAY_ATTRIBUTE;
import static by.kharchenko.cafe.controller.RequestParameter.*;
import static by.kharchenko.cafe.controller.RequestParameter.ROLE;

public class RegistrationCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router = null;
        Map<String, String> userData = new HashMap<>();
        String role = request.getParameter(ROLE_ATTRIBUTE);
        userData.put(LOGIN, request.getParameter(LOGIN_ATTRIBUTE));
        userData.put(PASSWORD, request.getParameter(PASSWORD_ATTRIBUTE));
        userData.put(EMAIL, request.getParameter(EMAIL_ATTRIBUTE));
        userData.put(PHONE_NUMBER, request.getParameter(PHONE_NUMBER_ATTRIBUTE));
        userData.put(NAME, request.getParameter(NAME_ATTRIBUTE));
        userData.put(SURNAME, request.getParameter(SURNAME_ATTRIBUTE));
        userData.put(BIRTHDAY, request.getParameter(BIRTHDAY_ATTRIBUTE));
        userData.put(ROLE, role);
        HttpSession session = request.getSession();
        try {
            boolean match = UserServiceImpl.getInstance().add(userData);
            if (match) {
                session.setAttribute(MSG_ATTRIBUTE, "success registration");
                request.setAttribute(REPEAT_ATTRIBUTE, false);
                router = new Router(PagePath.HOME_PAGE, Router.Type.REDIRECT);
            } else {
                if (userData.get(LOGIN).equals(LOGIN_EXISTS)) {
                    request.setAttribute(MSG_ATTRIBUTE, "login exists");
                    userData.put(LOGIN, "");
                }
                request.setAttribute(USER_ATTRIBUTE, userData);
                request.setAttribute(REPEAT_ATTRIBUTE, true);
                router = new Router(PagePath.REGISTRATION_PAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}

package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.dao.DefaultValues;
import by.kharchenko.cafe.model.entity.Administrator;
import by.kharchenko.cafe.model.entity.User;
import by.kharchenko.cafe.model.service.impl.AdministratorServiceImpl;
import by.kharchenko.cafe.model.service.impl.ClientServiceImpl;
import by.kharchenko.cafe.model.service.impl.UserServiceImpl;
import by.kharchenko.cafe.validator.DataValidator;
import by.kharchenko.cafe.validator.impl.DataValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static by.kharchenko.cafe.controller.PageMessage.LOGIN_EXISTS;
import static by.kharchenko.cafe.controller.RequestAttribute.*;
import static by.kharchenko.cafe.controller.RequestParameter.*;

public class RegistrationCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = null;
        Map<String, String> userData = new HashMap<>();
        String role = request.getParameter(ROLE_ATTRIBUTE);
        userData.put(LOGIN, request.getParameter(LOGIN));
        userData.put(PASSWORD, request.getParameter(PASSWORD));
        userData.put(EMAIL, request.getParameter(EMAIL));
        userData.put(PHONE_NUMBER, request.getParameter(PHONE_NUMBER));
        userData.put(NAME, request.getParameter(NAME));
        userData.put(SURNAME, request.getParameter(SURNAME));
        userData.put(AGE, request.getParameter(AGE));
        userData.put(ROLE, role);
        boolean isAdd = false;
        boolean isLoginExists;
        HttpSession session = request.getSession();
        session.setAttribute(OLD_PAGE, PagePath.REGISTRATION_PAGE);
        try {
            if (Objects.equals(role.toUpperCase(), User.Role.ADMINISTRATOR.toString())) {
                userData.put(EXPERIENCE, request.getParameter(EXPERIENCE));
                userData.put(STATUS, Administrator.Status.WAITING.getStatus());
                isAdd = AdministratorServiceImpl.getInstance().add(userData);
            } else if (Objects.equals(role.toUpperCase(), User.Role.CLIENT.toString())) {
                userData.put(PAYMENT_TYPE, request.getParameter(PAYMENT_TYPE));
                userData.put(IS_BLOCK, Boolean.toString(DefaultValues.DEFAULT_BOOLEAN_IS_BLOCK));
                userData.put(LOYALTY_POINTS, Integer.toString(DefaultValues.DEFAULT_LOYALTY_POINTS));
                isAdd = ClientServiceImpl.getInstance().add(userData);
            }
            if (isAdd) {
                router = new Router(PagePath.LOGIN_PAGE, Router.Type.REDIRECT);
            } else {
                if (!Objects.equals(userData.get(LOGIN), "")) {
                    isLoginExists = UserServiceImpl.getInstance().findIdUserByLogin(userData.get(LOGIN)).isPresent();
                    if (isLoginExists) {
                        request.setAttribute(EXCEPTION_MSG_ATTRIBUTE, LOGIN_EXISTS);
                    }
                }
                request.setAttribute(USER_ATTRIBUTE, userData);
                router = new Router(PagePath.REGISTRATION_PAGE, Router.Type.FORWARD);
            }
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        session.setAttribute(NEW_PAGE, router.getPage());
        return router;
    }
}

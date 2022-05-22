package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.dao.DefaultValues;
import by.kharchenko.cafe.model.entity.Administrator;
import by.kharchenko.cafe.model.entity.User;
import by.kharchenko.cafe.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.javatuples.Triplet;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static by.kharchenko.cafe.controller.PageMessage.*;
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
        HttpSession session = request.getSession();
        session.setAttribute(OLD_PAGE, PagePath.REGISTRATION_PAGE);
        try {
            if (Objects.equals(role.toUpperCase(), User.Role.ADMINISTRATOR.toString())) {
                userData.put(EXPERIENCE, request.getParameter(EXPERIENCE));
                userData.put(STATUS, Administrator.Status.WAITING.getStatus());
            } else if (Objects.equals(role.toUpperCase(), User.Role.CLIENT.toString())) {
                userData.put(PAYMENT_TYPE, request.getParameter(PAYMENT_TYPE));
                userData.put(IS_BLOCK, Boolean.toString(DefaultValues.DEFAULT_BOOLEAN_IS_BLOCK));
                userData.put(LOYALTY_POINTS, Integer.toString(DefaultValues.DEFAULT_LOYALTY_POINTS));
            }
            Triplet<Boolean, Boolean, Boolean> triplet = UserServiceImpl.getInstance().add(userData);
            if (triplet.getValue0() && !triplet.getValue1() && triplet.getValue2()) {
                router = new Router(PagePath.LOGIN_PAGE, Router.Type.REDIRECT);
                session.setAttribute(MSG_ATTRIBUTE, SUCCESSFUL_REGISTRATION);
            }
            if (!triplet.getValue0() && triplet.getValue1()) {
                request.setAttribute(MSG_ATTRIBUTE, LOGIN_EXISTS);
                request.setAttribute(USER_ATTRIBUTE, userData);
                router = new Router(PagePath.REGISTRATION_PAGE, Router.Type.FORWARD);
            }
            if (!triplet.getValue0() && !triplet.getValue1()) {
                request.setAttribute(USER_ATTRIBUTE, userData);
                router = new Router(PagePath.REGISTRATION_PAGE, Router.Type.FORWARD);
            }
            if (triplet.getValue0() && triplet.getValue1()) {
                request.setAttribute(MSG_ATTRIBUTE, LOGIN_EXISTS);
                request.setAttribute(USER_ATTRIBUTE, userData);
                router = new Router(PagePath.REGISTRATION_PAGE, Router.Type.FORWARD);
            }
            if (triplet.getValue0() && !triplet.getValue1() && !triplet.getValue2()) {
                request.setAttribute(MSG_ATTRIBUTE, FAILED_TO_ADD_USER);
                request.setAttribute(USER_ATTRIBUTE, userData);
                router = new Router(PagePath.REGISTRATION_PAGE, Router.Type.FORWARD);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        session.setAttribute(NEW_PAGE, router.getPage());
        return router;
    }
}

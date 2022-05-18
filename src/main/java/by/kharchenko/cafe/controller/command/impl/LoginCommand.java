package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PageMessage;
import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Administrator;
import by.kharchenko.cafe.model.entity.User;
import by.kharchenko.cafe.model.service.UserService;
import by.kharchenko.cafe.model.service.impl.UserServiceImpl;
import com.oracle.wls.shaded.org.apache.bcel.generic.NEW;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static by.kharchenko.cafe.controller.RequestAttribute.*;

public class LoginCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(LOGIN_ATTRIBUTE);
        String password = request.getParameter(PASSWORD_ATTRIBUTE);
        String page = "";
        Router router;
        HttpSession session = request.getSession();
        session.setAttribute(OLD_PAGE, PagePath.LOGIN_PAGE);
        UserService userService = UserServiceImpl.getInstance();
        try {
            Optional<? extends User> optionalUser = userService.authenticate(login, password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                request.setAttribute(USER_ATTRIBUTE, user);
                if (user.getRole() == User.Role.CLIENT) {
                    page = PagePath.CLIENT_PAGE;
                } else if (user.getRole() == User.Role.ADMINISTRATOR) {
                    if (((Administrator) user).getStatus() == Administrator.Status.ACCEPTED) {
                        page = PagePath.ADMINISTRATOR_PAGE;
                    } else {
                        request.setAttribute(EXCEPTION_MSG_ATTRIBUTE, PageMessage.ADMINISTRATOR_STATUS_EXCEPTION);
                        page = PagePath.LOGIN_PAGE;
                    }
                }
            } else {
                request.setAttribute(EXCEPTION_MSG_ATTRIBUTE, PageMessage.INCORRECT_PASSWORD_OR_LOGIN);
                page = PagePath.LOGIN_PAGE;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        session.setAttribute(NEW_PAGE, page);
        router = new Router(page, Router.Type.FORWARD);
        return router;
    }
}

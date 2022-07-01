package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Client;
import by.kharchenko.cafe.model.entity.User;
import by.kharchenko.cafe.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;

import static by.kharchenko.cafe.controller.RequestAttribute.*;
import static by.kharchenko.cafe.controller.RequestParameter.BUNDLE_NAME;

public class RefillCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        BigDecimal refill = new BigDecimal(request.getParameter(PRICE_ATTRIBUTE));
        int a = refill.compareTo(BigDecimal.ONE);
        try {
            HttpSession session = request.getSession();
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale((String) session.getValue(LANGUAGE_ATTRIBUTE)));
            if (a >= 0) {
                User user = (User) session.getValue(USER_ATTRIBUTE);
                UserServiceImpl.getInstance().refill(refill, ((Client) user).getIdClient());
                return new Router(PagePath.CLIENT_PAGE, Router.Type.REDIRECT);
            } else {
                request.setAttribute(MSG_ATTRIBUTE, bundle.getString(INVALID_DATE));
                return new Router(PagePath.REFILL_PAGE);
            }
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}

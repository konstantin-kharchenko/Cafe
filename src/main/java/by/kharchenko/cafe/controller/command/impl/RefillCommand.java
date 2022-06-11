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

import static by.kharchenko.cafe.controller.RequestAttribute.*;

public class RefillCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        BigDecimal refill = new BigDecimal(request.getParameter(PRICE_ATTRIBUTE));
        var a = refill.compareTo(BigDecimal.ONE);
        try {
            if (a >= 0) {
                HttpSession session = request.getSession();
                User user = (User) session.getValue(USER_ATTRIBUTE);
                UserServiceImpl.getInstance().refill(refill, ((Client) user).getIdClient());
                return new Router(PagePath.CLIENT_PAGE, Router.Type.REDIRECT);
            } else {
                request.setAttribute(MSG_ATTRIBUTE, "invalid data");
                return new Router(PagePath.REFILL_PAGE);
            }
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}

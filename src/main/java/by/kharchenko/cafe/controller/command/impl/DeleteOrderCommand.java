package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static by.kharchenko.cafe.controller.RequestAttribute.*;

public class DeleteOrderCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String idOrder = request.getParameter(ID_ORDER_ATTRIBUTE);
        try {
            boolean match = OrderServiceImpl.getInstance().delete(Integer.parseInt(idOrder));
            if (match){
                HttpSession session = request.getSession();
                String userPage = (String) session.getValue(USER_PAGE);
                return new Router(userPage, Router.Type.REDIRECT);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return null;
    }
}

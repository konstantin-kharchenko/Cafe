package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Client;
import by.kharchenko.cafe.model.entity.Order;
import by.kharchenko.cafe.model.entity.User;
import by.kharchenko.cafe.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static by.kharchenko.cafe.controller.RequestAttribute.ORDERS_ATTRIBUTE;
import static by.kharchenko.cafe.controller.RequestAttribute.USER_ATTRIBUTE;

public class GoAllOrdersPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getValue(USER_ATTRIBUTE);
        try {
            List<Order> orders = OrderServiceImpl.getInstance().findOrdersByIdClient(((Client)user).getIdClient());
            request.setAttribute(ORDERS_ATTRIBUTE, orders);
            return new Router(PagePath.ALL_ORDERS_PAGE);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Order;
import by.kharchenko.cafe.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.kharchenko.cafe.controller.RequestAttribute.*;
import static by.kharchenko.cafe.controller.RequestParameter.*;

public class GoChangeOrderPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String idOrder = request.getParameter(ID_ORDER_ATTRIBUTE);
        try {
            Optional<Order> optionalOrder = OrderServiceImpl.getInstance().findOrderByOrderId(Integer.parseInt(idOrder));
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                Map<String, String> orderData = new HashMap<>();
                orderData.put(NAME, order.getName());
                orderData.put(DATE, order.getDate().toString());
                orderData.put(PAYMENT_TYPE, order.getPaymentType().toString());
                request.setAttribute(ORDER_ATTRIBUTE, orderData);
                request.setAttribute(ID_ORDER_ATTRIBUTE, idOrder);
                request.setAttribute(REPEAT_ATTRIBUTE, true);
                return new Router(PagePath.CHANGE_ORDER_PAGE);
            }
            else {
                HttpSession session = request.getSession();
                String userPage = (String) session.getValue(USER_PAGE);
                return new Router(userPage, Router.Type.REDIRECT);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

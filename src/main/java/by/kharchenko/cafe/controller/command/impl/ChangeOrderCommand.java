package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Client;
import by.kharchenko.cafe.model.entity.User;
import by.kharchenko.cafe.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static by.kharchenko.cafe.controller.RequestAttribute.*;
import static by.kharchenko.cafe.controller.RequestParameter.*;
import static by.kharchenko.cafe.controller.RequestParameter.NAME;

public class ChangeOrderCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router = null;
        try {
            HttpSession session = request.getSession();
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale((String) session.getValue(LANGUAGE_ATTRIBUTE)));
            User user = (User) session.getValue(USER_ATTRIBUTE);
            Integer idOrder = Integer.parseInt(request.getParameter(ID_ORDER_ATTRIBUTE));
            Map<String, String> orderData = new HashMap<>();
            orderData.put(NAME, request.getParameter(NAME_ATTRIBUTE));
            orderData.put(DATE, request.getParameter(DATE_ATTRIBUTE));
            orderData.put(PAYMENT_TYPE, request.getParameter(PAYMENT_TYPE_ATTRIBUTE));
            orderData.put(ID_ORDER, idOrder.toString());
            orderData.put(ID_CLIENT, ((Client) user).getIdClient().toString());
            boolean match = OrderServiceImpl.getInstance().update(orderData);
            if (match) {
                session.setAttribute(MSG_ATTRIBUTE, bundle.getString(SUCCESS_CREATE_ORDER));
                request.setAttribute(REPEAT_ATTRIBUTE, false);
                request.setAttribute(ID_ORDER_ATTRIBUTE, idOrder);
                return new Router(PagePath.ORDER_PAGE);
            } else {
                if (orderData.get(NAME).equals(NAME_EXISTS)) {
                    request.setAttribute(MSG_ATTRIBUTE, bundle.getString(BUNDLE_NAME_EXISTS));
                    orderData.put(NAME, "");
                }
                request.setAttribute(REPEAT_ATTRIBUTE, true);
                request.setAttribute(ORDER_ATTRIBUTE, orderData);
                router = new Router(PagePath.CREATE_ORDER_PAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}

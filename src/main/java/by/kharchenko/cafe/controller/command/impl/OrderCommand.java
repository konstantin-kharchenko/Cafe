package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Menu;
import by.kharchenko.cafe.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static by.kharchenko.cafe.controller.RequestAttribute.*;

public class OrderCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String orderName = request.getParameter(ORDER_NAME_ATTRIBUTE);
        String orderId = request.getParameter(ORDER_ID_ATTRIBUTE);
        String orderDate = request.getParameter(ORDER_DATE_ATTRIBUTE);
        String orderIdClient = request.getParameter(ORDER_ID_CLIENT_ATTRIBUTE);
        String orderSum = request.getParameter(ORDER_SUM_ATTRIBUTE);
        HttpSession session = request.getSession();
        session.setAttribute(OLD_PAGE, PagePath.CLIENT_PAGE);
        try {
            List<Menu> menuList = OrderServiceImpl.getInstance().findMenuByIdOrder(Integer.parseInt(orderId));
            request.setAttribute(MENU_ATTRIBUTE, menuList);
            request.setAttribute(ORDER_NAME_ATTRIBUTE, orderName);
            request.setAttribute(ORDER_DATE_ATTRIBUTE, orderDate);
            request.setAttribute(ORDER_ID_CLIENT_ATTRIBUTE, orderIdClient);
            request.setAttribute(ORDER_SUM_ATTRIBUTE, orderSum);
            session.setAttribute(NEW_PAGE, PagePath.ORDER_PAGE);
            return new Router(PagePath.ORDER_PAGE, Router.Type.FORWARD);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static by.kharchenko.cafe.controller.RequestAttribute.*;

public class DeleteProductFromOrderCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String idOrder = request.getParameter(ID_ORDER_ATTRIBUTE);
        String idProduct = request.getParameter(ID_PRODUCT_ATTRIBUTE);
        try {
            boolean isDelete = OrderServiceImpl.getInstance().deleteProductFromOrder(Integer.parseInt(idProduct), Integer.parseInt(idOrder));
            if (isDelete) {
                request.setAttribute(ID_ORDER_ATTRIBUTE, idOrder);
                return new Router(PagePath.ORDER_PAGE);
            } else {
                HttpSession session = request.getSession();
                String userPage = (String) session.getValue(USER_PAGE);
                return new Router(userPage, Router.Type.REDIRECT);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

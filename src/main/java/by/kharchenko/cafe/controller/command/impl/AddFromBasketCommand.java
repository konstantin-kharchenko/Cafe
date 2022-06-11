package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Product;
import by.kharchenko.cafe.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Set;

import static by.kharchenko.cafe.controller.RequestAttribute.*;

public class AddFromBasketCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        Set<Product> basketProducts = (Set<Product>) session.getValue(BASKET_PRODUCTS_ATTRIBUTE);
        String idOrder = request.getParameter(ID_ORDER_ATTRIBUTE);
        try {
            if (basketProducts != null && basketProducts.size() > 0) {
                boolean match = OrderServiceImpl.getInstance().addProductsIdInOrdersProductsTableByIdOrder(Integer.parseInt(idOrder),
                        basketProducts.stream().toList());
                if (match){
                    request.setAttribute(ID_ORDER_ATTRIBUTE, idOrder);
                    session.setAttribute(BASKET_PRODUCTS_ATTRIBUTE, null);
                    return new Router(PagePath.ORDER_PAGE);
                }
                else {
                    String userPage = (String) session.getValue(USER_PAGE);
                    return new Router(userPage, Router.Type.REDIRECT);
                }
            } else {
                request.setAttribute(ID_ORDER_ATTRIBUTE, idOrder);
                return new Router(PagePath.ORDER_PAGE);
            }
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}

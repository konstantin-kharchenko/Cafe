package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.model.entity.Product;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Set;

import static by.kharchenko.cafe.controller.RequestAttribute.BASKET_PRODUCTS_ATTRIBUTE;
import static by.kharchenko.cafe.controller.RequestAttribute.ID_PRODUCT_ATTRIBUTE;

public class DeleteProductFromBasketCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String idProductString = request.getParameter(ID_PRODUCT_ATTRIBUTE);
        Integer idProduct = Integer.parseInt(idProductString);
        HttpSession session = request.getSession();
        Set<Product> basketProducts = (Set<Product>) session.getValue(BASKET_PRODUCTS_ATTRIBUTE);
        basketProducts.removeIf(a -> a.getIdProduct() == idProduct);
        return new Router(PagePath.BASKET_PAGE);
    }
}

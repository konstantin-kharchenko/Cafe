package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Ingredient;
import by.kharchenko.cafe.model.entity.Product;
import by.kharchenko.cafe.model.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;
import java.util.Set;

import static by.kharchenko.cafe.controller.RequestAttribute.*;

public class AddIngredientsFromAdministratorBasketCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        Set<Ingredient> basketIngredients = (Set<Ingredient>) session.getValue(BASKET_INGREDIENTS_ATTRIBUTE);
        String idProduct = request.getParameter(ID_PRODUCT_ATTRIBUTE);
        try {
            if (basketIngredients != null && basketIngredients.size() > 0) {
                ProductServiceImpl.getInstance().addIngredientsIdInProductsIngredientsTableByIdProduct(Integer.parseInt(idProduct),
                        basketIngredients.stream().toList());
            }
            Optional<Product> product = ProductServiceImpl.getInstance().findProductByProductId(Integer.parseInt(idProduct));
            request.setAttribute(PRODUCT_ATTRIBUTE, product.get());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(PagePath.CHANGE_PRODUCT_PAGE);
    }
}

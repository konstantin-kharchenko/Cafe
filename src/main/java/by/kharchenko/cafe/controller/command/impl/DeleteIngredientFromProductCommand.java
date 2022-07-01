package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Product;
import by.kharchenko.cafe.model.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

import static by.kharchenko.cafe.controller.RequestAttribute.*;

public class DeleteIngredientFromProductCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String idIngredient = request.getParameter(ID_INGREDIENT_ATTRIBUTE);
        String idProduct = request.getParameter(ID_PRODUCT_ATTRIBUTE);
        try {
            ProductServiceImpl.getInstance().deleteIngredientFromProduct(Integer.parseInt(idIngredient), Integer.parseInt(idProduct));
            Router router = new Router(PagePath.CHANGE_PRODUCT_PAGE);
            Optional<Product> product = ProductServiceImpl.getInstance().findProductByProductId(Integer.parseInt(idProduct));
            request.setAttribute(PRODUCT_ATTRIBUTE, product.get());
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

    }
}

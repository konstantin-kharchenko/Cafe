package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Ingredient;
import by.kharchenko.cafe.model.entity.Product;
import by.kharchenko.cafe.model.service.impl.IngredientServiceImpl;
import by.kharchenko.cafe.model.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.*;

import static by.kharchenko.cafe.controller.RequestAttribute.*;
import static by.kharchenko.cafe.controller.RequestParameter.*;

public class ChangeGramsCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale((String) session.getValue(LANGUAGE_ATTRIBUTE)));
        Map<String, String> data = new HashMap<>();
        data.put(ID_INGREDIENT, request.getParameter(ID_INGREDIENT_ATTRIBUTE));
        data.put(ID_PRODUCT, request.getParameter(ID_PRODUCT_ATTRIBUTE));
        data.put(GRAMS, request.getParameter(GRAMS_ATTRIBUTE));
        try {
            boolean match = ProductServiceImpl.getInstance().changeGrams(data);
            if (!match) {
                request.setAttribute(MSG_ATTRIBUTE, bundle.getString(INVALID_GRAMS));
                Optional<Ingredient> ingredient = IngredientServiceImpl.getInstance().findIngredientWithGrams(Integer.parseInt(data.get(ID_INGREDIENT)), Integer.parseInt(data.get(ID_PRODUCT)));
                if (ingredient.isPresent()) {
                    request.setAttribute(INGREDIENT_ATTRIBUTE, ingredient.get());
                    request.setAttribute(ID_PRODUCT_ATTRIBUTE, data.get(ID_PRODUCT));
                    return new Router(PagePath.CHANGE_GRAMS_PAGE);
                } else {
                    return new Router(PagePath.ADMINISTRATOR_PAGE);
                }
            }
            else {
                request.setAttribute(MSG_ATTRIBUTE, bundle.getString(SUCCESS_UPDATE_GRAMS));
            }
            Optional<Product> product = ProductServiceImpl.getInstance().findProductByProductId(Integer.parseInt(data.get(ID_PRODUCT)));
            request.setAttribute(PRODUCT_ATTRIBUTE, product.get());
            return new Router(PagePath.CHANGE_PRODUCT_PAGE);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

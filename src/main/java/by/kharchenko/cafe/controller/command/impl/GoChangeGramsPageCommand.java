package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Ingredient;
import by.kharchenko.cafe.model.service.impl.IngredientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

import static by.kharchenko.cafe.controller.RequestAttribute.*;

public class GoChangeGramsPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String idIngredient = request.getParameter(ID_INGREDIENT_ATTRIBUTE);
        String idProduct = request.getParameter(ID_PRODUCT_ATTRIBUTE);
        try {
            Optional<Ingredient> ingredient = IngredientServiceImpl.getInstance().findIngredientWithGrams(Integer.parseInt(idIngredient), Integer.parseInt(idProduct));
            if (ingredient.isPresent()) {
                request.setAttribute(INGREDIENT_ATTRIBUTE, ingredient.get());
                request.setAttribute(ID_PRODUCT_ATTRIBUTE, idProduct);
                return new Router(PagePath.CHANGE_GRAMS_PAGE);
            } else {
                return new Router(PagePath.ADMINISTRATOR_PAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

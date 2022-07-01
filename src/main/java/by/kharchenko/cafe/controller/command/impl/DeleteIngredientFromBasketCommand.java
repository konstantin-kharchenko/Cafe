package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.model.entity.Ingredient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Set;

import static by.kharchenko.cafe.controller.RequestAttribute.BASKET_INGREDIENTS_ATTRIBUTE;
import static by.kharchenko.cafe.controller.RequestAttribute.ID_INGREDIENT_ATTRIBUTE;

public class DeleteIngredientFromBasketCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String idIngredientString = request.getParameter(ID_INGREDIENT_ATTRIBUTE);
        Integer idIngredient = Integer.parseInt(idIngredientString);
        HttpSession session = request.getSession();
        Set<Ingredient> basketIngredients = (Set<Ingredient>) session.getValue(BASKET_INGREDIENTS_ATTRIBUTE);
        basketIngredients.removeIf(a -> a.getIdIngredient() == idIngredient);
        return new Router(PagePath.ADMINISTRATOR_BASKET_PAGE);
    }
}

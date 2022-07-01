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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.kharchenko.cafe.controller.RequestAttribute.*;
import static by.kharchenko.cafe.controller.RequestParameter.*;

public class GoChangeIngredientCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String idIngredient = request.getParameter(ID_INGREDIENT_ATTRIBUTE);
        try {
            Optional<Ingredient> optionalIngredient = IngredientServiceImpl.getInstance().findIngredientById(Integer.parseInt(idIngredient));
            if (optionalIngredient.isPresent()) {
                Ingredient ingredient = optionalIngredient.get();
                Map<String, String> ingredientData = new HashMap<>();
                ingredientData.put(NAME, ingredient.getName());
                ingredientData.put(SHELF_LIFE, ingredient.getShelfLife().toString());
                ingredientData.put(ID_INGREDIENT, idIngredient);
                request.setAttribute(INGREDIENT_ATTRIBUTE, ingredientData);
                return new Router(PagePath.CHANGE_INGREDIENT_PAGE);
            }
            return new Router(PagePath.ADMINISTRATOR_PAGE);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

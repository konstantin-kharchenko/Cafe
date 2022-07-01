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
import jakarta.servlet.http.HttpSession;

import java.util.*;

import static by.kharchenko.cafe.controller.RequestAttribute.*;
import static by.kharchenko.cafe.controller.RequestParameter.*;

public class ChangeIngredientCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Map<String, String> data = new HashMap<>();
        HttpSession session = request.getSession();
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale((String) session.getValue(LANGUAGE_ATTRIBUTE)));
        Router router;
        try {
            data.put(NAME, request.getParameter(NAME_ATTRIBUTE));
            data.put(SHELF_LIFE, request.getParameter(SHELF_LIFE_ATTRIBUTE));
            data.put(ID_INGREDIENT, request.getParameter(ID_INGREDIENT_ATTRIBUTE));
            boolean match = IngredientServiceImpl.getInstance().update(data);
            if (!match) {
                Optional<Ingredient> optionalIngredient = IngredientServiceImpl.getInstance().findIngredientById(Integer.parseInt(data.get(ID_INGREDIENT)));
                if (optionalIngredient.isPresent()) {
                    Ingredient ingredient = optionalIngredient.get();
                    Map<String, String> ingredientData = new HashMap<>();
                    ingredientData.put(NAME, ingredient.getName());
                    ingredientData.put(SHELF_LIFE, ingredient.getShelfLife().toString());
                    request.setAttribute(INGREDIENT_ATTRIBUTE, ingredientData);
                } else {
                    return new Router(PagePath.ADMINISTRATOR_PAGE);
                }
                StringBuilder stringBuilder = new StringBuilder();
                if (data.get(NAME).equals("")) {
                    stringBuilder.append(bundle.getString(INVALID_NAME));
                    stringBuilder.append(" ");
                }
                if (data.get(SHELF_LIFE).equals("")) {
                    stringBuilder.append(bundle.getString(INVALID_SHELF_LIFE));
                    stringBuilder.append(" ");
                }
                if (data.get(NAME).equals(NAME_EXISTS)) {
                    stringBuilder.append(bundle.getString(BUNDLE_NAME_EXISTS));
                }
                request.setAttribute(MSG_ATTRIBUTE, stringBuilder.toString());
                router = new Router(PagePath.CHANGE_INGREDIENT_PAGE);
            } else {
                int currentPage = 1;
                int pageCount = 0;
                List<Ingredient> ingredients = IngredientServiceImpl.getInstance().findIngredientsByPageNumber(currentPage);
                pageCount = (int) Math.ceil((1.0 * IngredientServiceImpl.getInstance().countProducts()) / 10);
                if (pageCount == 1) {
                    request.setAttribute(FIRST_PAGE, true);
                    request.setAttribute(LAST_PAGE, true);
                } else if (currentPage == pageCount) {
                    request.setAttribute(FIRST_PAGE, true);
                    request.setAttribute(LAST_PAGE, false);
                } else if (currentPage == 1) {
                    request.setAttribute(FIRST_PAGE, true);
                    request.setAttribute(LAST_PAGE, false);
                } else {
                    request.setAttribute(FIRST_PAGE, false);
                    request.setAttribute(LAST_PAGE, false);
                }
                request.setAttribute(COUNT_PAGE, pageCount);
                request.setAttribute(CURRENT_PAGE, currentPage);
                request.setAttribute(INGREDIENTS_ATTRIBUTE, ingredients);
                request.setAttribute(MSG_ATTRIBUTE, bundle.getString(SUCCESS_UPDATE_INGREDIENT));
                router = new Router(PagePath.ALL_INGREDIENTS_PAGE);
            }

        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}

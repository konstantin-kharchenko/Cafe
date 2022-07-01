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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static by.kharchenko.cafe.controller.RequestAttribute.*;

public class AddToAdministratorBasketCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            HttpSession session = request.getSession();
            Set<Ingredient> basketIngredients = (Set<Ingredient>) session.getValue(BASKET_INGREDIENTS_ATTRIBUTE);
            if (basketIngredients == null) {
                basketIngredients = new HashSet<>();
            }
            String id = request.getParameter(ID_INGREDIENT_ATTRIBUTE);
            Optional<Ingredient> optionalIngredient = IngredientServiceImpl.getInstance().findIngredientById(Integer.parseInt(id));
            if (optionalIngredient.isPresent()) {
                basketIngredients.add(optionalIngredient.get());
            }
            session.setAttribute(BASKET_INGREDIENTS_ATTRIBUTE, basketIngredients);
            int currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
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
            return new Router(PagePath.ALL_INGREDIENTS_PAGE);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

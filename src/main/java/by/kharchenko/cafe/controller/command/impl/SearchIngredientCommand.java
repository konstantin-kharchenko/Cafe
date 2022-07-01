package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Client;
import by.kharchenko.cafe.model.entity.Ingredient;
import by.kharchenko.cafe.model.service.impl.ClientServiceImpl;
import by.kharchenko.cafe.model.service.impl.IngredientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

import static by.kharchenko.cafe.controller.RequestAttribute.*;

public class SearchIngredientCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String name = request.getParameter(SEARCH_ATTRIBUTE);
        try {
            Optional<Ingredient> optionalIngredient = IngredientServiceImpl.getInstance().findIngredientByName(name);
            if (optionalIngredient.isPresent()) {
                Ingredient ingredient = optionalIngredient.get();
                request.setAttribute(SEARCH_INGREDIENT_ATTRIBUTE, ingredient);
                request.setAttribute(CURRENT_PAGE, 1);
                return new Router(PagePath.ALL_INGREDIENTS_PAGE);
            } else {
                int currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
                int pageCount = 0;
                List<Client> clients = ClientServiceImpl.getInstance().findClientsByPageNumber(currentPage);
                pageCount = (int) Math.ceil((1.0 * ClientServiceImpl.getInstance().countClients()) / 10);
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
                request.setAttribute(CLIENTS_ATTRIBUTE, clients);
                return new Router(PagePath.ALL_INGREDIENTS_PAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

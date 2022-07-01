package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.service.impl.IngredientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.*;

import static by.kharchenko.cafe.controller.RequestAttribute.*;
import static by.kharchenko.cafe.controller.RequestParameter.*;

public class AddIngredientCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router = null;
        try {
            HttpSession session = request.getSession();
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale((String) session.getValue(LANGUAGE_ATTRIBUTE)));
            Map<String, String> ingredientData = new HashMap<>();
            ingredientData.put(NAME, request.getParameter(NAME_ATTRIBUTE));
            ingredientData.put(SHELF_LIFE, request.getParameter(SHELF_LIFE_ATTRIBUTE));
            boolean match = IngredientServiceImpl.getInstance().add(ingredientData);
            if (match) {
                session.setAttribute(MSG_ATTRIBUTE, bundle.getString(SUCCESS_CREATE_INGREDIENT));
                router = new Router(PagePath.ADMINISTRATOR_PAGE, Router.Type.REDIRECT);
                request.setAttribute(REPEAT_ATTRIBUTE, false);
            } else {
                if (ingredientData.get(NAME).equals(NAME_EXISTS)) {
                    request.setAttribute(MSG_ATTRIBUTE, bundle.getString(BUNDLE_NAME_EXISTS));
                    ingredientData.put(NAME, "");
                }
                request.setAttribute(REPEAT_ATTRIBUTE, true);
                request.setAttribute(INGREDIENT_ATTRIBUTE, ingredientData);
                router = new Router(PagePath.ADD_INGREDIENT_PAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}

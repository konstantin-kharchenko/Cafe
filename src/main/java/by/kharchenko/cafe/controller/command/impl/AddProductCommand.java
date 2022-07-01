package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static by.kharchenko.cafe.controller.RequestAttribute.*;
import static by.kharchenko.cafe.controller.RequestParameter.*;

public class AddProductCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router = null;
        try {
            HttpSession session = request.getSession();
            String lan = (String) session.getValue(LANGUAGE_ATTRIBUTE);
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale(lan));
            Map<String, String> productData = new HashMap<>();
            productData.put(NAME, request.getParameter(NAME_ATTRIBUTE));
            productData.put(DATE, request.getParameter(DATE_ATTRIBUTE));
            productData.put(PRICE, request.getParameter(PRICE_ATTRIBUTE));
            boolean match = ProductServiceImpl.getInstance().add(productData);
            if (match) {
                session.setAttribute(MSG_ATTRIBUTE, bundle.getString(SUCCESS_CREATE_PRODUCT));
                router = new Router(PagePath.ADMINISTRATOR_PAGE, Router.Type.REDIRECT);
                request.setAttribute(REPEAT_ATTRIBUTE, false);
            } else {
                if (productData.get(NAME).equals(NAME_EXISTS)) {
                    request.setAttribute(MSG_ATTRIBUTE, bundle.getString(BUNDLE_NAME_EXISTS));
                    productData.put(NAME, "");
                }
                request.setAttribute(REPEAT_ATTRIBUTE, true);
                request.setAttribute(PRODUCT_ATTRIBUTE, productData);
                router = new Router(PagePath.ADD_PRODUCT_PAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}

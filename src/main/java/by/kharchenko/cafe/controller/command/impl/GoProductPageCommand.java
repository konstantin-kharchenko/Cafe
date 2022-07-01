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
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static by.kharchenko.cafe.controller.RequestAttribute.*;

public class GoProductPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String productId = request.getParameter(ID_PRODUCT_ATTRIBUTE);
        try {
            Optional<Product> optionalProduct = ProductServiceImpl.getInstance().findProductByProductId(Integer.parseInt(productId));
            if (optionalProduct.isPresent()) {
                request.setAttribute(PRODUCT_ATTRIBUTE, optionalProduct.get());
                return new Router(PagePath.CLIENT_PRODUCT_PAGE);
            } else {
                HttpSession session = request.getSession();
                String userPage = (String) session.getValue(USER_PAGE);
                return new Router(userPage, Router.Type.REDIRECT);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

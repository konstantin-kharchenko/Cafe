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

import java.util.List;

import static by.kharchenko.cafe.controller.RequestAttribute.*;

public class GoAllProductsPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        int pageCount = 0;
        try {
            List<Product> products = ProductServiceImpl.getInstance().findProductsByPageNumber(currentPage);
            pageCount = (int) Math.ceil((1.0 * ProductServiceImpl.getInstance().countProducts()) / 10);
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
            request.setAttribute(PRODUCTS_ATTRIBUTE, products);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(PagePath.ALL_PRODUCTS_PAGE, Router.Type.FORWARD);
    }
}

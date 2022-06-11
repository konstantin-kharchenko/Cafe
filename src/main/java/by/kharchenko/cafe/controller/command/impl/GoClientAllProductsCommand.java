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

import static by.kharchenko.cafe.controller.RequestAttribute.PRODUCTS_ATTRIBUTE;

public class GoClientAllProductsCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        int pageCount = 0;
        try {
            List<Product> products = ProductServiceImpl.getInstance().findProductsByPageNumber(currentPage);
            pageCount = (int) Math.ceil((1.0*ProductServiceImpl.getInstance().countProducts())/10);
            if (currentPage == 1) {
                request.setAttribute("firstPage", true);
                request.setAttribute("lastPage", false);
            } else if (currentPage == pageCount) {
                request.setAttribute("lastPage", true);
                request.setAttribute("firstPage", false);
            } else {
                request.setAttribute("page.lastPage", false);
                request.setAttribute("page.firstPage", false);
            }
            request.setAttribute("pageCount", pageCount);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute(PRODUCTS_ATTRIBUTE, products);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(PagePath.CLIENT_ALL_PRODUCTS_PAGE, Router.Type.FORWARD);
    }
}

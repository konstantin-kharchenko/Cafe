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

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static by.kharchenko.cafe.controller.RequestAttribute.*;


public class AddProductInBasketCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        Set<Product> basketProducts = (Set<Product>) session.getValue(BASKET_PRODUCTS_ATTRIBUTE);
        if (basketProducts == null) {
            basketProducts = new HashSet<>();
        }
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String name = request.getParameter(NAME_ATTRIBUTE);
        String id = request.getParameter(ID_PRODUCT_ATTRIBUTE);
        String date = request.getParameter(DATE_ATTRIBUTE);
        String price = request.getParameter(PRICE_ATTRIBUTE);
        Product product = new Product();
        product.setIdProduct(Integer.parseInt(id));
        product.setName(name);
        product.setPrice(new BigDecimal(price));
        product.setDate(LocalDate.parse(date));
        basketProducts.add(product);
        session.setAttribute(BASKET_PRODUCTS_ATTRIBUTE, basketProducts);
        String returnPage = request.getParameter(RETURN_PAGE);
        if (returnPage.equals(PagePath.CLIENT_ALL_PRODUCTS_PAGE)){
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
            return new Router(returnPage, Router.Type.FORWARD);
        }
        else {
            return new Router(returnPage, Router.Type.REDIRECT);
        }
    }
}

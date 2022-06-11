package by.kharchenko.cafe.controller.filter;

import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Product;
import by.kharchenko.cafe.model.service.impl.ProductServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static by.kharchenko.cafe.controller.PagePath.HOME_PAGE;
import static by.kharchenko.cafe.controller.RequestAttribute.ORDERS_ATTRIBUTE;
import static by.kharchenko.cafe.controller.RequestAttribute.PRODUCTS_ATTRIBUTE;

@WebFilter(filterName = "PreHomeFilter", dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST},
        urlPatterns = {"/" + HOME_PAGE})
public class PreHomeFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        List<Product> products = null;
        try {
            products = ProductServiceImpl.getInstance().findNewProducts();
            request.setAttribute(PRODUCTS_ATTRIBUTE, products);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        chain.doFilter(request, response);
    }
}

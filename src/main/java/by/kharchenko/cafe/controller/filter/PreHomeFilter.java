package by.kharchenko.cafe.controller.filter;

import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Product;
import by.kharchenko.cafe.model.service.impl.ProductServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import static by.kharchenko.cafe.controller.PagePath.HOME_PAGE;
import static by.kharchenko.cafe.controller.RequestAttribute.*;
import static by.kharchenko.cafe.controller.RequestAttribute.LANGUAGE_ATTRIBUTE;

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
        HttpSession session = httpServletRequest.getSession(true);
        Cookie[] cookies = httpServletRequest.getCookies();
        Cookie cookie = null;
        if (cookies != null) {
            if (cookies.length == 1) {
                session.setAttribute(LANGUAGE_ATTRIBUTE, ENGLISH_CODE_ATTRIBUTE);
            } else {
                for (Cookie c : cookies) {
                    if (LANGUAGE_ATTRIBUTE.equals(c.getName())) {
                        cookie = c;
                        break;
                    }
                }
                if (cookie != null) {
                    String val = cookie.getValue();
                    session.setAttribute(LANGUAGE_ATTRIBUTE, val);
                }
            }
        }
        List<Product> products = null;
        try {
            products = ProductServiceImpl.getInstance().findNewProducts();
            httpServletRequest.setAttribute(PRODUCTS_ATTRIBUTE, products);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        chain.doFilter(request, response);
    }
}

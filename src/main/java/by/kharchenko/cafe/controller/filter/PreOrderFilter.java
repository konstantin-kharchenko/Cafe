package by.kharchenko.cafe.controller.filter;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Order;
import by.kharchenko.cafe.model.entity.Product;
import by.kharchenko.cafe.model.service.impl.OrderServiceImpl;
import by.kharchenko.cafe.model.service.impl.ProductServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static by.kharchenko.cafe.controller.PagePath.HOME_PAGE;
import static by.kharchenko.cafe.controller.PagePath.ORDER_PAGE;
import static by.kharchenko.cafe.controller.RequestAttribute.*;

@WebFilter(filterName = "PreOrderFilter", dispatcherTypes = {DispatcherType.FORWARD},
        urlPatterns = {"/" + ORDER_PAGE})
public class PreOrderFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession(true);
        String idOrder = httpServletRequest.getParameter(ID_ORDER_ATTRIBUTE);
        try {
            Optional<Order> optionalOrder = OrderServiceImpl.getInstance().findOrderByOrderId(Integer.parseInt(idOrder));
            if (optionalOrder.isPresent()) {
                request.setAttribute(ORDER_ATTRIBUTE, optionalOrder.get());
                List<Product> products = ProductServiceImpl.getInstance().findProductsByIdOrder(Integer.parseInt(idOrder));
                request.setAttribute(PRODUCTS_ATTRIBUTE, products);
            } else {
                String userPage = (String) session.getValue(USER_PAGE);
                String contextPath = httpServletRequest.getContextPath();
                httpServletResponse.sendRedirect(contextPath + "/" + userPage);
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        chain.doFilter(request, response);
    }
}

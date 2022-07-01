package by.kharchenko.cafe.controller.filter;

import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.*;
import by.kharchenko.cafe.model.service.impl.ClientServiceImpl;
import by.kharchenko.cafe.model.service.impl.OrderServiceImpl;
import by.kharchenko.cafe.model.service.impl.ProductServiceImpl;
import by.kharchenko.cafe.model.service.impl.UserServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static by.kharchenko.cafe.controller.PagePath.*;
import static by.kharchenko.cafe.controller.RequestAttribute.*;

@WebFilter(filterName = "PreUserFilter", dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST},
        urlPatterns = {"/" + CLIENT_PAGE, "/" + ADMINISTRATOR_PAGE})
public class PreUserFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession(true);
        String login = (String) session.getValue(LOGIN_ATTRIBUTE);
        User.Role role = (User.Role) session.getValue(ROLE_ATTRIBUTE);
        try {
            Optional<? extends User> optionalUser = UserServiceImpl.getInstance().findUserByLoginAndRole(login, role);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                String stringPhoto = user.getStringPhoto();
                user.setStringPhoto("");
                session.setAttribute(USER_ATTRIBUTE, user);
                request.setAttribute(PHOTO_ATTRIBUTE, stringPhoto);
                if (user.getRole() == User.Role.CLIENT) {
                    if (((Client) user).isBlock()) {
                        session.setAttribute(MSG_ATTRIBUTE, CLIENT_BLOCK);
                        String contextPath = httpServletRequest.getContextPath();
                        httpServletResponse.sendRedirect(contextPath + "/" + SIGN_IN_PAGE);
                    } else {
                        List<Order> orders = OrderServiceImpl.getInstance().findNewOrdersByIdClient(((Client) user).getIdClient());
                        List<Product> products = ProductServiceImpl.getInstance().findNewProducts();
                        request.setAttribute(ORDERS_ATTRIBUTE, orders);
                        request.setAttribute(PRODUCTS_ATTRIBUTE, products);
                    }
                } else if (user.getRole() == User.Role.ADMINISTRATOR) {
                    if (((Administrator) user).getStatus() != Administrator.Status.ACCEPTED) {
                        session.setAttribute(MSG_ATTRIBUTE, ADMINISTRATOR_NOT_ACCEPTED);
                        String contextPath = httpServletRequest.getContextPath();
                        httpServletResponse.sendRedirect(contextPath + "/" + SIGN_IN_PAGE);
                    } else {
                        List<Client> clients = ClientServiceImpl.getInstance().findClientsWithOverdueOrders();
                        List<Client> todayClients = ClientServiceImpl.getInstance().findTodayClients();
                        request.setAttribute(CLIENTS_ATTRIBUTE, clients);
                        request.setAttribute(TODAY_CLIENTS_ATTRIBUTE, todayClients);
                    }
                }
            } else {
                throw new ServletException("no user");
            }

        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        chain.doFilter(request, response);
    }
}

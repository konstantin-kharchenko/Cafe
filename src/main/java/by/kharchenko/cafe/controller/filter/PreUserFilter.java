package by.kharchenko.cafe.controller.filter;

import by.kharchenko.cafe.exception.FilterException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Client;
import by.kharchenko.cafe.model.entity.Order;
import by.kharchenko.cafe.model.entity.User;
import by.kharchenko.cafe.model.service.impl.OrderServiceImpl;
import by.kharchenko.cafe.model.service.impl.UserServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static by.kharchenko.cafe.controller.PagePath.*;
import static by.kharchenko.cafe.controller.RequestAttribute.*;

@WebFilter(filterName = "PreUserFilter", dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST},
        urlPatterns = {"/" + CLIENT_PAGE, "/" + ADMINISTRATOR_PAGE})
public class PreUserFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(PreUserFilter.class);

    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession(true);
        String login = (String) session.getValue(LOGIN_ATTRIBUTE);
        if (login == null) {
            httpServletResponse.sendRedirect(LOGIN_PAGE);
        } else {
            User user = (User) request.getAttribute(USER_ATTRIBUTE);
            if (user == null) {
                try {
                    Optional<? extends User> optionalUser = UserServiceImpl.getInstance().findUserByLogin(login);
                    if (optionalUser.isPresent()) {
                        User user1 = optionalUser.get();
                        request.setAttribute(USER_ATTRIBUTE, user1);
                        if (user1.getRole() == User.Role.CLIENT) {
                            session.setAttribute(NEW_PAGE, CLIENT_PAGE);
                            List<Order> orders = OrderServiceImpl.getInstance().findOrdersByIdClient(((Client)user1).getIdClient());
                            request.setAttribute(ORDERS_ATTRIBUTE, orders);
                        } else if (user1.getRole() == User.Role.ADMINISTRATOR) {
                            session.setAttribute(NEW_PAGE, ADMINISTRATOR_PAGE);
                        }
                    } else {
                        throw new FilterException("no user");
                    }
                } catch (ServiceException | FilterException e) {
                    throw new ServletException(e);
                }
            } else {
                if (user.getRole() == User.Role.CLIENT) {
                    session.setAttribute(NEW_PAGE, CLIENT_PAGE);
                } else if (user.getRole() == User.Role.ADMINISTRATOR) {
                    session.setAttribute(NEW_PAGE, ADMINISTRATOR_PAGE);
                }
            }
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}

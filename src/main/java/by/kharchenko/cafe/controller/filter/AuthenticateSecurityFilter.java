package by.kharchenko.cafe.controller.filter;

import by.kharchenko.cafe.model.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.kharchenko.cafe.controller.RequestAttribute.ROLE_ATTRIBUTE;

@WebFilter(filterName = "AuthenticateSecurityFilter", urlPatterns = {"/view/jsp/client/*", "/view/jsp/administrator/*",
        "/view/jsp/common/exit.jsp"},
        initParams = {@WebInitParam(name = "HOME_PATH",
                value = "view/jsp/home.jsp")})
public class AuthenticateSecurityFilter implements Filter {
    private String homePath;

    public void init(FilterConfig config) throws ServletException {
        homePath = "/" + config.getInitParameter("HOME_PATH");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        User.Role role = (User.Role) httpServletRequest.getSession().getValue(ROLE_ATTRIBUTE);
        if (role == null) {
            String contextPath = httpServletRequest.getContextPath();
            httpServletResponse.sendRedirect(contextPath + homePath);
        } else {
            chain.doFilter(request, response);
        }
    }
}

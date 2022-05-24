package by.kharchenko.cafe.controller.filter;

import by.kharchenko.cafe.controller.PagePath;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.kharchenko.cafe.controller.RequestAttribute.IS_AUTHENTICATE_ATTRIBUTE;

@WebFilter(filterName = "SecurityFilter",
        urlPatterns = {"/view/pages/users/*"},
        initParams = {@WebInitParam(name = "LOGIN_PATH",
                value = PagePath.LOGIN_PAGE)})
public class SecurityFilter implements Filter {
    private String loginPath;

    public void init(FilterConfig config) throws ServletException {
        loginPath = "/" + config.getInitParameter("LOGIN_PATH");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Boolean isAuthenticate = (Boolean) httpServletRequest.getSession().getValue(IS_AUTHENTICATE_ATTRIBUTE);
        if (isAuthenticate == null || !isAuthenticate) {
            String contextPath = httpServletRequest.getContextPath();
            httpServletResponse.sendRedirect(contextPath + loginPath);
        }
        chain.doFilter(request, response);
    }
}

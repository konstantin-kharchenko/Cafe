package by.kharchenko.cafe.controller.filter;

import by.kharchenko.cafe.controller.PagePath;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "SecurityFilter",
        urlPatterns = {"/view/pages/*"},
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
        String contextPath = httpServletRequest.getContextPath();
        httpServletResponse.sendRedirect(contextPath + loginPath);
        chain.doFilter(request, response);
    }
}

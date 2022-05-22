package by.kharchenko.cafe.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.kharchenko.cafe.controller.RequestParameter.ENGLISH_LANGUAGE_CODE;
import static by.kharchenko.cafe.controller.RequestParameter.LANGUAGE;

@WebFilter(filterName = "PreLoginFilter", urlPatterns = "/view/login.jsp")
public class PreLoginFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(PreLoginFilter.class);

    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(true);
        Cookie[] cookies = httpServletRequest.getCookies();
        Cookie cookie = null;
        if (cookies != null) {
            if (cookies.length == 1) {
                session.setAttribute(LANGUAGE, ENGLISH_LANGUAGE_CODE);
            } else {
                for (Cookie c : cookies) {
                    if (LANGUAGE.equals(c.getName())) {
                        cookie = c;
                        break;
                    }
                }
                if (cookie != null) {
                    String val = cookie.getValue();
                    session.setAttribute(LANGUAGE, val);
                }
            }
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

}

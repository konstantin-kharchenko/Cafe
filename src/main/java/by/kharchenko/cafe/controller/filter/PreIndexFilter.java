package by.kharchenko.cafe.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.kharchenko.cafe.controller.RequestAttribute.ENGLISH_CODE_ATTRIBUTE;
import static by.kharchenko.cafe.controller.RequestAttribute.LANGUAGE_ATTRIBUTE;

@WebFilter(filterName = "PreIndexFilter", urlPatterns = "/index.jsp")
public class PreIndexFilter implements Filter {
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
        chain.doFilter(request, response);
    }
}

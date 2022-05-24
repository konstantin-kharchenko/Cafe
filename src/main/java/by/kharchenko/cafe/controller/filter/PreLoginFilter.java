package by.kharchenko.cafe.controller.filter;

import by.kharchenko.cafe.controller.PagePath;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Objects;

import static by.kharchenko.cafe.controller.PagePath.LANGUAGE_PAGE;
import static by.kharchenko.cafe.controller.PagePath.LOGIN_PAGE;
import static by.kharchenko.cafe.controller.RequestAttribute.NEW_PAGE;
import static by.kharchenko.cafe.controller.RequestAttribute.OLD_PAGE;
import static by.kharchenko.cafe.controller.RequestParameter.ENGLISH_LANGUAGE_CODE;
import static by.kharchenko.cafe.controller.RequestParameter.LANGUAGE;

@WebFilter(filterName = "PreLoginFilter", dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST}, urlPatterns = "/" + LOGIN_PAGE)
public class PreLoginFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(PreLoginFilter.class);

    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute(NEW_PAGE, PagePath.LOGIN_PAGE);
        String old_page = (String) session.getValue(OLD_PAGE);
        if (old_page == null || Objects.equals(old_page, LANGUAGE_PAGE)) {
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
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

}

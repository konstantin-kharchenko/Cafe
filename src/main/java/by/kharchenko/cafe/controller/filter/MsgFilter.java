package by.kharchenko.cafe.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.kharchenko.cafe.controller.RequestAttribute.MSG_ATTRIBUTE;

@WebFilter(filterName = "MsgFilter", dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST},
        urlPatterns = {"/view/jsp/*"})
public class MsgFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(true);
        String msg = (String) session.getValue(MSG_ATTRIBUTE);
        if (msg != null && !msg.equals("")) {
            httpServletRequest.setAttribute(MSG_ATTRIBUTE, msg);
            session.setAttribute(MSG_ATTRIBUTE, "");
        }
        chain.doFilter(request, response);
    }
}

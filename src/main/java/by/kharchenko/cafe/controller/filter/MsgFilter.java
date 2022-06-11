package by.kharchenko.cafe.controller.filter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.kharchenko.cafe.controller.PagePath.*;
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
        httpServletRequest.setAttribute(MSG_ATTRIBUTE, (String)session.getValue(MSG_ATTRIBUTE));
        session.setAttribute(MSG_ATTRIBUTE,"");
        chain.doFilter(request, response);
    }
}

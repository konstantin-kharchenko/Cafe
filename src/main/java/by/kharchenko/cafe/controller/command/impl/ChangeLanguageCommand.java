package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static by.kharchenko.cafe.controller.RequestAttribute.*;

public class ChangeLanguageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        String language = request.getParameter(LANGUAGE_ATTRIBUTE);
        String mainPage = (String) session.getValue(MAIN_PAGE_FOR_LANGUAGE_ATTRIBUTE);
        Cookie newCookie = new Cookie(LANGUAGE_ATTRIBUTE, language);
        newCookie.setMaxAge(60 * 60 * 24 * 7);
        Cookie cookie = new Cookie(LANGUAGE_ATTRIBUTE, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.addCookie(newCookie);
        session.setAttribute(LANGUAGE_ATTRIBUTE, language);
        session.setAttribute(MAIN_PAGE_FOR_LANGUAGE_ATTRIBUTE, "");
        return new Router(mainPage, Router.Type.REDIRECT);
    }
}

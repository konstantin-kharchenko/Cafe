package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static by.kharchenko.cafe.controller.RequestAttribute.MAIN_PAGE_FOR_LANGUAGE_ATTRIBUTE;


public class GoLanguagePageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        String mainPage = request.getParameter(MAIN_PAGE_FOR_LANGUAGE_ATTRIBUTE);
        session.setAttribute(MAIN_PAGE_FOR_LANGUAGE_ATTRIBUTE, mainPage);
        return new Router(PagePath.LANGUAGE_PAGE);
    }
}

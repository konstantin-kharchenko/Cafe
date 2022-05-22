package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Objects;

import static by.kharchenko.cafe.controller.RequestParameter.*;

public class LanguageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String language = request.getParameter(LANGUAGE);
        if (Objects.equals(language, RUSSIAN_LANGUAGE)) {
            session.setAttribute(LANGUAGE, RUSSIAN_LANGUAGE_CODE);
        }
        if (Objects.equals(language, ENGLISH_LANGUAGE)) {
            session.setAttribute(LANGUAGE, ENGLISH_LANGUAGE_CODE);
        }
        return new Router(PagePath.LOGIN_PAGE, Router.Type.REDIRECT);
    }
}

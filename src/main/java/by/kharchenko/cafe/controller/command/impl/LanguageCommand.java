package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Objects;

import static by.kharchenko.cafe.controller.RequestAttribute.OLD_PAGE;
import static by.kharchenko.cafe.controller.RequestParameter.*;

public class LanguageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String language = request.getParameter(LANGUAGE);
        String oldPage = (String) session.getValue(OLD_PAGE);
        session.setAttribute(OLD_PAGE, PagePath.LANGUAGE_PAGE);
        switch (language) {
            case RUSSIAN_LANGUAGE:
                session.setAttribute(LANGUAGE, RUSSIAN_LANGUAGE_CODE);
                break;
            case ENGLISH_LANGUAGE:
                session.setAttribute(LANGUAGE, ENGLISH_LANGUAGE_CODE);
                break;
            default:
                session.setAttribute(LANGUAGE, ENGLISH_LANGUAGE_CODE);

        }
        if (Objects.equals(oldPage, PagePath.LOGIN_PAGE)) {
            return new Router(oldPage, Router.Type.REDIRECT);
        } else {
            return new Router(oldPage, Router.Type.REDIRECT);
        }
    }
}

package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Administrator;
import by.kharchenko.cafe.model.service.impl.AdministratorServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import static by.kharchenko.cafe.controller.RequestAttribute.ADMINISTRATORS_ATTRIBUTE;

public class AdministratorApplicationsCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            List<Administrator> administrators = AdministratorServiceImpl.getInstance().findApplications();
            request.setAttribute(ADMINISTRATORS_ATTRIBUTE, administrators);
            return new Router(PagePath.ADMINISTRATOR_APPLICATIONS_PAGE);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

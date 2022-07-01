package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Client;
import by.kharchenko.cafe.model.service.impl.ClientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

import static by.kharchenko.cafe.controller.RequestAttribute.*;

public class FindClientCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Integer idUser = Integer.parseInt(request.getParameter(ID_USER_ATTRIBUTE));
        try {
            Optional<Client> client = ClientServiceImpl.getInstance().findClientByUserId(idUser);
            if (client.isPresent()) {
                request.setAttribute(CLIENT_ATTRIBUTE, client.get());
            }
            return new Router(PagePath.ADMINISTRATOR_CLIENT_PAGE);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

    }
}

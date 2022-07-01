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
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static by.kharchenko.cafe.controller.RequestAttribute.*;

public class UnlockCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String idClient = request.getParameter(ID_CLIENT_ATTRIBUTE);
        try {
            boolean match = ClientServiceImpl.getInstance().unlockClient(Integer.parseInt(idClient));
            if (match) {
                String idUser = request.getParameter(ID_USER_ATTRIBUTE);
                Optional<Client> optionalUser = ClientServiceImpl.getInstance().findClientByUserId(Integer.parseInt(idUser));
                if (optionalUser.isPresent()) {
                    request.setAttribute(CLIENT_ATTRIBUTE, optionalUser.get());
                    return new Router(PagePath.ADMINISTRATOR_CLIENT_PAGE);
                } else {
                    HttpSession session = request.getSession();
                    String userPage = (String) session.getValue(USER_PAGE);
                    return new Router(userPage);
                }
            } else {
                HttpSession session = request.getSession();
                String userPage = (String) session.getValue(USER_PAGE);
                return new Router(userPage);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

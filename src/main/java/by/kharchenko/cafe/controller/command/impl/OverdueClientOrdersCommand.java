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

import static by.kharchenko.cafe.controller.RequestAttribute.CLIENT_ATTRIBUTE;
import static by.kharchenko.cafe.controller.RequestAttribute.ID_CLIENT_ATTRIBUTE;

public class OverdueClientOrdersCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String idClient = request.getParameter(ID_CLIENT_ATTRIBUTE);
            Client client = ClientServiceImpl.getInstance().findClientWithOverdueOrders(Integer.parseInt(idClient));
            request.setAttribute(CLIENT_ATTRIBUTE, client);
            return new Router(PagePath.OVERDUE_CLIENT_ORDERS_PAGE);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

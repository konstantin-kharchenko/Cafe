package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Product;
import by.kharchenko.cafe.model.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

import static by.kharchenko.cafe.controller.RequestAttribute.ID_PRODUCT_ATTRIBUTE;
import static by.kharchenko.cafe.controller.RequestAttribute.PRODUCT_ATTRIBUTE;

public class GoChangeProductPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String idProduct = request.getParameter(ID_PRODUCT_ATTRIBUTE);
        try {
            Optional<Product> product = ProductServiceImpl.getInstance().findProductByProductId(Integer.parseInt(idProduct));
            request.setAttribute(PRODUCT_ATTRIBUTE, product.get());
            return new Router(PagePath.CHANGE_PRODUCT_PAGE);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

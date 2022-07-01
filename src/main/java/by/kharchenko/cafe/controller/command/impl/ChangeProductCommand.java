package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Product;
import by.kharchenko.cafe.model.service.impl.ProductServiceImpl;
import by.kharchenko.cafe.util.encryption.CustomPictureEncoder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.*;

import static by.kharchenko.cafe.controller.RequestAttribute.*;
import static by.kharchenko.cafe.controller.RequestParameter.*;

public class ChangeProductCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Map<String, String> data = new HashMap<>();
        Router router;
        HttpSession session = request.getSession();
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale((String) session.getValue(LANGUAGE_ATTRIBUTE)));
        Part filePart = null;
        try {
            filePart = request.getPart(PHOTO_ATTRIBUTE);
            byte[] photoBytes = filePart.getInputStream().readAllBytes();
            String userPhoto = CustomPictureEncoder.arrayToBase64(photoBytes);
            String photoName = filePart.getSubmittedFileName();
            data.put(NAME, request.getParameter(NAME_ATTRIBUTE));
            data.put(PRICE, request.getParameter(PRICE_ATTRIBUTE));
            data.put(DATE, request.getParameter(DATE_ATTRIBUTE));
            data.put(ID_PRODUCT, request.getParameter(ID_PRODUCT_ATTRIBUTE));
            data.put(PHOTO, userPhoto);
            data.put(PHOTO_NAME, photoName);
            boolean match = ProductServiceImpl.getInstance().update(data);
            if (!match) {
                StringBuilder stringBuilder = new StringBuilder();
                if (data.get(NAME).equals("")) {
                    stringBuilder.append(bundle.getString(INVALID_NAME));
                    stringBuilder.append(" ");
                }
                if (data.get(PRICE).equals("")) {
                    stringBuilder.append(bundle.getString(INVALID_PRICE));
                    stringBuilder.append(" ");
                }
                if (data.get(DATE).equals("")) {
                    stringBuilder.append(bundle.getString(INVALID_DATE));
                    stringBuilder.append(" ");
                }
                if (!data.get(PHOTO).equals(EMPTY)) {
                    stringBuilder.append(bundle.getString(INVALID_PHOTO_FILE));
                }
                request.setAttribute(MSG_ATTRIBUTE, stringBuilder.toString());
            }
            router = new Router(PagePath.CHANGE_PRODUCT_PAGE);
            Optional<Product> product = ProductServiceImpl.getInstance().findProductByProductId(Integer.parseInt(data.get(ID_PRODUCT)));
            request.setAttribute(PRODUCT_ATTRIBUTE, product.get());
        } catch (ServiceException | ServletException | IOException e) {
            throw new CommandException(e);
        }
        return router;
    }
}

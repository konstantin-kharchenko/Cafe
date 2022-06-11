package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.User;
import by.kharchenko.cafe.model.service.impl.UserServiceImpl;
import by.kharchenko.cafe.util.encryption.CustomPictureEncoder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static by.kharchenko.cafe.controller.RequestAttribute.*;
import static by.kharchenko.cafe.controller.RequestParameter.*;

public class UpdateUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Map<String, String> userData = new HashMap<>();
        Router router;
        HttpSession session = request.getSession();
        Part filePart = null;
        try {
            User user = (User) session.getValue(USER_ATTRIBUTE);
            filePart = request.getPart(PHOTO_ATTRIBUTE);
            byte[] photoBytes = filePart.getInputStream().readAllBytes();
            String userPhoto = CustomPictureEncoder.arrayToBase64(photoBytes);
            String photoName = filePart.getSubmittedFileName();
            userData.put(NAME, request.getParameter(NAME_ATTRIBUTE));
            userData.put(SURNAME, request.getParameter(SURNAME_ATTRIBUTE));
            userData.put(LOGIN, request.getParameter(LOGIN_ATTRIBUTE));
            userData.put(PHONE_NUMBER, request.getParameter(PHONE_NUMBER_ATTRIBUTE));
            userData.put(PHOTO, userPhoto);
            userData.put(PHOTO_NAME, photoName);
            userData.put(ID_USER, user.getIdUser().toString());
            userData.put(ROLE, user.getRole().toString());
            boolean match = UserServiceImpl.getInstance().update(userData);
            if (match) {
                String userPage = (String) session.getValue(USER_PAGE);
                router = new Router(userPage, Router.Type.REDIRECT);
            } else {
                String fromPage = request.getParameter(FROM_PAGE);
                router = new Router(fromPage);
                StringBuilder stringBuilder = new StringBuilder();
                if (userData.get(NAME).equals("")) {
                    stringBuilder.append("Invalid Name. ");
                }
                if (userData.get(SURNAME).equals("")) {
                    stringBuilder.append("Invalid Surname. ");
                }
                if (userData.get(LOGIN).equals("")) {
                    stringBuilder.append("Invalid Login. ");
                }
                if (userData.get(LOGIN).equals(LOGIN_EXISTS)) {
                    stringBuilder.append("Login Exists. ");
                }
                if (userData.get(PHONE_NUMBER).equals("")) {
                    stringBuilder.append("Invalid Phone Number. ");
                }
                if (!userData.get(PHOTO).equals("Empty")) {
                    stringBuilder.append("Invalid Photo File. ");
                }
                request.setAttribute(MSG_ATTRIBUTE, stringBuilder.toString());
                String stringPhoto = UserServiceImpl.getInstance().findStringPhotoByStringPath(user.getPhotoPath());
                request.setAttribute(PHOTO_ATTRIBUTE, stringPhoto);
            }
        } catch (ServiceException | ServletException | IOException e) {
            throw new CommandException(e);
        }
        return router;
    }
}

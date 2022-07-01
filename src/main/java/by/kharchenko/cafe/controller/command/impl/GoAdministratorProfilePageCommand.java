package by.kharchenko.cafe.controller.command.impl;

import by.kharchenko.cafe.controller.PagePath;
import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.User;
import by.kharchenko.cafe.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static by.kharchenko.cafe.controller.RequestAttribute.PHOTO_ATTRIBUTE;
import static by.kharchenko.cafe.controller.RequestAttribute.USER_ATTRIBUTE;

public class GoAdministratorProfilePageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String stringPhoto = null;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getValue(USER_ATTRIBUTE);
            stringPhoto = UserServiceImpl.getInstance().findStringPhotoByStringPath(user.getPhotoPath());
            request.setAttribute(PHOTO_ATTRIBUTE, stringPhoto);
            return new Router(PagePath.ADMINISTRATOR_PROFILE_PAGE);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}

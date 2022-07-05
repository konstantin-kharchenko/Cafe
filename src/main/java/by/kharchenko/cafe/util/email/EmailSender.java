package by.kharchenko.cafe.util.email;

import by.kharchenko.cafe.exception.ServiceException;

public interface EmailSender {

    boolean sendMail(String mail, String mailSubject, String mailText) throws ServiceException;
}

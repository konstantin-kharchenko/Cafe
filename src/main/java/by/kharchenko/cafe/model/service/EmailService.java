package by.kharchenko.cafe.model.service;

import by.kharchenko.cafe.exception.ServiceException;

public interface EmailService {
    boolean sendMail(String mail) throws ServiceException;
}

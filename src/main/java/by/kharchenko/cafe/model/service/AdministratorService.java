package by.kharchenko.cafe.model.service;

import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Administrator;
import by.kharchenko.cafe.model.entity.User;

import java.util.Optional;

public interface AdministratorService {
    Optional<Administrator> findAdministratorByLogin(String login) throws ServiceException;
}

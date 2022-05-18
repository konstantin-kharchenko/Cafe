package by.kharchenko.cafe.model.service;

import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.Client;
import by.kharchenko.cafe.model.entity.User;

import java.util.Optional;

public interface ClientService {
    Optional<Client> findClientByLogin(String login) throws ServiceException;
}

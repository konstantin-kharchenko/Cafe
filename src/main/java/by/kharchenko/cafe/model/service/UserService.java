package by.kharchenko.cafe.model.service;

import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<? extends User> authenticate(String login, String password) throws ServiceException;

    Optional<? extends User> findUserByLogin(String login) throws ServiceException;

    Optional<Integer> findIdUserByLogin(String login) throws ServiceException;

    List<String> findLogins() throws ServiceException;
}

package by.kharchenko.cafe.model.service;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User.Role> authenticate(String login, String password) throws ServiceException;

    User.Role findUserRoleByLogin(String login) throws ServiceException;

    Optional<? extends User> findUserByLogin(String login, User.Role role) throws ServiceException;

    List<String> findLogins() throws ServiceException;

    String findStringPhotoByStringPath(String stringPath) throws ServiceException;

    void refill(BigDecimal refill, Integer idClient) throws ServiceException;
}

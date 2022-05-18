package by.kharchenko.cafe.model.dao;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    boolean authenticate(String login, String password) throws DaoException;

    Optional<Integer> findIdUserByLogin(String login) throws DaoException;

    User.Role findUserRoleByLogin(String login) throws DaoException;

    List<String> findLogins() throws DaoException;
}

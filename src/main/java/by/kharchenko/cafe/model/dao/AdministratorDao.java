package by.kharchenko.cafe.model.dao;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Administrator;
import by.kharchenko.cafe.model.entity.User;

import java.util.Map;
import java.util.Optional;

public interface AdministratorDao {
    Optional<Administrator> findAdministratorByUserId(int id) throws DaoException;

    void addInHistory(Map<String, String> userData);
}

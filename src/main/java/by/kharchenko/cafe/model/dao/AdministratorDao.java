package by.kharchenko.cafe.model.dao;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Administrator;

import java.util.Optional;

public interface AdministratorDao {
    Administrator findAdministratorByUserId(int id) throws DaoException;
}

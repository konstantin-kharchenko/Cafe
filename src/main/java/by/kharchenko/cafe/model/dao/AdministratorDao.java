package by.kharchenko.cafe.model.dao;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Administrator;

import java.util.List;

public interface AdministratorDao {
    Administrator findAdministratorByUserId(int id) throws DaoException;

    List<Administrator> findApplications() throws DaoException;

    void approval(int parseInt) throws DaoException;
}

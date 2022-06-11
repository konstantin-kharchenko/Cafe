package by.kharchenko.cafe.model.dao;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Client;

import java.util.Optional;

public interface ClientDao {
    Client findClientByUserId(int idUser) throws DaoException;
}

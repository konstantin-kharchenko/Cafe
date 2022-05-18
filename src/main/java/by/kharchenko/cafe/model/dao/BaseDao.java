package by.kharchenko.cafe.model.dao;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.exception.ServiceException;
import by.kharchenko.cafe.model.entity.AbstractEntity;

import java.util.List;
import java.util.Map;

public interface BaseDao<T extends AbstractEntity> {
    boolean insert(T t) throws DaoException;

    boolean delete(T t) throws DaoException;

    boolean delete(int id) throws DaoException;

    boolean add(Map<String, String> userData) throws DaoException;

    List<T> findAll() throws DaoException;

    T update(T t) throws DaoException;

}
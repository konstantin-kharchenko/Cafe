package by.kharchenko.cafe.model.dao;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.AbstractEntity;

import java.util.List;

public interface BaseDao<T extends AbstractEntity> {

    boolean delete(T t) throws DaoException;

    boolean delete(int id) throws DaoException;

    boolean add(T userData) throws DaoException;

    List<T> findAll() throws DaoException;

    boolean update(T t) throws DaoException;

}
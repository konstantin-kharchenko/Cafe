package by.kharchenko.cafe.model.dao.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.dao.BaseDao;
import by.kharchenko.cafe.model.dao.OrderDao;
import by.kharchenko.cafe.model.entity.Order;

import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao, BaseDao<Order> {
    @Override
    public boolean insert(Order order) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Order order) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return false;
    }

    @Override
    public boolean add(Map<String, String> userData) throws DaoException {
        return false;
    }

    @Override
    public List<Order> findAll() throws DaoException {
        return null;
    }

    @Override
    public Order update(Order order) throws DaoException {
        return null;
    }
}

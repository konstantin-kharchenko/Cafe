package by.kharchenko.cafe.model.dao.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.dao.BaseDao;
import by.kharchenko.cafe.model.dao.ProductDao;
import by.kharchenko.cafe.model.entity.Product;

import java.util.List;
import java.util.Map;

public class ProductDaoImpl implements ProductDao, BaseDao<Product> {
    @Override
    public boolean insert(Product product) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Product product) throws DaoException {
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
    public List<Product> findAll() throws DaoException {
        return null;
    }

    @Override
    public Product update(Product product) throws DaoException {
        return null;
    }
}

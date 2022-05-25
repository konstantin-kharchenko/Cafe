package by.kharchenko.cafe.model.mapper.impl;

import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Product;
import by.kharchenko.cafe.model.mapper.CustomRowMapper;

import java.sql.ResultSet;

public class ProductMapper implements CustomRowMapper<Product> {
    private static final ProductMapper instance = new ProductMapper();

    public static ProductMapper getInstance() {
        return instance;
    }

    private ProductMapper() {

    }
    @Override
    public void rowMap(Product product, ResultSet resultSet) throws DaoException {

    }
}

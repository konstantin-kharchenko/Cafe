package by.kharchenko.cafe.model.mapper.impl;

import by.kharchenko.cafe.controller.RequestParameter;
import by.kharchenko.cafe.exception.DaoException;
import by.kharchenko.cafe.model.entity.Product;
import by.kharchenko.cafe.model.mapper.CustomRowMapper;
import org.apache.logging.log4j.Level;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ProductMapper implements CustomRowMapper<Product> {
    private static final ProductMapper instance = new ProductMapper();

    public static ProductMapper getInstance() {
        return instance;
    }

    private ProductMapper() {

    }

    @Override
    public void rowMap(Product product, ResultSet resultSet) throws DaoException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            product.setIdProduct(resultSet.getInt(RequestParameter.ID_PRODUCT));
            product.setName(resultSet.getString(RequestParameter.NAME));
            product.setDate(resultSet.getDate(RequestParameter.DATE).toLocalDate());
            product.setPrice(resultSet.getBigDecimal(RequestParameter.PRICE));
            product.setPhoto(resultSet.getString(RequestParameter.PHOTO));
            logger.log(Level.INFO, product.getName());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
